package com.multithread.demo;

import java.util.ArrayList;
import java.util.Vector;

/**
 * 并发下的ArrayList
 * @author WangZhanWu
 * @date 2018/8/2 14:30
 */
public class ArrayListMultiThread {

    //    static ArrayList<Integer> arrayList = new ArrayList<>();
    static Vector<Integer> arrayList = new Vector<>();
    public static class AddThread implements Runnable {
        @Override
        public void run() {
            for(int i = 0; i<10000000; i++) {
                arrayList.add(i);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new AddThread());
        Thread t2 = new Thread(new AddThread());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(arrayList.size());
    }
}
