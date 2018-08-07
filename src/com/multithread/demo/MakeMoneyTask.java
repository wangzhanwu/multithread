package com.multithread.demo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Fork/Join框架
 * @author W2
 * @date 2018/8/7 13:38
 */
public class MakeMoneyTask extends RecursiveTask<Integer> {
    private static final int MIN_GOAL_MONAY = 100000;
    private int goalMoney;
    private String name;
    private static final AtomicInteger employeeNo = new AtomicInteger();

    public MakeMoneyTask(int goalMoney) {
        this.goalMoney = goalMoney;
        this.name = employeeNo.getAndIncrement() +"号员工";
    }

    @Override
    protected Integer compute() {
        if(goalMoney < MIN_GOAL_MONAY) {
            System.out.println(name+"老板交代了，要赚够"+goalMoney+"元，加油干吧！");
            return makeMoney();
        } else {//如果目标金额大于100000则分解目标金额
            int subThreadcount = ThreadLocalRandom.current().nextInt(10)+2;
            System.out.println(name+":老板要我赚"+goalMoney+"元，有点小多，没事！让我的"+subThreadcount+"个部下去完成吧！"
                            +"每个人赚"+Math.ceil(goalMoney*1.0/subThreadcount)+"元应该没问题。");
            ArrayList<MakeMoneyTask> tasks= new ArrayList<>();
            for(int i=0; i< subThreadcount; i++) {
                tasks.add(new MakeMoneyTask(goalMoney/subThreadcount));
            }
            Collection<MakeMoneyTask> makeMoneyTasks = invokeAll(tasks);
            int sum = 0;
            for(MakeMoneyTask task : makeMoneyTasks) {
                try {
                    sum += task.get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return sum;
        }
    }

    private Integer makeMoney() {
        int sum = 0;
        int day = 1;
        try {
            while(true) {
                Thread.sleep(ThreadLocalRandom.current().nextInt(200));
                int money = ThreadLocalRandom.current().nextInt(MIN_GOAL_MONAY / 3);
                System.out.println(name +"在第"+(day++)+"天"+"赚了"+money+"元钱");
                sum += money;
                if(sum >= goalMoney) {
                    System.out.println(name +":终于赚到了"+sum+"元，可以交差了。");
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return sum;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Integer> task = pool.submit(new MakeMoneyTask(1000000));
        do {
            try {
                TimeUnit.MILLISECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (!task.isDone());
        System.out.println(task.get());
        //就像这个程序一样，人生也需要一点变化
    }
}
