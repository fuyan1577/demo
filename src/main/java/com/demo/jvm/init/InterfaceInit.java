package com.demo.jvm.init;

/**
 * Created by ivan on 2015/6/22.
 */
public class InterfaceInit {
    public static void main(String args[]) {
        int i = ParentInterface.i;  //不会触发ParentInterface的初始化，因为i是常量
        int hours = ChildImplementation.hours;  //此处只会触发父类ParentClass的初始化，不会触发ChildImplementation的初始化
        ChildInterface childInterface = new ChildImplementation();  //此处不会触发ParentInterface和ChildInterface的初始化
        childInterface.getInfo();
        int variableInInterface = childInterface.i;  //此处会触发ChildInterface的初始化，但不会触发其父接口的初始化
    }
}
