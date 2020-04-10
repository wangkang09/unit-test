package com.wangkang.spock.test

import com.wangkang.spock.MockException
import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author wangkang
 * @date 2020/4/10- 22:29 
 * @since
 */
class MockExceptionTest extends Specification {

    @Unroll
    def "异常测试:#param,#out,#errorMs"() {
        given:
        def mockException = new MockException();
        expect:
        try {
            mockException.exception(param) == out
        } catch (Exception e) {
            e.getMessage().equals(errorMsg)
        }
        where:
        param | out  | errorMsg
        1     | 1    | null
        -1    | null | "发生异常！"
        0     | 0    | null
    }
}
