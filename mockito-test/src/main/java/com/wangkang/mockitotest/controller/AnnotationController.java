package com.wangkang.mockitotest.controller;

import com.wangkang.mockitotest.service.AnnotationService;
import com.wangkang.mockitotest.service.AnnotationSpyService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author wangkang
 * @date 2020/4/7- 23:06
 * @since
 */
@Controller
@Data
public class AnnotationController {
    @Autowired
    private AnnotationService annotationService;

    @Autowired
    private AnnotationSpyService annotationSpyService;
}
