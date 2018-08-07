package com.multithread.demo;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author WangZhanWu
 * @date 2018/8/2 17:20
 */
public class TryLock implements Runnable {
    public static ReentrantLock lock1 = new ReentrantLock();
    public static ReentrantLock lock2 = new ReentrantLock();

    int lock;

    public TryLock(int lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        if(lock == 1) {
            while(true) {
                if(lock1.tryLock()) {
                   try {
                       try {
                           Thread.sleep(500);
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }
                       if(lock2.tryLock()) {
                           try {
                               System.out.println(Thread.currentThread().getId()+ "My Job done");
                           } finally {
                               lock2.unlock();
                           }
                       }
                   } finally {
                       lock1.unlock();
                   }
                }
            }
        } else {
            while(true) {
                if(lock2.tryLock()) {
                    try {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(lock1.tryLock()) {
                            try {
                                System.out.println(Thread.currentThread().getId()+"My Job done");
                            } finally {
                                lock1.unlock();
                            }
                        }
                    } finally {
                        lock2.unlock();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        TryLock tryLock1 = new TryLock(1);
        TryLock tryLock2 = new TryLock(2);

        Thread t1 = new Thread(tryLock1);
        Thread t2 = new Thread(tryLock2);
        t1.start();
        t2.start();

    }

}
