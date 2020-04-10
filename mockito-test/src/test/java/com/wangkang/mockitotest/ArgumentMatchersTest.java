package com.wangkang.mockitotest;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.exceptions.misusing.UnfinishedStubbingException;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * @author wangkang
 * @date 2020/4/9- 22:01
 * @since
 */
@RunWith(MockitoJUnitRunner.class)
public class ArgumentMatchersTest {
    @Mock
    List<Integer> list;

    @Test
    public void test() {
        //public class InstanceOf implements ArgumentMatcher
        doReturn(1).when(list).get(anyInt());
        assert 1 == list.get(-1);
        assert 1 == list.get(0);
        assert 1 == list.get(1);

        doReturn(false).when(list).add(argThat(new GreatThenFive()));
        assert !list.add(null);//null 没有匹配，greateThenFive，所以取默认值 flase!!!
        assert !list.add(6);

        doReturn(true).when(list).add(argThat((v) -> v < -1));
        assert list.add(-2);
    }

    @Test(expected = Throwable.class)
    @Ignore
    public void test2() {
        Argument argument = mock(Argument.class);
        doReturn(1).when(argument).args(anyInt(), "-1");
    }
}

class GreatThenFive implements ArgumentMatcher<Integer> {

    @Override
    public boolean matches(Integer argument) {
        if (argument == null) {
            return false;
        }
        return argument > 5;
    }
}
