package com.multithread.demo;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 计划任务
 * @author WangZhanWu
 * @date 2018/8/4 19:55
 */
public class ScheduledExecutorServiceDemo {

    public static class ScheduledDemo implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(System.currentTimeMillis()/1000);
        }
    }

    public static void main(String[] args) {
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(10);
        ScheduledDemo sd = new ScheduledDemo();
//        ses.scheduleAtFixedRate(sd, 0, 2, TimeUnit.SECONDS);
        ses.scheduleWithFixedDelay(sd, 0, 2, TimeUnit.SECONDS);
    }

}
