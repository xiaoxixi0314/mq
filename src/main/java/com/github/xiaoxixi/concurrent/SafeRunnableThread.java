package com.github.xiaoxixi.concurrent;

/**
 * 如何安全的中断线程
 */
public class SafeRunnableThread {

    /**
     * 可安全中断的线程
     */
    private static class SafeInterrupt implements Runnable {

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println(threadName + " with implements runnable is running!");
            }
            System.out.println(threadName + "interrupt status:" + Thread.currentThread().isInterrupted());

        }
    }



    public static void main(String[] args) throws InterruptedException {

        SafeInterrupt safeInterrupt = new SafeInterrupt();
        Thread thread = new Thread(safeInterrupt);
        thread.start();
        Thread.sleep(200);
        thread.interrupt();

    }
}
