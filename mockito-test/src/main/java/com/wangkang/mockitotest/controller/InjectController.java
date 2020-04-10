package com.wangkang.mockitotest.controller;

import com.wangkang.mockitotest.service.InjectService;
import lombok.Data;

/**
 * @author wangkang
 * @date 2020/4/7- 22:59
 * @since
 */
@Data
public class InjectController {
    private InjectService injectService;
}
