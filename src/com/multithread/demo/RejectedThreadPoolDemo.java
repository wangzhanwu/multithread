package com.multithread.demo;

import java.util.concurrent.*;

/**
 * 线程池拒绝策略
 *
 * @author W2
 * @date 2018/8/6 13:43
 */
public class RejectedThreadPoolDemo {
    public static class MyTask implements Runnable {
        @Override
        public void run() {
            System.out.println(System.currentTimeMillis() + ": Thread ID :" + Thread.currentThread().getId());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyTask myTask = new MyTask();
        ExecutorService threadPool = new ThreadPoolExecutor(5, 5, 0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<Runnable>(10),
                Executors.defaultThreadFactory(),
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        System.out.println(r.toString() + "is rejected");
                    }
                });
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            threadPool.submit(myTask);
            Thread.sleep(10);
        }
    }
}
