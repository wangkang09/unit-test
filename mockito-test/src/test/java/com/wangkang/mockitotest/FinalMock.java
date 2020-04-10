package com.wangkang.mockitotest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import java.util.List;

import static org.mockito.AdditionalAnswers.delegatesTo;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

/**
 * @author wangkang
 * @date 2020/4/9- 22:39
 * @since
 */
public class FinalMock {
    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Test
    public void test() {
        DontYouDareToMockMe dare = new DontYouDareToMockMe();
        List<Integer> mock = mock(List.class, delegatesTo(dare));
        //mock DontYouDareToMockMe 的 get() 方法！！
        doReturn(2).when(mock).get(anyInt());
        //只要方法名和参数一样就可以
        assert mock.get(1) == 2;
    }

    final class DontYouDareToMockMe {
        public Integer get(int idx) { return idx;}
    }
}
