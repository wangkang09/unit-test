package com.wangkang.spock;

/**
 * @author wangkang
 * @date 2020/4/10- 22:42
 * @since
 */
public class Placeholder {

    public int holder(String str) {
        if ("11".equals(str)) {
            return 1;
        }
        if ("1-1".equals(str)) {
            return -1;
        }
        return 0;
    }
}
