package com.wangkang.spock.test

import com.wangkang.spock.Placeholder
import spock.lang.Specification

/**
 * @author wangkang
 * @date 2020/4/10- 22:41 
 * @since
 */
class PlaceholderTest extends Specification {

    def "占位符测试"() {
        given:
        def holder = new Placeholder()
        expect:
        holder.holder("1${param}") == out
        where:
        param || out
        "1"    | 1
        "-1"   | -1
        "0"    | 0
    }
}
