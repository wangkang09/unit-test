package com.wangkang.spock.test

import com.wangkang.spock.Mock1
import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author wangkang
 * @date 2020/4/10- 22:13 
 * @since
 */
class MockTest extends Specification {

    @Unroll
    def "spock 的 Mock 测试#param,#out1,#out2,#out3"() {
        given:
        def mock1 = Mock(Mock1)
        mock1.t1(_) >>> [12, 13, 14]
        expect:
        mock1.t1(param) == out1
        mock1.t1(param) == out2
        mock1.t1(param) == out3
        where:
        param || out1 | out2 | out3
        -1     | 12   | 13   | 14
        1      | 12   | 13   | 14
        0      | 12   | 13   | 14
    }
}
