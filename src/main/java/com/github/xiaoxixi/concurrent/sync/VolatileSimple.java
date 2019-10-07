package com.github.xiaoxixi.concurrent.sync;

/**
 * 演示volatile只能保证可见性
 * 不能保证原子性
 * volatile适应场景：一写多读
 */
public class VolatileSimple {

    private static class VolatileThread extends Thread {

        private volatile Integer num = 0;

        public VolatileThread(String name)  {
            super(name);
        }

        @Override
        public void run() {
            Thread t1 = startSumThread();
            Thread t2 = startSumThread();
            Thread t3 = startSumThread();
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            t1.interrupt();
            t2.interrupt();
            t3.interrupt();

        }

        public Thread startSumThread() {
            Thread thread = new Thread(() -> {
                String threadName = Thread.currentThread().getName();
                try {
                    while (!isInterrupted()) {
                        num = num + 1;
                        Thread.sleep(10);
                        System.out.println(threadName + " The num is: " + num);
                    }
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
            return thread;
        }

    }

    public static void main(String[] args) throws InterruptedException {
        VolatileThread t1 = new VolatileThread("t1");
        t1.start();

    }
}
