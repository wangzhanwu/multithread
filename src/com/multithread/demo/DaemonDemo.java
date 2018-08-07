package com.multithread.demo;

/**
 * 守护线程：用户线程执行完毕之后，守护线程也会结束运行，此时虚拟机就会退出。
 * @author WangZhanWu
 * @date 2018/8/2 11:09
 */
public class DaemonDemo {
    public static class DaemonT extends Thread {
        @Override
        public void run() {
            while(true) {
                System.out.println("I am alive");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new DaemonT();
        thread.setDaemon(true);//将线程thread设置为守护线程。
        thread.start();

        Thread.sleep(2000);
    }
}
