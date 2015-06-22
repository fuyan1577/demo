package com.demo.jvm.init;

/**
 * Created by ivan on 2015/6/22.
 */
public class ReferencedClass {
    static {
        System.out.println("ReferencedClass was initialized.");
    }

    static int getInfo(String s) {
        System.out.println(s + "was initialized.");
        return 1;
    }
}
