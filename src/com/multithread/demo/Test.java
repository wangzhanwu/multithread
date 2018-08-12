package com.multithread.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author W2
 * @date 2018/8/8 10:05
 * Created by W2 on 2018/8/8 10:05
 */
public class Test {
    public static void main(String[] args) {
        Collections.synchronizedMap(new HashMap());
        Collections.synchronizedList(new ArrayList<>());
        new ArrayBlockingQueue<Integer>(10);
        new ThreadLocal().get();
        new ThreadLocal().set(new Object());
        new ThreadLocal().remove();
        new ThreadLocal() {
            @Override
            protected Object initialValue() {
                return super.initialValue();
            }
        };
    }
}
