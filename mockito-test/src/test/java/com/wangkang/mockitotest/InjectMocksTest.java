package com.wangkang.mockitotest;

import com.wangkang.mockitotest.controller.InjectController;
import com.wangkang.mockitotest.entity.DeepInner;
import com.wangkang.mockitotest.entity.DeepOut;
import com.wangkang.mockitotest.mapper.InjectMapper;
import com.wangkang.mockitotest.service.InjectService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.doReturn;

/**
 * @author wangkang
 * @date 2020/4/7- 22:54
 * @since
 */
@RunWith(MockitoJUnitRunner.class)
public class InjectMocksTest {

    @InjectMocks
    DeepOut deepOut;
    @Mock
    DeepInner deepInner;
    @Test
    public void test() {
        doReturn("return").when(deepInner).getAddress();
        "return".equals(deepOut.getDeepInner().getAddress());
    }

    @InjectMocks
    InjectService injectService;
    @InjectMocks
    InjectController injectController;
    @Mock
    InjectMapper injectMapper;
    @Test
    public void inject() {
        //因为 injectService 使用了 @InjectMocks 注解，而不能使用 @Mock 注解，则 injectService 不能自动注入到 injectController 中，通过以下方法注入
        ReflectionTestUtils.setField(injectController, "injectService", injectService);
        doReturn("result").when(injectMapper).getName();
        "return".equals(injectController.getInjectService().getInjectMapper().getName());
    }
}
