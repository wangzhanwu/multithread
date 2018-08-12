package com.multithread.demo;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 测试一下在多线程下产生随机数的性能问题
 * @author W2
 * @date 2018/8/12 10:12
 */
public class RandomThreadLocalDemo {

    public static final int GEN_COUNT = 1000000;
    public static final int THREAD_COUNT = 4;
    static ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
    public static Random rnd = new Random(123);

    public static ThreadLocal<Random> tRnd = new ThreadLocal<Random>() {
        @Override
        protected Random initialValue() {
            return new Random(123);
        }
    };

    public static class RndTask implements Callable<Long> {
        private int mod;
        public RndTask(int mod) {
            this.mod = mod;
        }

        public Random getRandom(int mod) {
            if(mod == 0) {
                return rnd;
            } else if(mod == 1) {
                return tRnd.get();
            } else {
                return null;
            }
        }

        @Override
        public Long call() throws Exception {
            long start = System.currentTimeMillis();
            for(int i=0; i < GEN_COUNT; i++) {
                getRandom(mod).nextInt();
            }
            long end = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName()+" spend"+ (end-start)+"ms");
            return end-start;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Future<Long>[] futs = new Future[THREAD_COUNT];
        for(int i=0; i<THREAD_COUNT; i++) {
            futs[i] = executorService.submit(new RndTask(0));
        }
        long totaltime = 0;
        for(int i=0; i<THREAD_COUNT; i++) {
            totaltime += futs[i].get();
        }
        System.out.println("多线程访问同一个Random实例：" + totaltime+"ms");

        Thread.sleep(2000);
        //ThreadLocal
        for(int i=0; i<THREAD_COUNT; i++) {
            futs[i] = executorService.submit(new RndTask(1));
        }
        totaltime = 0;
        for(int i=0; i<THREAD_COUNT; i++) {
            totaltime += futs[i].get();
        }
        System.out.println("使用ThreadLocal包装Random实例：" + totaltime+"ms");

    }
}
