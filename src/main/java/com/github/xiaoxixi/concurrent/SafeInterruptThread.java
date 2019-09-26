package com.github.xiaoxixi.concurrent;

/**
 * 如何安全的中断线程
 */
public class SafeInterruptThread  {

    /**
     * 可安全中断的线程
     */
    private static class SafeInterrupt extends Thread {
        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            while (!isInterrupted()) {
                System.out.println(threadName + " is running!");
            }
            System.out.println(threadName + "interrupt status:" + interrupted());

        }
    }

    /**
     * 永不中断的线程
     */
    private static class NotInterrupt extends  Thread {
        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            while (true) {
                System.out.println(threadName + " is running!");
            }

        }
    }

    public static void main(String[] args) throws InterruptedException {

        SafeInterrupt safeInterrupt = new SafeInterrupt();
        safeInterrupt.start();
        Thread.sleep(200);
        safeInterrupt.interrupt();

        NotInterrupt notInterrupt = new NotInterrupt();
        notInterrupt.start();
    }
}
