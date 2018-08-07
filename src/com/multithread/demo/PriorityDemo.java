package com.multithread.demo;

/**
 * 线程优先级
 * @author WangZhanWu
 * @date 2018/8/2 11:27
 */
public class PriorityDemo {
    public static class HighPriority extends Thread {
        static int count  = 0;
        public void run() {
            while(true) {
                synchronized (PriorityDemo.class) {
                    count++;
                    if(count > 100000000) {
                        System.out.println("HighPriority is complete");
                        break;
                    }
                }
            }
        }
    }
    public static class LowPriority extends Thread {
        static int count  = 0;
        public void run() {
            while(true) {
                synchronized (PriorityDemo.class) {
                    count++;
                    if(count > 100000000) {
                        System.out.println("LowPriority is complete");
                        break;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread high = new HighPriority();
        Thread low = new LowPriority();

        low.setPriority(Thread.MIN_PRIORITY);
        high.setPriority(Thread.MAX_PRIORITY);

        low.start();
        high.start();
    }
}
