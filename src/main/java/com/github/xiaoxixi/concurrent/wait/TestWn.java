package com.github.xiaoxixi.concurrent.wait;

/**
 * wait notify notifyAll 测试类
 */
public class TestWn {

    public static Express express = new Express(0, Express.CITY);

    private static class CheckKm extends  Thread {
        @Override
        public void run(){
            try {
                express.waitKm();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class CheckSite extends Thread {
        @Override
        public void run(){
            try {
                express.waitSite();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i <3; i++) {
            new CheckKm().start();
        }
        for (int i = 0; i <3; i++) {
            new CheckSite().start();
        }
        System.out.println("main thread will sleep 2s");
        Thread.sleep(2000);
        express.changeKm();
    }

}
