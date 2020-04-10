package com.wangkang.mockitotest;

import com.wangkang.mockitotest.entity.DeepOut;
import com.wangkang.mockitotest.entity.RealMethodMock;
import com.wangkang.mockitotest.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * @author wangkang
 * @date 2020/4/7- 22:18
 * @since
 */
@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class AnnotationTest {
//    @Rule
//    public MockitoRule mockito = MockitoJUnit.rule();

//    @Before
//    public void before() {
//        MockitoAnnotations.initMocks(this);
//    }

    @Mock
    List<User> mockedList;
    @Mock(answer = Answers.RETURNS_DEFAULTS)
    List<User> mockedListDefault;
    @Mock(answer = Answers.RETURNS_SMART_NULLS)
    List<User> mockedListSMART;
    @Mock(answer = Answers.RETURNS_MOCKS)
    List<User> mockedListMOCKS;
    @Mock(answer = Answers.RETURNS_MOCKS)
    DeepOut deepOutMOCKS;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    DeepOut deepOutDEEP_STUBS;
    @Mock(answer = Answers.CALLS_REAL_METHODS)
    RealMethodMock realMethodMock;
    @Mock(answer = Answers.RETURNS_SELF)
    HttpRequesterWithHeaders.HttpBuilder builder;
    @Spy
    RealMethodMock realMethodSpy;

    @Test
    public void test() {
        assert mockedList.get(0) == null;
        mockedList = mock(LinkedList.class, RETURNS_DEFAULTS);
        assert mockedList.get(0) == null;
        log.info("-------------------------------> mock 方法默认返回 null");
        try {
            mockedListDefault.get(0).getAge();
            Assert.fail();
        } catch (Exception e) {
            assert e.getMessage() == null;
        }

        log.info("-------------------------------> RETURNS_SMART_NULLS 方法返回 SMART_NULLS");
        try {
            mockedListSMART.get(0).getAge();
            Assert.fail();
        } catch (Exception e) {
            assert e.getMessage().contains("cannot be cast to class com.wangkang.mockitotest.entity.User");
        }

        log.info("-------------------------------> RETURNS_MOCKS 方法如果是引用对象返回 mock，如果是 primitive 返回 java 默认值，如果是 final 返回 null");
        assert Mockito.mockingDetails(mockedListMOCKS.get(0)).isMock();
        //引用类型返回 mock。final 类返回 null
        assert Mockito.mockingDetails(deepOutMOCKS.getDeepInner()).isMock();
        //默认情况返回 空字符串、false、0 等 java 默认初始化值
        assert "".equals(deepOutMOCKS.getAddress());

        log.info("-------------------------------> RETURNS_DEEP_STUBS 方法如果是引用对象返回 mock，如果是 primitive or a final class 返回 null");
        //RETURNS_DEEP_STUBS 适用于 mock 某个对象内部对象的方法返回值
        //DEEP_STUBS 只能通过 when().then() 形式，不可使用 do().when()形式
        assert Mockito.mockingDetails(deepOutDEEP_STUBS.getDeepInner()).isMock();
        //直到属性是 final 类型时，返回的就不是 mock 了！
        assert !Mockito.mockingDetails(deepOutDEEP_STUBS.getAddress()).isMock();
        //这是 RETURNS_DEEP_STUBS 和 RETURNS_MOCKS 的区别
        assert deepOutDEEP_STUBS.getAddress() == null;
        when(deepOutDEEP_STUBS.getDeepInner().getAddress()).thenReturn("可以级联打桩");

//        //下面一行必须提出来，否则会报错
//        DeepInner deepInner = deepOut.getDeepInner();
//        assert Mockito.mockingDetails(deepInner).isMock();
//        doReturn("可以级联打桩").when(deepOut.getDeepInner()).getAddress();
        assert "可以级联打桩".equals(deepOutDEEP_STUBS.getDeepInner().getAddress());

        log.info("-------------------------------> CALLS_REAL_METHODS 方法默认调用真实方法，mock 之后返回 return 属性");
        assert "real".equals(realMethodMock.real());
        doReturn("notReal").when(realMethodMock).real();
        assert "notReal".equals(realMethodMock.real());

        log.info("-------------------------------> RETURNS_SELF 方法返回自身");
        HttpRequesterWithHeaders requester = new HttpRequesterWithHeaders(builder);
        doReturn("StatusCode: 200").when(builder).request();
        //这里内部调用 builder.withUrl(uri) 返回的是自身
        assert "StatusCode: 200".equals(requester.request("URI"));

        log.info("-------------------------------> SPY 方法默认调用真实方法，mock 之后返回 return 属性");
        assert "real".equals(realMethodSpy.real());
        doReturn("notReal").when(realMethodSpy).real();
        assert "notReal".equals(realMethodSpy.real());
    }
}
