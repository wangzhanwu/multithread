package com.multithread.demo;


/**
 * @Description 用stop方法终止一个线程 该方法不安全，有可能会造成数据不一致
 * @Author WangZhanWu
 * @Date 2018/7/25 10:13
 */
public class StopThreadUnsafe {

    public static class User {
        private int id;
        private String name;
        public User() {
            id = 0;
            name = "0";
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.getClass() + "{" + "id=" + id + ", name='" + name + '\'' + '}';
        }
    }

    public static User user = new User();
    //修改user对象的内容
    public static class ChangeObjectThread extends Thread {
        volatile boolean stopme = false;
        public void stopMe() {
            stopme = true;
        }
        @Override
        public void run() {
            while(true) {
                if(stopme) {
                    System.out.println("exit by stop me");
                    break;
                }
                synchronized (user) {
                    int v = (int) (System.currentTimeMillis()/1000);
                    user.setId(v);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    user.setName(String.valueOf(v));
                    Thread.yield();
                }
            }
        }
    }

    public static class ReadObjectThread extends  Thread {
        @Override
        public void run() {
            while(true) {
                synchronized (user) {
                    if(user.getId() != Integer.parseInt(user.getName())) {
                        System.out.println(user.toString());
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ReadObjectThread().start();
        while(true) {
            Thread thread = new ChangeObjectThread();
            thread.start();
            Thread.sleep(150);
            ((ChangeObjectThread) thread).stopMe();
        }
    }
}
