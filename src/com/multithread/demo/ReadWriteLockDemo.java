package com.multithread.demo;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.IntToDoubleFunction;

/**
 * ReadWriteLock读写锁
 * @author WangZhanWu
 * @date 2018/8/3 10:53
 */
public class ReadWriteLockDemo {
    private static Lock lock = new ReentrantLock();
    private static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static Lock readLock = readWriteLock.readLock();
    private static Lock writeLock = readWriteLock.writeLock();
    private int value;

    public Object handleRead(Lock lock) throws InterruptedException {
       try {
           lock.lock();
           Thread.sleep(1000);
           return value;
       } finally {
           lock.unlock();
       }
    }

    public void handleWrite(Lock lock, int index) throws InterruptedException {
        try {
            lock.lock();
            Thread.sleep(1000);
            value = index;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        final ReadWriteLockDemo readWriteLockDemo = new ReadWriteLockDemo();
        Runnable readRunnable = new Runnable() {
            @Override
            public void run() {
                try {
//                    readWriteLockDemo.handleRead(readLock);
                    readWriteLockDemo.handleRead(lock);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Runnable writeRunnable = new Runnable() {
            @Override
            public void run() {
                try {
//                    readWriteLockDemo.handleWrite(writeLock, new Random().nextInt());
                    readWriteLockDemo.handleWrite(lock, new Random().nextInt());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        for (int i=0; i < 18; i++) {
            new Thread(readRunnable).start();
        }
        for(int i=18; i < 20; i++) {
            new Thread(writeRunnable).start();
        }
    }
}
