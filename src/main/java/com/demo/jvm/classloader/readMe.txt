如果在IDE里配置如下运行参数：
D:\SourceCode\demo\target\classes com.demo.jvm.classloader.Hello
则打印出来的是AppClassLoader(系统加载器)，因为这个类在classes目录下，所以由系统加载器加载

如果将class放到其他的目录，比如E盘，修改运行参数为：
E:/ com.demo.jvm.classloader.Hello
运行结果仍然是AppClassLoader，因为classes目录下仍然有这个class

将classes目录下的Hello.class删除，并在运行这个命令
E:/ com.demo.jvm.classloader.Hello
运行结果显示：
加载Hello类的是CustomClassLoader，加载其接口的仍热是AppClassLoader
