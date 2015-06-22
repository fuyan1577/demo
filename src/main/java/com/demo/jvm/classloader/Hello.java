package com.demo.jvm.classloader;

/**
 * Created by ivan on 2015/6/22.
 */
public class Hello implements Greeter {
    @Override
    public void greet() {
        System.out.println("Hello, world!");
    }
}
