package com.demo.multithread;

/**
 * Created by ivan on 2015/7/2.
 */
public class TestThread extends Thread {
    private int ticket = 10;
    private int num = 0;

    public TestThread(int num) {
        this.num = num;
    }

    public void run() {
        for (int i = 0; i < 20; i++) {
            if (this.ticket > 0) {
                System.out.println("Thread:" + num + " 卖票：ticket " + this.ticket--);
            }
        }
    }

    public static void main(String[] args) {
        TestThread test1 = new TestThread(1);
        TestThread test2 = new TestThread(2);
        //每个线程都会卖10张
        test1.start();
        test2.start();
    }
}
