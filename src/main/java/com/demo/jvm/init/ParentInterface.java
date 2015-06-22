package com.demo.jvm.init;

/**
 * Created by ivan on 2015/6/22.
 */
public interface ParentInterface {
    int i = 1;

    int info  = ReferencedClass.getInfo("ParentInterface");  //
}
