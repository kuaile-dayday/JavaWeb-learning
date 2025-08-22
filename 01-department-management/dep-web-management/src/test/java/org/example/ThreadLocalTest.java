package org.example;

public class ThreadLocalTest {
    private static ThreadLocal<String> local = new ThreadLocal<>();
    public static void main(String[] args) {
        local.set("Main Message");

        new Thread(new Runnable() {
            @Override
            public void run() {
                local.set("Thread1 Message");
                System.out.println(Thread.currentThread().getName()+":"+local.get());
            }
        }).start();

        System.out.println(Thread.currentThread().getName()+":"+local.get());

        local.remove();
        System.out.println(Thread.currentThread().getName()+":"+local.get());
    }
}
