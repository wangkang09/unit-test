package com.wangkang.spock;

/**
 * @author wangkang
 * @date 2020/4/10- 22:30
 * @since
 */
public class MockException {

    public int exception(int age) {
        if (age == -1) {
            throw new RuntimeException("发生异常！");
        }
        return age;
    }
}
