package com.demo.multithread;

/**
 * Created by ivan on 2015/6/25.
 * 不正确的发布对象
 */
public class Holder {
    private int n;

    public Holder(int n) {
        this.n = n;
    }

    public void assertSanity() {
        if(n!= n) {
            throw new AssertionError("Concurrent error.");
        }
    }
}
