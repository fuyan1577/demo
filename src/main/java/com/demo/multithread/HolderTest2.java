package com.demo.multithread;

import java.util.Random;
import java.util.Vector;
import java.util.concurrent.*;

/**
 * Created by ivan on 2015/6/25.
 * 和HolderTest的差别是此处将对象放入了线程安全的容器，通过这样的方式可以确保其他线程读到正确的值
 */
public class HolderTest2 {
    private static String MAP_KEY = "TEST";

    private static BlockingQueue<Holder> blockingQueue = new LinkedBlockingQueue<Holder>();

    private static Boolean stopGenerate = Boolean.FALSE;

    private static class HolderGenerateThread extends Thread {
        BlockingQueue<Holder> blockingQueue;

        public HolderGenerateThread(BlockingQueue<Holder> blockingQueue) {
            this.blockingQueue = blockingQueue;
        }

        @Override
        public void run() {
            while (!stopGenerate) {
                Random random = new Random(100);
                int num = random.nextInt();
                Holder holder = new Holder(num);
                try {
                    blockingQueue.put(holder);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }

    private static class HolderReaderThread extends Thread {
        BlockingQueue<Holder> blockingQueue;

        public HolderReaderThread(BlockingQueue<Holder> blockingQueue) {
            this.blockingQueue = blockingQueue;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Holder holder = blockingQueue.take();
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
        HolderTest2 holderTest = new HolderTest2();
        for (int i = 0; i < 2; i++) {
            new HolderGenerateThread(blockingQueue).start();
            new HolderReaderThread(blockingQueue).start();
        }
    }
}
