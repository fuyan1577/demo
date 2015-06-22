package com.demo.jvm.init;

/**
 * Created by ivan on 2015/6/22.
 */
public interface ChildInterface extends  ParentInterface {
    int i  = ReferencedClass.getInfo("ChildInterface");
    public void getInfo();
}
