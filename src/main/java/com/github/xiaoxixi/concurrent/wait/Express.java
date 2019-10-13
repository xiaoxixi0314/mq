package com.github.xiaoxixi.concurrent.wait;

/**
 * 演示wait 和 notify/ notifyAll的使用
 */
public class Express {

    public static final String CITY = "SHANGHAI";

    /**
     * 快递到达站点
     */
    private String site = "SHANGHAI";

    /**
     * 快递运输里程数
     */
    private int km = 0;

    public Express(int km, String site) {
        this.site = site;
        this.km = km;
    }

    /**
     * 变化公里数
     * 然后通知出于wait状态的并需要处理公里数的线程进行业务处理
     */
    public synchronized void changeKm() {
        this.km = 101;
        //notifyAll();
        notify();
    }

    /**
     * 变化到达地点
     */
    public synchronized  void changeSite() {
        this.site = "BEIJING";
//        notifyAll();
        notify();
    }

    public synchronized void waitKm () throws InterruptedException {
        while(this.km < 100) {
            wait();
            System.out.println("check km thread["+ Thread.currentThread().getId()+"] is be notified.");
        }
        // 模拟进行业务处理
        System.out.println("The km is changed to " + this.km + "km, I will change db.");
    }

    public synchronized void waitSite() throws InterruptedException{
        while(CITY.equals(this.site)) {
            wait();
            System.out.println("check site thread["+ Thread.currentThread().getId()+"] is be notified.");
        }
        // 模拟进行业务处理
        System.out.println("The site is changed to " + this.site + ", I will change db.");
    }
}
