package com.platon.browser.task;

import com.platon.browser.TestBase;
import com.platon.browser.config.BlockChainConfig;
import com.platon.browser.engine.BlockChain;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

/**
 * @Auther: Chendongming
 * @Date: 2019/9/9 20:29
 * @Description:
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class NetworkStatUpdateTaskTest extends TestBase {
    private static Logger logger = LoggerFactory.getLogger(NetworkStatUpdateTaskTest.class);
    @Spy
    private NetworkStatUpdateTask networkStatUpdateTask;
    @Mock
    private BlockChain blockChain;
    @Mock
    private BlockChainConfig chainConfig;

    @Before
    public void setup(){
        ReflectionTestUtils.setField(networkStatUpdateTask, "blockChain", blockChain);
        ReflectionTestUtils.setField(networkStatUpdateTask, "chainConfig", chainConfig);
    }

    @Test
    public void testStart(){
        when(blockChain.getCurBlock()).thenReturn(blocks.get(0));
        Map<Integer, BigDecimal> foundationSubsidiesMap = new HashMap<>();
        foundationSubsidiesMap.put(1,BigDecimal.ONE);
        when(chainConfig.getFoundationSubsidies()).thenReturn(foundationSubsidiesMap);
        when(blockChain.getAddIssueEpoch()).thenReturn(BigInteger.ONE);
        when(blockChain.getChainConfig()).thenReturn(new BlockChainConfig());
        networkStatUpdateTask.start();
        verify(networkStatUpdateTask, times(1)).start();
    }
}
