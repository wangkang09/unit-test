package com.wangkang.mockitotest;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

/**
 * @author wangkang
 * @date 2020/4/8- 22:12
 * @since
 */
@RunWith(MockitoJUnitRunner.class)
public class DoWhenTest {
    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
    @Mock
    List<String> list;
    @Test
    public void doReturnTest() {
        doReturn("return").when(list).get(anyInt());
        assert "return".equals(list.get(-1));
        doReturn("first","second","third").when(list).get(-1);

        //后面声明的会覆盖前面声明的桩
        assert "first".equals(list.get(-1));
        assert "return".equals(list.get(-2));
        assert "second".equals(list.get(-1));
        assert "third".equals(list.get(-1));
        assert "third".equals(list.get(-1));
    }

    @Test
    public void doThrowTest(){
        doThrow(new RuntimeException("测试doThrow"),new RuntimeException("测试doThrow2")).when(list).add("throw");

        try {
            list.add("throw");
            Assert.fail();
        } catch (Exception e) {
            assert "测试doThrow".equals(e.getMessage());
        }
        try {
            list.add("throw");
            Assert.fail();
        } catch (Exception e) {
            assert "测试doThrow2".equals(e.getMessage());
        }
        try {
            list.add("throw");
            Assert.fail();
        } catch (Exception e) {
            assert "测试doThrow2".equals(e.getMessage());
        }
    }


    @Mock
    DoNothing doNothing;
    /**
     * 只有 void 方法可以使用 doNothing!
     */
    @Test
    public void doNothingTest() {
        doNothing().doThrow(new RuntimeException("测试记录")).doCallRealMethod().when(doNothing).print("nothing");
        doNothing.print("nothing");
        assert !systemOutRule.getLog().contains("nothing");
        try {
            doNothing.print("nothing");
        } catch (Exception e) {
            assert "测试记录".equals(e.getMessage());
        }
        doNothing.print("nothing");
        assert systemOutRule.getLog().contains("nothing");
    }

    @Test
    public void doAnswerTest() {
        
    }
}
