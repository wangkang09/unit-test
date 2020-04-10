package com.wangkang.mockitotest;

import com.wangkang.mockitotest.controller.AnnotationController;
import com.wangkang.mockitotest.service.AnnotationService;
import com.wangkang.mockitotest.service.AnnotationSpyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wangkang
 * @date 2020/4/7- 23:05
 * @since
 */
@SpringBootTest(classes = MockitoTestApplication.class)
@RunWith(SpringRunner.class)
public class AnnotationBeanTest {
    //创建一个mock 并注入到容器中，如果没有注入的话，启动就会报错 require true
    @MockBean
    AnnotationService annotationService;
    @SpyBean
    AnnotationSpyService annotationSpyService;
    @Autowired
    AnnotationController annotationController;

    @Test
    public void mockTest() {
        annotationController.getAnnotationService().setName("wk");
        assert annotationController.getAnnotationService() == annotationService;
        assert Mockito.mockingDetails(annotationService).isMock();
        assert annotationController.getAnnotationService().getName() == null;
    }

    @Test
    public void spyTest() {
        annotationController.getAnnotationSpyService().setName("wk");
        assert annotationController.getAnnotationSpyService() == annotationSpyService;
        assert Mockito.mockingDetails(annotationSpyService).isMock();
        assert "wk".equals(annotationController.getAnnotationSpyService().getName());
    }
}
