package com.github.xiaoxixi.concurrent;


public class HasInterrputedExceotion {

    private static class UseThread extends Thread {

        public UseThread(String threadName) {
            super(threadName);
        }

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            while(!isInterrupted()) {
                try {
                    Thread.sleep(200);
                    // 抛出interrputedException后，中断标记位又被复位成了false
                    // 如果需要中断当前线程的执行，需要在catch中手动调用interrupt()方法线程才会真正中断
                } catch (InterruptedException e ) {
                    System.out.println(threadName + " interrupted status "+ isInterrupted());
                    // catch InterrupttedException后线程中断标记会被复位，需要手动再调用interrupt()方法线程才会真正被中断
                    interrupt();
                    e.printStackTrace();
                }
                System.out.println(threadName);
            }
            System.out.println(threadName + " interrupted status "+ isInterrupted());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread endThread = new UseThread("hello-jvm-thread");
        endThread.start();
        System.out.println("main thread will sleep 500 ms");
        Thread.sleep(500);
        endThread.interrupt();
    }
}
