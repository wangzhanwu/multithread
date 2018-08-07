package com.multithread.demo;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier 循环栅栏
 * @author WangZhanWu
 * @date 2018/8/3 15:18
 */
public class CyclicBarrierDemo {
    public static class Solider implements Runnable {
        private String solider;
        private final CyclicBarrier cyclicBarrier;

        public Solider(CyclicBarrier cyclicBarrier, String soliderName) {
            this.cyclicBarrier = cyclicBarrier;
            this.solider = soliderName;
        }

        @Override
        public void run() {
            try {
                cyclicBarrier.await();
                doWork();
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        private void doWork() {
            try {
                Thread.sleep(Math.abs(new Random().nextInt()%10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(solider + "任务完成!");
        }
    }

    public static class BarrierRun implements Runnable {
        boolean flag;
        int N;
        public BarrierRun(boolean flag, int N) {
            this.flag = flag;
            this.N = N;
        }

        @Override
        public void run() {
            if(flag) {
                System.out.println("司令[士兵"+N+"个，任务完成！]");
            } else {
                System.out.println("司令[士兵"+N+"个，集合完成！]");
                flag = true;
            }
        }
    }

    public static void main(String[] args) {
        final int N = 10;
        Thread[] allSoliders = new Thread[N];
        boolean flag = false;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(N, new BarrierRun(flag, N));
        System.out.println("集合队伍！");
        for(int i = 0; i < N; i++) {
            System.out.println("士兵"+i+"报道！");
            allSoliders[i] = new Thread(new Solider(cyclicBarrier, "士兵" + i));
            allSoliders[i].start();
        }
    }
}
