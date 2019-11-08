package com.platon.browser.collection.queue.handler;

import com.lmax.disruptor.EventHandler;
import com.platon.browser.client.result.ReceiptResult;
import com.platon.browser.collection.queue.event.BlockEvent;
import com.platon.browser.common.collection.dto.CollectionBlock;
import com.platon.browser.exception.BeanCreateOrUpdateException;
import com.platon.browser.common.queue.collection.publisher.CollectionEventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.web3j.protocol.core.methods.response.PlatonBlock;

import java.util.concurrent.ExecutionException;

/**
 * 区块事件处理器
 */
@Slf4j
@Component
public class BlockEventHandler implements EventHandler<BlockEvent> {

    @Autowired
    private CollectionEventPublisher collectionEventPublisher;

    private Long preBlockNum=0L;
    @Override
    @Retryable(value = Exception.class, maxAttempts = Integer.MAX_VALUE,label = "BlockEventHandler")
    public void onEvent(BlockEvent event, long sequence, boolean endOfBatch) throws ExecutionException, InterruptedException, BeanCreateOrUpdateException {
        log.debug("BlockEvent处理:{}(event(block({})),sequence({}),endOfBatch({}))",
                Thread.currentThread().getStackTrace()[1].getMethodName(),event.getEpochMessage().getCurrentBlockNumber(),sequence,endOfBatch);
        try {
            PlatonBlock.Block rawBlock = event.getBlockCF().get().getBlock();
            ReceiptResult receiptResult = event.getReceiptCF().get();
            CollectionBlock block = CollectionBlock.newInstance().updateWithRawBlockAndReceiptResult(rawBlock,receiptResult);

            if(preBlockNum!=0L&&(block.getNum()-preBlockNum!=1)) throw new AssertionError();

            collectionEventPublisher.publish(block,block.getTransactions(),event.getEpochMessage());
            preBlockNum=block.getNum();
        }catch (Exception e){
            log.error("",e);
            throw e;
        }
    }
}