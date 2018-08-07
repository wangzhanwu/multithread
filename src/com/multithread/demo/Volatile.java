package com.multithread.demo;

/**
 * @Description volatile关键字可以确保变量在线程间的有序性和可见性，但并不能保证一些符合操作的原子性
 * @Author WangZhanWu
 * @Date 2018/7/25 17:22
 */
public class Volatile {
    static volatile int i = 0;

    public static class PlusTask implements Runnable {
        @Override
        public void run() {
            for(int k = 0; k<10000; k++) {
                i++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[10];
        for(int i=0;i<threads.length;i++) {
            threads[i] = new Thread(new PlusTask());
            threads[i].start();
        }
        for(int i = 0;i<10;i++) {
            threads[i].join();
        }
        System.out.println(i);
    }
}
