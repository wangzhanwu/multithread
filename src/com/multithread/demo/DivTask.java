package com.multithread.demo;

import com.sun.org.apache.xpath.internal.operations.Div;

import java.util.concurrent.*;

/**
 * @author W2
 * @date 2018/8/7 9:47
 */
public class DivTask implements Runnable {
    int a, b;
    public DivTask(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public void run() {
        double c = a/b;
        System.out.println(c);
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 0L, TimeUnit.MILLISECONDS,
                    new SynchronousQueue<Runnable>());
        for(int i=0; i<5; i++) {
//            Future future = poolExecutor.submit(new DivTask(100, i));
//            System.out.println("future.get() = " + future.get());
            poolExecutor.execute(new DivTask(100, i));
        }
    }
}
