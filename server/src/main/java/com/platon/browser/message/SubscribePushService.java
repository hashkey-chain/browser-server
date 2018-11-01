package com.platon.browser.message;

import com.alibaba.fastjson.JSON;
import com.maxmind.geoip.Location;
import com.platon.browser.common.base.BaseResp;
import com.platon.browser.common.dto.agent.BlockDto;
import com.platon.browser.common.dto.agent.NodeDto;
import com.platon.browser.common.dto.agent.TransactionDto;
import com.platon.browser.common.dto.mq.Message;
import com.platon.browser.common.enums.MqMessageTypeEnum;
import com.platon.browser.common.enums.RetEnum;
import com.platon.browser.dto.IndexInfo;
import com.platon.browser.dto.StatisticInfo;
import com.platon.browser.dto.StatisticItem;
import com.platon.browser.dto.block.BlockInfo;
import com.platon.browser.dto.node.NodeInfo;
import com.platon.browser.dto.transaction.TransactionInfo;
import com.platon.browser.enums.ChainEnum;
import com.platon.browser.service.CacheService;
import com.platon.browser.util.GeoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SubscribePushService {

    private final Logger logger = LoggerFactory.getLogger(SubscribePushService.class);

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private CacheService cacheService;

    @RabbitListener(queues = "#{platonQueue.name}")
    public void receive(String msg) {
        Message message = JSON.parseObject(msg,Message.class);
        BaseResp resp;
        ChainEnum chainId = ChainEnum.getEnum(message.getChainId());
        switch (MqMessageTypeEnum.valueOf(message.getType().toUpperCase())){
            case NODE:
                logger.info("STOMP推送节点信息: {}",msg);
                NodeDto nodeDto = JSON.parseObject(message.getStruct(), NodeDto.class);

                NodeInfo nodeInfo = new NodeInfo();
                BeanUtils.copyProperties(nodeDto,nodeInfo);
                Location location = GeoUtil.getLocation(nodeDto.getIp());
                nodeInfo.setLatitude(location.latitude);
                nodeInfo.setLongitude(location.longitude);

                resp = BaseResp.build(RetEnum.RET_SUCCESS.getCode(),RetEnum.RET_SUCCESS.getName(),nodeInfo);
                messagingTemplate.convertAndSend("/topic/node/new?cid="+chainId.code, resp);
                break;
            case BLOCK:
                logger.info("STOMP推送区块信息: {}",msg);
                BlockDto blockDto = JSON.parseObject(message.getStruct(),BlockDto.class);

                BlockInfo blockInfo = new BlockInfo();
                BeanUtils.copyProperties(blockDto,blockInfo);
                blockInfo.setServerTime(System.currentTimeMillis());
                blockInfo.setNode(blockDto.getMiner());
                blockInfo.setTimestamp(blockDto.getTimestamp());
                blockInfo.setHeight(blockDto.getNumber());
                blockInfo.setBlockReward(blockDto.getBlockReward());
                blockInfo.setTransaction(blockDto.getTransaction().size());

                resp = BaseResp.build(RetEnum.RET_SUCCESS.getCode(),RetEnum.RET_SUCCESS.getName(),blockInfo);
                messagingTemplate.convertAndSend("/topic/block/new?cid="+chainId.code, resp);

                logger.info("STOMP推送指标信息: {}",msg);
                IndexInfo indexInfo = new IndexInfo();
                indexInfo.setCurrentHeight(blockInfo.getHeight());
                indexInfo.setCurrentTransaction(blockDto.getTransaction().size());
                cacheService.updateIndexInfo(indexInfo,false, chainId);
                indexInfo = cacheService.getIndexInfo(chainId);
                resp = BaseResp.build(RetEnum.RET_SUCCESS.getCode(),RetEnum.RET_SUCCESS.getName(),indexInfo);
                messagingTemplate.convertAndSend("/topic/index/new?cid="+chainId.code, resp);

                logger.info("STOMP推送交易信息: {}",msg);
                List<TransactionDto> transactionDtos = blockDto.getTransaction();
                List<TransactionInfo> transactionInfos = new ArrayList<>();
                transactionDtos.forEach(transactionDto -> {
                    TransactionInfo bean = new TransactionInfo();
                    BeanUtils.copyProperties(transactionDto,bean);
                    bean.setTxHash(transactionDto.getHash());
                    bean.setTimestamp(transactionDto.getTimestamp());
                    bean.setBlockHeight(transactionDto.getBlockNumber().longValue());
                    bean.setFrom(transactionDto.getFrom());
                    bean.setTo(transactionDto.getTo());
                    bean.setTransactionIndex(transactionDto.getTransactionIndex().intValue());
                    bean.setValue(transactionDto.getValue());
                    transactionInfos.add(bean);
                });
                resp = BaseResp.build(RetEnum.RET_SUCCESS.getCode(),RetEnum.RET_SUCCESS.getName(),transactionInfos);
                messagingTemplate.convertAndSend("/topic/transaction/new?cid="+chainId.code, resp);

                logger.info("STOMP推送统计信息: {}",msg);
                StatisticInfo statisticInfo = new StatisticInfo();
                statisticInfo.setHighestBlockNumber(blockInfo.getHeight());
                statisticInfo.setBlockCount(1);
                statisticInfo.setTransactionCount(transactionInfos.size());
                statisticInfo.setCurrent(transactionDtos.size());
                statisticInfo.setDayTransaction(transactionInfos.size());

                List<StatisticItem> statisticItems = new ArrayList<>();
                StatisticItem statisticItem = new StatisticItem();
                statisticItem.setHeight(blockInfo.getHeight());
                statisticItem.setTime(blockInfo.getTimestamp());
                statisticItem.setTransaction(Long.valueOf(transactionInfos.size()));
                statisticItems.add(statisticItem);
                statisticInfo.setBlockStatisticList(statisticItems);
                cacheService.updateStatisticInfo(statisticInfo,false,chainId);

                statisticInfo = cacheService.getStatisticInfo(chainId);

                resp = BaseResp.build(RetEnum.RET_SUCCESS.getCode(),RetEnum.RET_SUCCESS.getName(),statisticInfo);
                messagingTemplate.convertAndSend("/topic/statistic/new?cid="+chainId.code, resp);
                break;
        }
    }
}
