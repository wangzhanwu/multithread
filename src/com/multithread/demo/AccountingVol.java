package com.multithread.demo;

/**
 * 线程安全和synchronized
 * @author WangZhanWu
 * @date 2018/8/2 13:53
 */
public class AccountingVol implements  Runnable{
    static AccountingVol instance = new AccountingVol();
    static volatile int i = 0;
    public synchronized void increase() {
        i++;
    }

    @Override
    public void run() {
        for(int j=0;j<10000000;j++) {
            increase();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }
}
