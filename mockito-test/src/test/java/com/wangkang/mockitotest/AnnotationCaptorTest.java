package com.wangkang.mockitotest;

import com.wangkang.mockitotest.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.concurrent.ThreadLocalRandom;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;

/**
 * @author wangkang
 * @date 2020/4/7- 23:20
 * @since
 */
@RunWith(MockitoJUnitRunner.class)
public class AnnotationCaptorTest {
    @Captor
    ArgumentCaptor<Integer> argumentCaptor;
    @Mock
    User user;

    @Test
    public void test() {
        int age = ThreadLocalRandom.current().nextInt(1000);
        user.setAge(age);
        //通过使用了 argumentCaptor.capture() 获取了 age 值
        verify(user).setAge(argumentCaptor.capture());
        assert argumentCaptor.getValue() == age;
    }

}
