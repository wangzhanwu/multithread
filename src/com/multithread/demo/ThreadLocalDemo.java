package com.multithread.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TheadLocal 实例
 * 通过ThreadLocal为每一个线程分配不同的对象，需要在应用层面保证。
 * ThreadLocal只起到了简单的容器作用。
 * @author W2
 * @date 2018/8/10 11:26
 */
public class ThreadLocalDemo {

//    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final ThreadLocal<SimpleDateFormat> t1 = new ThreadLocal<>();
    public static class ParseDate implements Runnable {
        int i = 0;
        public ParseDate(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            try {
//                Date t = sdf.parse("2018-08-10 11:32:" + i % 60);
                if(t1.get() == null) {
                    t1.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
                }
                Date t = t1.get().parse("2018-08-10 11:32:" + i % 60);
                System.out.println(i+":" + t);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService pools = Executors.newFixedThreadPool(10);
        for(int i=0;i<1000;i++) {
            pools.execute(new ParseDate(i));
        }
    }
}
