package com.demo.jvm.init;

/**
 * Created by ivan on 2015/6/22.
 */
public class ParentClass {
    static int hours = (int)(Math.random() * 10);
    static {
        System.out.println("ParentClass was initialized.");
    }
}
