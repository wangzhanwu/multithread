package com.multithread.demo;

/**
 * 线程组
 * @author WangZhanWu
 * @date 2018/8/2 10:52
 */
public class ThreadGroupTest implements Runnable{

    public static void main(String[] args) {
        ThreadGroup tg = new ThreadGroup("PrintGroup");
        Thread t1 = new Thread(tg, new ThreadGroupTest(), "T1");
        Thread t2 = new Thread(tg, new ThreadGroupTest(), "T2");
        t1.start();
        t2.start();
        System.out.println(tg.activeCount());
        tg.list();

    }
    @Override
    public void run() {
        String groupAndName = Thread.currentThread().getThreadGroup().getName()
                + "-" + Thread.currentThread().getName();
        System.out.println("I am " + groupAndName);

    }
}
