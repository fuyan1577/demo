package com.demo.jvm.init;

/**
 * Created by ivan on 2015/6/22.
 */
public class ChildImplementation extends ParentClass implements  ChildInterface {

    @Override
    public void getInfo() {
        System. out.println("getInfo in ChildImplementation was invoked.");
    }
}
