package com.wangkang.mockitotest;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.quality.Strictness;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * @author wangkang
 * @date 2020/4/9- 23:22
 * @since
 */
public class StricTEst {
    @Rule
    public MockitoRule mockito = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS);

    @Test
    public void STRIT_STUBS() {
        List<Integer> STRIT_STUBS = mock(List.class);
        doReturn(1).when(STRIT_STUBS).get(1);
        STRIT_STUBS.size();
        verify(STRIT_STUBS).size();
        STRIT_STUBS.get(1);// Automatically verified by STRICT_STUBS
        verifyNoMoreInteractions(STRIT_STUBS); // No need of ignoreStubs()
        System.out.println("----------->STRIT_STUBS模式下，执行打桩方法会自动进行校验");
    }
}
