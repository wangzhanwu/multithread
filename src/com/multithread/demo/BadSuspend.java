package com.multithread.demo;

/**
 * @Description  线程挂起suspend导致死锁
 * @Author WangZhanWu
 * @Date 2018/7/25 15:11
 */
public class BadSuspend {
    public static Object u = new Object();

    public static class ChangeObjectThread extends Thread {
        public ChangeObjectThread(String name) {
            super.setName(name);
        }

        public void run() {
            synchronized (u) {
                System.out.println("in "+ getName());
                Thread.currentThread().suspend();
            }
        }
    }
    static ChangeObjectThread t1 = new ChangeObjectThread("t1");
    static ChangeObjectThread t2 = new ChangeObjectThread("t2");

    public static void main(String[] args) throws InterruptedException {
        t1.start();
        Thread.sleep(2000);
        t2.start();
        t1.resume();
        t2.resume();
        t1.join();
        t2.join();
    }
}
