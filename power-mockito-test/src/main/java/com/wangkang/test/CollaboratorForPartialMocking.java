package com.wangkang.test;

/**
 * @author wangkang
 * @date 2020-05-19
 * @since -
 */
public class CollaboratorForPartialMocking {
    public static String staticMethod() {
        return "Hello Baeldung!";
    }

    public final String finalMethod() {
        return "Hello Baeldung!";
    }

    private String privateMethod() {
        return "Hello Baeldung!";
    }

    public String privateMethodCaller() {
        return privateMethod() + " Welcome to the Java world.";
    }
}
