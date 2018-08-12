package com.multithread.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ThreadLocal的内存管理
 * 说明：这个程序只打印10个create SimpleDateFormat，是因为线程池中只有10个线程，
 *  当第一次把runnable对象提交到线程池中时，线程池中的10个线程t1.get()都返回null，所以每个线程就会分别进行t1.set(),
 *  创建10个simpleDateFormat对象，所以会打印上述字符串10次。之后每个线程t1.get()都会返回相对应的simpleDateFormat对象
 *  不再重新创建，所以不会打印上述字符串了。
 *
 * @author W2
 * @date 2018/8/11 17:30
 */
public class ThreadLocalDemoGc {
    static volatile ThreadLocal<SimpleDateFormat> t1 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected void finalize() throws Throwable {
            System.out.println(this.toString() + "is gc");
        }
    };

    static volatile CountDownLatch cd = new CountDownLatch(10000);

    public static class ParseDate implements Runnable {
        int i = 0;
        public ParseDate(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            try {
                if(t1.get() == null) {
                    t1.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") {
                        @Override
                        protected void finalize() throws Throwable {
                            System.out.println(this.toString()+" is gc");
                        }
                    });
                    System.out.println(Thread.currentThread().getId()+": create SimpleDateFormat");
                }
                Date date = t1.get().parse("2018-08-11 17:36:"+i%60);
//                System.out.println("date = " + date);
            } catch (ParseException e) {
                e.printStackTrace();
            } finally {
               cd.countDown();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for(int i=0; i<10000; i++) {
            executorService.execute(new ParseDate(i));
        }
        cd.await();
        System.out.println("mission complete!!");
        t1 = null;
        System.gc();
        System.out.println("first GC complete!");

        t1 = new ThreadLocal<SimpleDateFormat>();
        cd = new CountDownLatch(10000);
        for(int i=0; i<10000; i++) {
            executorService.execute(new ParseDate(i));
        }
        cd.await();
        Thread.sleep(1000);
        System.gc();
        System.out.println("second GC complete!");

    }
}
