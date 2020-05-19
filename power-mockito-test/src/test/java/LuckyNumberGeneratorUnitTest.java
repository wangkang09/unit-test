import com.wangkang.test.LuckyNumberGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.*;

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
        doReturn(300).when(mock,"getDefaultLuckyNumber");
        int result = mock.getLuckyNumber(null);
        assertEquals(300, result);
    }

    /**
     * 打桩 带 ArgumentMatchers：指定输入参数时，输出
     */
    @Test
    public final void givenPrivateMethodWithArgumentAndReturn_whenUsingPowerMockito_thenCorrect() throws Exception {
        LuckyNumberGenerator mock = spy(new LuckyNumberGenerator());
        doReturn(1).when(mock, "getComputedLuckyNumber", ArgumentMatchers.anyInt());
        int result = mock.getLuckyNumber("Jack");
        assertEquals(1, result);
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
    }

}