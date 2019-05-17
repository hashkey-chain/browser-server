package com.platon.browser.controller;

import com.platon.browser.config.ChainsConfig;
import com.platon.browser.dao.entity.Transaction;
import com.platon.browser.dto.RespPage;
import com.platon.browser.dto.transaction.TransactionVoteReq;
import com.platon.browser.dto.transaction.VoteInfo;
import com.platon.browser.dto.transaction.VoteSummary;
import com.platon.browser.dto.transaction.VoteTransaction;
import com.platon.browser.enums.I18nEnum;
import com.platon.browser.enums.RetEnum;
import com.platon.browser.exception.ResponseException;
import com.platon.browser.req.transaction.CandidateTicketCountReq;
import com.platon.browser.req.transaction.TicketCountByTxHashReq;
import com.platon.browser.req.transaction.TransactionListReq;
import com.platon.browser.req.transaction.VoteSummaryReq;
import com.platon.browser.res.BaseResp;
import com.platon.browser.service.ApiService;
import com.platon.browser.util.I18nUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * User: dongqile
 * Date: 2019/3/19
 * Time: 11:39
 */
@RestController
@RequestMapping("/api")
public class ApiController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(ApiController.class);
    @Autowired
    private I18nUtil i18n;
    @Autowired
    private ChainsConfig chainsConfig;
    @Autowired
    private ApiService apiService;

    /**
     * @api {post} api/getBatchVoteSummary a.获取统计统计信息
     * @apiVersion 1.0.0
     * @apiName GetBatchVoteSummary
     * @apiGroup api
     * @apiDescription 获取统计统计信息
     * @apiParamExample {json} Request-Example:
     * {
     *      "addressList":[
     *       addlist1,      //地址列表list<String>
     *       addlist2
     *       ]
     * }
     * @apiSuccessExample {json} Success-Response:
     * {
     *      "errMsg": "",//描述信息
     *      "code": 0,//成功（0），失败则由相关失败码
     *      "data": [
     *               {
     *               "locked":"1999900000",//String类型,投票锁定,单位Energon
     *               "earnings":"1999900000",//String类型,投票收益,单位Energon
     *               "totalTicketNum": "100",//string！！！ 总票数
     *               "validNum":"2"//String类型,有效票数
     *               }
     *       ]
     * }
     */

    @PostMapping("getBatchVoteSummary")
    public RespPage<VoteSummary> getBatchVoteSummary(@RequestHeader(CID) String chainId, @Valid @RequestBody VoteSummaryReq req){
        if(req.getAddressList().size() < 0){
            throw new ResponseException(i18n.i(I18nEnum.FAILURE));
        }
        List<VoteSummary> res = apiService.getVoteSummary(req.getAddressList(),req.getCid());
        RespPage<VoteSummary> list = new RespPage<>();
        list.setData(res);
        return list;
    }


    /**
     * @api {post} api/getBatchVoteTransaction b.批量获选票交易
     * @apiVersion 1.0.0
     * @apiName getBatchVoteTransaction
     * @apiGroup api
     * @apiDescription 批量获选票交易
     * @apiParamExample {json} Request-Example:
     * {
     *      "pageNo": 1,//页数(必填)默认1
     *      "pageSize": 10,//页大小(必填)默认10,
     *       "walletAddrs" :
     *          [
     *          addlist1,      //地址列表list<String>
     *          addlist2
     *          ]
     * }
     * @apiSuccessExample {json} Success-Response:
     * {
     *      "errMsg": "",//描述信息
     *      "code": 0,//成功（0），失败则由相关失败码
     *      "totalCount":18,//总数
     *      "totalPages":1,//总页数
     *      "data": [
     *           {
     *           "TransactionHash":"0xbbbbbb...",//String类型
     *           "candidateId":"0xffffff....",//候选人Id
     *           "owner":"0xbbb..."//投票人钱包地址，
     *           "earnings":"90000000000000000",//此次交易投票获得的收益，单位Energon
     *           "transactionTime":"1231231233".//Unix时间戳，毫秒级,交易时间
     *           "deposit":"1000000000000",// 当时的购票价格，单位Energon
     *           "totalTicketNum": "100",// 总票数
     *           "validNum": "50",// 有效票
     *       }
     *       ]
     * }
     */
    @PostMapping("getBatchVoteTransaction")
    public RespPage<VoteTransaction> getBatchVoteTransaction(@RequestHeader(CID) String chainId,@Valid @RequestBody TransactionVoteReq req){
        RespPage<VoteTransaction> res = apiService.getVoteTransaction(req);
        return res;
    }

    /**
     * @api {post} api/getCandidateTicketCount c.批量获取节点有效选票
     * @apiVersion 1.0.0
     * @apiName getCandidateTicketCount
     * @apiGroup api
     * @apiDescription 批量获取节点有效选票
     * @apiParamExample {json} Request-Example:
     * {
     *
     *       "nodeIds" :
     *          [
     *          nodeId1,      //节点id列表list<String>
     *          nodeId2
     *          ],
     * }
     * @apiSuccessExample {json} Success-Response:
     * {
     *      "errMsg": "",//描述信息
     *      "code": 0,//成功（0），失败则由相关失败码
     *      "data": [
     *           {
     *           "nodeId1" : 10, //节点id : 有效票
     *           "nodeId2" : 1,
     *           "nodeId3" : 3
     *       }
     *       ]
     * }
     */
    @PostMapping("getCandidateTicketCount")
    public BaseResp getCandidateTicketCount(@RequestHeader(CID) String chainId, @Valid @RequestBody CandidateTicketCountReq req){
        Map<String,Integer> map = apiService.getCandidateTicketCount(req.getNodeIds(), req.getCid());
        return BaseResp.build(RetEnum.RET_SUCCESS.getCode(),i18n.i(I18nEnum.SUCCESS),map);
    }



    /**
     * @api {post} api/getTicketCountByTxHash d.批量获取投票交易相关信息
     * @apiVersion 1.0.0
     * @apiName getTicketCountByTxHash
     * @apiGroup api
     * @apiDescription 批量获取投票交易相关信息
     * @apiParamExample {json} Request-Example:
     * {
     *       "hashList" :
     *          [
     *          "0xqew1652131d3...."      //hash列表list<String>
     *          ]
     * }
     * @apiSuccessExample {json} Success-Response:
     * {
     *      "errMsg": "",//描述信息
     *      "code": 0,//成功（0），失败则由相关失败码
     *      "totalCount":18,//总数
     *      "totalPages":1,//总页数
     *      "data": [
     *           {
     *           "nodeId":"0xbbbbbb...",//节点Id
     *           "nodeName":"123",//节点名称
     *           "vailVoteCount":12,//有效票数
     *           "voteSum":22,//总票数
     *           "deadLine":"1231231233".//实际过期时间
     *           "walletAddress":"0xbbbbbb",//钱包地址
     *           "price":"100"//票价
     *           "income":,//收益
     *       }
     *       ]
     * }
     */
    @PostMapping("getTicketCountByTxHash")
    public RespPage<VoteInfo> getTicketCountByTxHash(@RequestHeader(CID) String chainId,@Valid @RequestBody TicketCountByTxHashReq req){
        return apiService.getTicketCountByTxHash(req);
    }
}
