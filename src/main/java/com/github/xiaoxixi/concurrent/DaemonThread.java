package com.github.xiaoxixi.concurrent;

/**
 * 守护线程
 */
public class DaemonThread {

    private static class Usethread extends Thread {

        @Override
        public void run() {
            try {
                while (!interrupted()) {
                    System.out.println("thread interrupt status:" + isInterrupted());
                }
                System.out.println("thread interrupt status:" + isInterrupted());
            } finally {
                // 守护线程中的finally不能保证一定会执行
                System.out.println("....finally");
            }
        }

    }
    public static void main(String[] args) throws InterruptedException {
        Usethread usethread = new Usethread();
        // 设置守护线程后，将与主线程共死，主线程执行完毕后，子线程也将over
        usethread.setDaemon(true);
        usethread.start();
        Thread.sleep(5);
        System.out.println("the thread will sleep over");
//        usethread.interrupt();
    }
}
