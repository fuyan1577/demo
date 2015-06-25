package com.demo.multithread;

/**
 * Created by ivan on 2015/6/25.
 */
public class HolderTest {
    private Holder holder;

    private static class HolderReaderThread extends  Thread {
        @Override
        public void run() {

        }
    }

    public static void  main(String args[]) {
        HolderTest holderTest = new HolderTest();
    }
}
