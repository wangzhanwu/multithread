package com.multithread.demo;

import java.security.PublicKey;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生产者消费者
 * @author W2
 * @date 2018/8/8 13:53
 */
public class ProducterAndConsumer {

    public static class Producter extends Thread {
        final BlockingQueue<String> queue;
        static AtomicInteger count = new AtomicInteger();
        public Producter(BlockingQueue<String> queue, String threadName) {
            super(threadName);
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                while(true) {
                    queue.put(produce());
                    Thread.sleep(2000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        private String produce() {
            String productName = "产品" + count.getAndIncrement();
            System.out.println("我是生产者" + Thread.currentThread().getName()+"，生产商品："+productName);
            return productName;
        }
    }


    public static class Consumer extends Thread {
        final BlockingQueue<String> queue;
        public Consumer(BlockingQueue<String> queue, String threadName) {
            super(threadName);
            this.queue = queue;
        }

        public void run() {
            try {
                while(true) {
                    System.out.println("我是消费者"+Thread.currentThread().getName()+",消费商品："+queue.take());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);

        //通过线程池来执行生产者消费者线程
        ExecutorService pools = Executors.newFixedThreadPool(5);
        for(int i=0; i<2; i++) {
            pools.execute( new Producter(queue, "PRODUCTER"+i));
        }
        for(int j=0; j<3; j++) {
            pools.execute(new Consumer(queue, "CONSUMER"+j));
        }
    }

}
