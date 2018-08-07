package com.multithread.demo;

import java.util.concurrent.Semaphore;

/**
 * @author WangZhanWu
 * @date 2018/8/3 10:02
 */
public class SemaphorDemo implements Runnable {

    final Semaphore semaphore = new Semaphore(5);

    @Override
    public void run() {
        try {
            semaphore.acquire();
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getId() + "Done!");
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SemaphorDemo semaphorDemo = new SemaphorDemo();
        Thread[] threads = new Thread[20];
        for(int i=0;i<20;i++) {
            threads[i] = new Thread(semaphorDemo);
        }
        for (int i = 0; i < 20; i++) {
            threads[i].start();
        }
    }
}
