package com.demo.multithread;

import java.util.Random;

/**
 * Created by ivan on 2015/6/25.
 */
public class HolderTest {
    private static Holder holder;

    //即使使用volatile变量也不能确保不抛出异常
    private volatile static int volatileVariable;

    private static Boolean stopGenerate = Boolean.FALSE;

    private static class HolderGenerateThread extends Thread {
        @Override
        public void run() {
            while (!stopGenerate) {
                Random random = new Random(100);
                int num = random.nextInt();
                holder = new Holder(num);
                volatileVariable = 1;
            }
        }
    }

    private static class HolderReaderThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println(volatileVariable);
                    holder.assertSanity();
                } catch (Exception e) {
                    System.out.println("throw exception");
                    stopGenerate = Boolean.TRUE;
                    break;
                }
            }
        }

    }

    public static void main(String args[]) throws Exception {
        HolderTest holderTest = new HolderTest();
        for (int i = 0; i < 2; i++) {
            new HolderGenerateThread().start();
            //如果添加如下语句，则不会很容易出现异常
            //Thread.sleep(1000L);
            new HolderReaderThread().start();
        }
    }
}