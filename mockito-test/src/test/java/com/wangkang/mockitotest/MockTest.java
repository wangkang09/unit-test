package com.wangkang.mockitotest;

import com.wangkang.mockitotest.HttpRequesterWithHeaders.HttpBuilder;
import com.wangkang.mockitotest.entity.DeepInner;
import com.wangkang.mockitotest.entity.DeepOut;
import com.wangkang.mockitotest.entity.RealMethodMock;
import com.wangkang.mockitotest.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * @author wangkang
 * @date 2020/4/6- 18:01
 * @since
 */
@SuppressWarnings("all")
@Slf4j
public class MockTest {
    @Test
    public void test1() {
    }

    @Test
    public void test() {
        List<User> mockedList = mock(ArrayList.class);//可以mock具体类，建议
        assert mockedList.get(0) == null;
        mockedList = mock(LinkedList.class, RETURNS_DEFAULTS);
        assert mockedList.get(0) == null;
        mockedList = mock(LinkedList.class, withSettings().defaultAnswer(RETURNS_DEFAULTS));
        log.info("-------------------------------> mock 方法默认返回 null");
        try {
            mockedList.get(0).getAge();
            Assert.fail();
        } catch (Exception e) {
            assert e.getMessage() == null;
        }

        mockedList = mock(LinkedList.class, RETURNS_SMART_NULLS);
        log.info("-------------------------------> RETURNS_SMART_NULLS 方法返回 SMART_NULLS");
        try {
            mockedList.get(0).getAge();
            Assert.fail();
        } catch (Exception e) {
            assert e.getMessage().contains("cannot be cast to com.wangkang.mockitotest.entity.User");
        }

        log.info("-------------------------------> RETURNS_MOCKS 方法如果是引用对象返回 mock，如果是 primitive 返回 java 默认值，如果是 final 返回 null");
        mockedList = mock(LinkedList.class, RETURNS_MOCKS);
        assert Mockito.mockingDetails(mockedList.get(0)).isMock();
        DeepOut deepOut = mock(DeepOut.class, RETURNS_MOCKS);
        //引用类型返回 mock。final 类返回 null
        assert Mockito.mockingDetails(deepOut.getDeepInner()).isMock();
        //默认情况返回 空字符串、false、0 等 java 默认初始化值
        assert "".equals(deepOut.getAddress());

        log.info("-------------------------------> RETURNS_DEEP_STUBS 方法如果是引用对象返回 mock，如果是 primitive or a final class 返回 null");
        //RETURNS_DEEP_STUBS 适用于 mock 某个对象内部对象的方法返回值
        deepOut = mock(DeepOut.class, RETURNS_DEEP_STUBS);
        //DEEP_STUBS 只能通过 when().then() 形式，不可使用 do().when()形式
        assert Mockito.mockingDetails(deepOut.getDeepInner()).isMock();
        //直到属性是 final 类型时，返回的就不是 mock 了！
        assert !Mockito.mockingDetails(deepOut.getAddress()).isMock();
        //这是 RETURNS_DEEP_STUBS 和 RETURNS_MOCKS 的区别
        assert deepOut.getAddress() == null;
        when(deepOut.getDeepInner().getAddress()).thenReturn("可以级联打桩");

//        //下面一行必须提出来，否则会报错
//        DeepInner deepInner = deepOut.getDeepInner();
//        assert Mockito.mockingDetails(deepInner).isMock();
//        doReturn("可以级联打桩").when(deepOut.getDeepInner()).getAddress();

        log.info("-------------------------------> CALLS_REAL_METHODS 方法默认调用真实方法，mock 之后返回 return 属性");
        RealMethodMock realMethodMock = mock(RealMethodMock.class, CALLS_REAL_METHODS);
        assert "real".equals(realMethodMock.real());
        doReturn("notReal").when(realMethodMock).real();
        assert "notReal".equals(realMethodMock.real());

        log.info("-------------------------------> RETURNS_SELF 方法返回自身");
        HttpBuilder builder = mock(HttpBuilder.class, RETURNS_SELF);
        HttpRequesterWithHeaders requester = new HttpRequesterWithHeaders(builder);
        doReturn("StatusCode: 200").when(builder).request();
        //这里内部调用 builder.withUrl(uri) 返回的是自身
        assert "StatusCode: 200".equals(requester.request("URI"));

        log.info("-------------------------------> SPY 方法默认调用真实方法，mock 之后返回 return 属性");
        RealMethodMock realMethodSpy = spy(RealMethodMock.class);
        assert "real".equals(realMethodSpy.real());
        doReturn("notReal").when(realMethodSpy).real();
        assert "notReal".equals(realMethodSpy.real());
    }
}
