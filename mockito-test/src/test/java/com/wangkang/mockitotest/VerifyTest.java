package com.wangkang.mockitotest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @author wangkang
 * @date 2020/4/9- 19:29
 * @since
 */
@RunWith(MockitoJUnitRunner.class)
public class VerifyTest {
    @Mock
    List<String> list;

    @Test
    public void test() {
        list.add("-1");
        verify(list).add(anyString());

        try {
            verify(list).clear();
            Assert.fail();
        } catch (Throwable e) {
            System.out.println("----------->verify mock 对象没有执行的行为为报错");
        }
    }

    @Mock
    List<String> listOrder1;
    @Mock
    List<String> listOrder2;
    @Test
    public void timeTest() {
        list.add("-1");
        list.add("-2");
        verify(list, times(2)).add(anyString());
        try {
            verify(list).add(anyString());
            Assert.fail();
        } catch (Throwable e) {
            System.out.println("----------->默认验证的是调用了一次，可以 times() 指定多次");
        }
    }

    @Test
    public void OrderTest() {
        listOrder1.add("-1");
        listOrder2.add("-1");
        listOrder2.add("-2");
        listOrder1.add("-2");

        InOrder inOrder = inOrder(listOrder1, listOrder2);
        inOrder.verify(listOrder1).add("-1");
        inOrder.verify(listOrder2).add("-1");

        inOrder.verify(listOrder1).add("-2");
        try {
            inOrder.verify(listOrder2).add("-2");
        } catch (Throwable e) {
            System.out.println("----------->listOrder2.add() 是在 listOrder1.add 之前的，所以报错");
        }
    }

    @Mock
    RunTimeVerify runTimeVerify;
    @Test
    public void runTimeTest() throws InterruptedException {
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
            runTimeVerify.timeout(1);
        }).start();
        verify(runTimeVerify, timeout(1500)).timeout(1);
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
            runTimeVerify.timeout(2);
        }).start();

        try {
            verify(runTimeVerify, timeout(100)).timeout(2);
            Assert.fail();
        } catch (Throwable e) {
            System.out.println("----------->100ms 后验证方法并没有调用，所以报错");
        }
    }

    @Mock
    List<Integer> ignoreVerifyTest1;
    @Mock
    List<Integer> ignoreVerifyTest2;
    @Test
    public void ignoreVerifyTest() {
        ignoreVerifyTest1.clear();
        ignoreVerifyTest1.get(1);
        verify(ignoreVerifyTest1).clear();
        verify(ignoreVerifyTest1).get(1);
        verifyNoMoreInteractions(ignoreVerifyTest1);

        doReturn(1).when(ignoreVerifyTest2).get(1);
        ignoreVerifyTest2.clear();
        ignoreVerifyTest2.get(1);
        verify(ignoreVerifyTest2).clear();
        //忽略了已打桩的 get(1)方法的验证
        verifyNoMoreInteractions(ignoreStubs(ignoreVerifyTest2));

        try {
            verifyNoMoreInteractions(ignoreVerifyTest2);
            //等效
//            verifyZeroInteractions(ignoreVerifyTest2);
            Assert.fail();
        } catch (Throwable e) {
            System.out.println("----------->因为还要调用的方法没有verify，所以verifyNoMoreInteractions报错！");
        }
    }
    @Mock
    List<Integer> ignoreOrderTest;
    @Test
    public void ignoreOrderTest() {
        doReturn(false).when(ignoreOrderTest).add(0);
        ignoreOrderTest.get(1);
        ignoreOrderTest.clear();
        ignoreOrderTest.add(0);//我们不想验证这个已经打桩的方法
        InOrder inOrder = inOrder(ignoreStubs(ignoreOrderTest));
        inOrder.verify(ignoreOrderTest).get(1);
        inOrder.verify(ignoreOrderTest).clear();
        inOrder.verifyNoMoreInteractions();
        System.out.println("----------->ignoreStubs,使得 验证是可以忽略 打桩的方法调用");
    }
}
