package com.multithread.demo;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁
 * @author WangZhanWu
 * @date 2018/8/2 16:28
 */
public class ReenterLock implements Runnable{
    public static ReentrantLock lock = new ReentrantLock();
    public static int i = 0;

    @Override
    public void run() {
        for(int j=0;j<100000000;j++) {
            lock.lock();
            try{
                i++;
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReenterLock reenterLock = new ReenterLock();
        Thread t1 = new Thread(reenterLock);
        Thread t2 = new Thread(reenterLock);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }
}
