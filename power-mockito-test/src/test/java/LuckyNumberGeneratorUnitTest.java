import com.wangkang.test.LuckyNumberGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.powermock.api.mockito.PowerMockito.*;

/**
 * 私有方法 mock！
 */
//此注解也可以开启 mockito 注解等功能
@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = "com.wangkang.test.*")
public class LuckyNumberGeneratorUnitTest {

    @Mock
    LuckyNumberGenerator luckyNumberGenerator;

    /**
     * 打桩 不带 ArgumentMatchers：任何参数时，输出
     */
    @Test
    public final void givenPrivateMethodWithReturn_whenUsingPowerMockito_thenCorrect() throws Exception {
        assert luckyNumberGenerator != null;

        LuckyNumberGenerator mock = spy(new LuckyNumberGenerator());
        when(mock, "getDefaultLuckyNumber").thenReturn(300);
        int result = mock.getLuckyNumber(null);
        assertEquals(300, result);

        //mock 模式也行
        mock = mock(LuckyNumberGenerator.class, InvocationOnMock::callRealMethod);
        when(mock, "getDefaultLuckyNumber").thenReturn(301);
        assertEquals(301, mock.getLuckyNumber(null));
    }

    /**
     * 打桩 带 ArgumentMatchers：指定输入参数时，输出
     */
    @Test
    public final void givenPrivateMethodWithArgumentAndReturn_whenUsingPowerMockito_thenCorrect() throws Exception {
        LuckyNumberGenerator mock = spy(new LuckyNumberGenerator());
        when(mock, "getComputedLuckyNumber", anyInt()).thenReturn(1);
        int result = mock.getLuckyNumber("Jack");
        assertEquals(1, result);

        //同理 mock 模式也可以
    }

    /**
     * 没有 打桩 默认调用实际方法
     */
    @Test
    public final void givenPrivateMethodWithNoArgumentAndReturn_whenUsingPowerMockito_thenCorrect() throws Exception {
        LuckyNumberGenerator mock = spy(new LuckyNumberGenerator());
        int result = mock.getLuckyNumber("Tyranosorous");
        verifyPrivate(mock).invoke("saveIntoDatabase", ArgumentMatchers.anyString());
        assertEquals(10000, result);
        //同理 mock 模式也行
    }

}