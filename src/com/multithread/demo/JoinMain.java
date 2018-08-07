package com.multithread.demo;

/**
 * @Description join
 * @Author WangZhanWu
 * @Date 2018/7/25 16:12
 */
public class JoinMain {

    public volatile static int i = 0;
    public static class AddThread extends Thread {
        public void run() {
            for(i=0;i<100000000;i++);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AddThread thread = new AddThread();
        thread.start();
        thread.join();
        System.out.println(i);
    }
}
