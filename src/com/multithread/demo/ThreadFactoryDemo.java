package com.multithread.demo;

import java.util.concurrent.*;

/**
 * 线程工厂
 * @author W2
 * @date 2018/8/6 15:06
 */
public class ThreadFactoryDemo {
    public static class MyTask implements Runnable {
        @Override
        public void run() {
            System.out.println(System.currentTimeMillis() + "thread id : " + Thread.currentThread().getId());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyTask myTask = new MyTask();
        ExecutorService executorService = new ThreadPoolExecutor(5, 5,
                0L, TimeUnit.MILLISECONDS,
                new SynchronousQueue<Runnable>(),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r);
                        thread.setDaemon(true);
                        System.out.println("create " + thread);
                        return thread;
                    }
        });
        for (int i = 0; i < 5; i++) {
            executorService.submit(myTask);
        }
        Thread.sleep(2000);
    }
}
