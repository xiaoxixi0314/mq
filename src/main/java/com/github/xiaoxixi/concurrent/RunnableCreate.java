package com.github.xiaoxixi.concurrent;

public class RunnableCreate implements Runnable {

    @Override
    public void run() {
        System.out.println("implements Runnable");
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new RunnableCreate());
        thread.start();
    }
}
