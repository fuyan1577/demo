package com.demo.jvm.classloader;

/**
 * Created by ivan on 2015/6/22.
 */
public class CustomClassLoaderTest {
    public static void main(String args[]) {
        if (args.length <= 1) {
            System.out.println("Enter base path and greeter class name as args.");
            return;
        }
        CustomClassLoader customClassLoader = new CustomClassLoader(args[0]);
        for (int i = 1; i < args.length; i++) {
            try {
                Class c = customClassLoader.loadClass(args[i]);  //此处并不会触发接口的加载，也就是不会触发连接操作
                Object o = c.newInstance();
                Greeter greeter = (Greeter) o;
                greeter.greet();

                System.out.println("Class loader of greeter is: " + greeter.getClass().getClassLoader());
                System.out.println("Class loader of interface is: " + greeter.getClass().getInterfaces()[0].getClassLoader());   //此处打印AppClassLoader，只有在将对象强转为Greeter类型时才触发对接口的加载
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
