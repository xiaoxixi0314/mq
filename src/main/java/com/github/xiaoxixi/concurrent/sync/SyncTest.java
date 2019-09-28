package com.github.xiaoxixi.concurrent.sync;

/**
 *类锁和对象锁测试类
 */
public class SyncTest {




    private static class SyncClass extends Thread {
        @Override
        public void run() {
            try {
                syncClass();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class SyncInstance1 implements Runnable {

        private SyncTest syncTest;

        public SyncInstance1(SyncTest syncTest) {
            this.syncTest = syncTest;
        }

        @Override
        public void run() {
            System.out.println("SyncInstance1 will running....");
            try {
                syncTest.instance();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class SyncInstance2 implements Runnable {

        private SyncTest syncTest;

        public SyncInstance2(SyncTest syncTest) {
            this.syncTest = syncTest;
        }

        @Override
        public void run() {
            System.out.println("SyncInstance2 will running....");
            try {
                syncTest.instance2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 对象锁，同一个对象中的某一个方法在一个时刻只有一个线程可以运行，不能多个线程同时运行
     * 表现形式：非静态方法使用synchronized修饰，或者在代码块中使用synchronized(this){}包裹
     * @throws InterruptedException
     */
    private synchronized  void instance() throws InterruptedException {
        System.out.println("instance will running....");
        Thread.sleep(3000);
        System.out.println("instance ended.....");
        Thread.sleep(3000);
    }


    private synchronized  void instance2() throws InterruptedException {
        System.out.println("instance2 will running....");
        Thread.sleep(3000);
        System.out.println("instance2 ended.....");
        Thread.sleep(3000);
    }

    /**
     * 类锁，锁类，即同一个类的当前方法在一个时刻只有一个线程可以运行，不能多个线程同时运行
     * 表现形式：类的静态方法中加上synchronized关键字
     */
    private static synchronized void syncClass() throws InterruptedException {
        System.out.println("Sync class will be running...");
        Thread.sleep(1000);
        System.out.println("Sync class ended...");
        Thread.sleep(1000);
    }


    public static void main(String[] args) {

        // 1. 演示对象锁，不同对象互不干扰
//        SyncTest sc1 = new SyncTest();
//        Thread t1 = new Thread(new SyncInstance1(sc1));
//
//        SyncTest sc2 = new SyncTest();
//        Thread t2 = new Thread(new SyncInstance1(sc2));
//
//        t1.start();
//        t2.start();

        // 2. 同一个对象，某个时刻只能有一个线程运行该方法
//        SyncTest sc1 = new SyncTest();
//        Thread t1 = new Thread(new SyncInstance1(sc1));
//
//        Thread t2 = new Thread(new SyncInstance1(sc1));
//
//        t1.start();
//        t2.start();


        // 3. 类锁演示
        SyncClass syncClass1 = new SyncClass();
        SyncClass syncClass2 = new SyncClass();

        syncClass1.start();
        syncClass2.start();

    }
}
