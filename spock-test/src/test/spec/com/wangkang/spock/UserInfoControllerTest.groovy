package com.wangkang.spock

import org.spockframework.runtime.ConditionFailedWithExceptionError
import spock.lang.Specification

/**
 * @author wangkang
 * @date 2020/4/11- 11:46 
 * @since
 */
class UserInfoControllerTest extends Specification {
    def "GetOrderListDemo"() {
        given:
        def service = Mock(UserInfoServiceImpl);
        def controller = new UserInfoController(service);
        //这里的参数值是随意取的，主要是为了区分每次调用返回的值
        service.getUserInfo(1) >> null
        service.getUserInfo(2) >> 2
        service.getUserInfo(-1) >> { throw new IllegalArgumentException() }
        service.getUserInfo(-2) >> { throw new IllformedLocaleException() }
        expect:
        try {
            assert controller.getOrderListDemo(request, param2, param2, param2) == response;
        } catch (ConditionFailedWithExceptionError e) {
            //这里捕获的是 ConditionFailedWithExceptionError 包装后的业务抛出的异常
            assert request == null;
            assert "req 对象是null".equals(e.getCause().getMessage());
        }
        where:
        request | param2 || response
        1       | null    | null
        2       | null    | 2
        -1      | null    | -1
        -2      | null    | -2
        null    | null    | null
    }
}

