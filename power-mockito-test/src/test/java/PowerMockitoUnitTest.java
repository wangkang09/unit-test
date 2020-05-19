import com.wangkang.test.CollaboratorForPartialMocking;
import com.wangkang.test.CollaboratorWithFinalMethods;
import com.wangkang.test.CollaboratorWithStaticMethods;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;

/**
 * @author wangkang
 * @date 2020-05-19
 * @since -
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = "com.wangkang.test.*")
public class PowerMockitoUnitTest {

    /**
     * 测试 mock 构造器，即 final 方法
     */
    @Test
    public void givenFinalMethods_whenUsingPowerMockito_thenCorrect() throws Exception {
        CollaboratorWithFinalMethods mock = mock(CollaboratorWithFinalMethods.class);
        whenNew(CollaboratorWithFinalMethods.class).withNoArguments().thenReturn(mock);
        CollaboratorWithFinalMethods collaborator = new CollaboratorWithFinalMethods();
        //验证调用了无参
        verifyNew(CollaboratorWithFinalMethods.class).withNoArguments();

        doReturn("Hello Baeldung!").when(collaborator).helloMethod();
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
    public void givenStaticMethods_whenUsingPowerMockito_thenCorrect() {
        mockStatic(CollaboratorWithStaticMethods.class);

        when(CollaboratorWithStaticMethods.firstMethod(Mockito.anyString())).thenReturn("Hello Baeldung!");
        when(CollaboratorWithStaticMethods.secondMethod()).thenReturn("Nothing special");
        when(CollaboratorWithStaticMethods.thirdMethod()).thenThrow(new RuntimeException("dd"));

        String firstWelcome1 = CollaboratorWithStaticMethods.firstMethod("dds");
        String firstWelcome2 = CollaboratorWithStaticMethods.secondMethod();
        assertEquals("Hello Baeldung!", firstWelcome1);
        assertEquals("Nothing special", firstWelcome2);

        try {
            CollaboratorWithStaticMethods.thirdMethod();
            Assert.fail();
        } catch (Exception e) {
            assert e.getMessage().equals("dd");
        }
    }

    @Test
    public void givenPartialMocking_whenUsingPowerMockito_thenCorrect() throws Exception {
        String returnValue;

        spy(CollaboratorForPartialMocking.class);
        when(CollaboratorForPartialMocking.staticMethod()).thenReturn("I am a static mock method.");
        returnValue = CollaboratorForPartialMocking.staticMethod();
        CollaboratorForPartialMocking.staticMethod();
        assertEquals("I am a static mock method.", returnValue);

        CollaboratorForPartialMocking collaborator = new CollaboratorForPartialMocking();
        CollaboratorForPartialMocking mock = spy(collaborator);

        when(mock.finalMethod()).thenReturn("I am a final mock method.");
        returnValue = mock.finalMethod();
        verify(mock).finalMethod();
        assertEquals("I am a final mock method.", returnValue);

        when(mock, "privateMethod").thenReturn("I am a private mock method.");
        returnValue = mock.privateMethodCaller();
        verifyPrivate(mock).invoke("privateMethod");
        assertEquals("I am a private mock method. Welcome to the Java world.", returnValue);
    }
}
