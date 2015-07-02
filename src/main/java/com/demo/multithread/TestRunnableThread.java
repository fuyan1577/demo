package com.demo.multithread;

/**
 * Created by ivan on 2015/7/2.
 */
public class TestRunnableThread {
    static class TestRunnable implements Runnable {
        private volatile int ticket = 10;

        @Override
        public void run() {
            System.out.println("Thread.currentThread() is: " + Thread.currentThread());
            for (int i = 0; i < 20; i++) {
                if (ticket == 5) {
                    Thread.currentThread().interrupt();
                }
                if (ticket > 0) {
                    System.out.println("卖票：ticket " + this.ticket--);
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TestRunnable runnable = new TestRunnable();
        //下面2个线程会共享数据，只会卖10张票
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        thread1.start();
        thread2.start();

        System.out.println("main thread is: " + Thread.currentThread());

        //Thread.sleep(1000L);

        System.out.println("thread 1 status is:" + thread1.isAlive());
        System.out.println("thread 2 status is:" + thread2.isAlive());
        thread1.interrupt();
        System.out.println(thread1);
        if (thread1.isInterrupted()) {
            System.out.println(" thread1 线程中断了");
        }

        if (thread2.isInterrupted()) {
            System.out.println(" thread2 线程中断了");
        }

        //子线程的中断信息不会传递给父线程
        if (Thread.currentThread().isInterrupted()) {
            System.out.println("main thread 线程中断了");
        }
    }
}
