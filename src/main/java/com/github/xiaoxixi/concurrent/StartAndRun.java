package com.github.xiaoxixi.concurrent;

/**
 * run 和 start 方法
 */
public class StartAndRun {

    private static class StartAndRunThread extends Thread {

        @Override
        public void run() {
            try {
                int i = 50;
                while (i > 0) {
                    i --;
                    Thread.sleep(100);
                    System.out.println("I am "+ Thread.currentThread().getName() + ", i=" + i);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        StartAndRunThread runThread = new StartAndRunThread();
        runThread.setName("run-and-start");
//        runThread.run();
        runThread.start();
    }
}
