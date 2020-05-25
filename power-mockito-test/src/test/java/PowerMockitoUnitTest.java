import com.wangkang.test.CollaboratorForPartialMocking;
import com.wangkang.test.CollaboratorWithFinalMethods;
import com.wangkang.test.CollaboratorWithStaticMethods;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.withSettings;
import static org.powermock.api.mockito.PowerMockito.*;

/**
 * mock 静态方法、final 方法
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = "com.wangkang.test.*")
public class PowerMockitoUnitTest {
    /**
     * 测试 mock 构造器，即 final 方法
     */
    @Test
    public void givenFinalMethods_whenUsingPowerMockito_thenCorrect() throws Exception {
        System.out.println(PowerMockitoUnitTest.class.getName());
        CollaboratorWithFinalMethods mock = mock(CollaboratorWithFinalMethods.class);
        whenNew(CollaboratorWithFinalMethods.class).withNoArguments().thenReturn(mock);
        CollaboratorWithFinalMethods collaborator = new CollaboratorWithFinalMethods();
        //验证调用了无参
        verifyNew(CollaboratorWithFinalMethods.class).withNoArguments();
        doReturn("Hello Baeldung!").when(collaborator, "helloMethod");
        String welcome = collaborator.helloMethod();
        //验证调用
        verify(collaborator).helloMethod();
        //验证结果
        assertEquals("Hello Baeldung!", welcome);
    }

    /**
     * 测试 静态方法
     */
    @Test
    public void givenStaticMethods_whenUsingPowerMockito_thenCorrect() throws Exception {
        //mock
        mockStatic(CollaboratorWithStaticMethods.class, withSettings().defaultAnswer(InvocationOnMock::callRealMethod));
        //以上相当于：spy(CollaboratorForPartialMocking.class);

        //stubbing
        doReturn("Hello Baeldung!").when(CollaboratorWithStaticMethods.class, "firstMethod", any());
        doReturn("Nothing special").when(CollaboratorWithStaticMethods.class, "secondMethod");
        String firstWelcome1 = CollaboratorWithStaticMethods.firstMethod("dds");
        String firstWelcome2 = CollaboratorWithStaticMethods.secondMethod();
        assertEquals("Hello Baeldung!", firstWelcome1);
        assertEquals("Nothing special", firstWelcome2);

        //callRealMethod！！
        assert CollaboratorWithStaticMethods.thirdMethod().equals("Hello no one again!");
        //这个 doReturn 形式不好办
        when(CollaboratorWithStaticMethods.thirdMethod()).thenReturn("thirdFirstReturn", "thirdSecondReturn").thenThrow(new RuntimeException("dd"));
        //验证串联式打桩声明，不能直接 assert 必须分两步
        String first = CollaboratorWithStaticMethods.thirdMethod();
        assert first.equals("thirdFirstReturn");
        String second = CollaboratorWithStaticMethods.thirdMethod();
        assert second.equals("thirdSecondReturn");

        try {
            CollaboratorWithStaticMethods.thirdMethod();
            Assert.fail();
        } catch (Exception e) {
            assert e.getMessage().equals("dd");
        }
    }

    @Test
    public void givenPartialMocking_whenUsingPowerMockito_thenCorrect() throws Exception {
        //spy static
        String returnValue;
        spy(CollaboratorForPartialMocking.class);
        when(CollaboratorForPartialMocking.staticMethod()).thenReturn("I am a static mock method.");
        returnValue = CollaboratorForPartialMocking.staticMethod();
        assertEquals("I am a static mock method.", returnValue);

        //spy 方法得到 mock 对象必须传入一个创建的实例
        //spy final、private 方法
        CollaboratorForPartialMocking collaborator = new CollaboratorForPartialMocking();
        CollaboratorForPartialMocking mock = spy(collaborator);

        when(mock.finalMethod()).thenReturn("I am a final mock method.");
        returnValue = mock.finalMethod();
        //验证调用了 finalMethod 方法
        verify(mock).finalMethod();
        assertEquals("I am a final mock method.", returnValue);

        when(mock, "privateMethod").thenReturn("I am a private mock method.");
        returnValue = mock.privateMethodCaller();
        verifyPrivate(mock).invoke("privateMethod");
        assertEquals("I am a private mock method. Welcome to the Java world.", returnValue);
    }
}
