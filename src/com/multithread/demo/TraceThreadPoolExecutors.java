package com.multithread.demo;

import java.util.concurrent.*;

/**
 * 在线程池中寻找堆栈
 * 具体操作：对ThreadPoolExecutor中的execute和submit方法进行重写，对传入方法的参数Runnable进行包装
 * 使其在发生异常时打印提交线程的堆栈信息
 *
 * @author W2
 * @date 2018/8/7 10:25
 */
public class TraceThreadPoolExecutors extends ThreadPoolExecutor {
    public TraceThreadPoolExecutors(int corePoolSize, int maximumPoolSize,
                                    long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public void execute(Runnable task) {
        super.execute(wrap(task, clientTrace(), Thread.currentThread().getName()));
    }

    public Future<?> submit(Runnable task) {
        return super.submit(wrap(task, clientTrace(), Thread.currentThread().getName()));
    }

    private Exception clientTrace() {
        return new Exception("Client stack trace");
    }

    /**
     * 包装线程，当线程执行报错时打印提交任务线程异常堆栈
     * @params [task, clientStack, threadName]
     * @return java.lang.Runnable
     */
    private Runnable wrap(final Runnable task, final Exception clientStack, String threadName) {

        return new Runnable() {
            @Override
            public void run() {
                try {
                    task.run();
                } catch (Exception e) {
                    clientStack.printStackTrace();
                    throw e;
                }
            }
        };
    }

    public static class DivTask implements Runnable {
        int a, b;
        public DivTask(int a, int b) {
            this.a = a;
            this.b = b;
        }
        @Override
        public void run() {
            double c = a/b;
            System.out.println("c = " + c);
        }
    }

    public static void main(String[] args) {
        TraceThreadPoolExecutors pools = new TraceThreadPoolExecutors(0, Integer.MAX_VALUE, 0L,
                TimeUnit.MILLISECONDS, new SynchronousQueue<>());
        for(int i=0; i<5; i++) {
            pools.execute(new DivTask(100, i));
        }

        new ForkJoinPool();
    }
}
