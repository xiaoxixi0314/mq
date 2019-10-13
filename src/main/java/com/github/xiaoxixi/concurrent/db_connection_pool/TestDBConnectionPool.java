package com.github.xiaoxixi.concurrent.db_connection_pool;


import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.concurrent.CountDownLatch;

public class TestDBConnectionPool {

    private static final DBConnectionPool pool = new DBConnectionPool(10);

    private static CountDownLatch countDownLatch;

    private static class FetchConnectionThread extends Thread {

        /**
         * 模拟真实的数据库操作
         * 1.获取连接
         * 2.提交数据
         * 3.释放连接归还给连接池
         */
        @Override
        public void run() {
            Connection connection = pool.getConnection(80);
            if (connection == null) {
                System.out.println("× Thread not fetched connection, [" + Thread.currentThread().getId() + "]");
                return;
            }
            try {
                System.out.println("● Thread fetched connection, [" + Thread.currentThread().getId() + "]");
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                pool.releaseConnection(connection);
            }
        }
    }

    public  static  void main(String[] args) throws InterruptedException{
        int count = 100;
        countDownLatch = new CountDownLatch(count);
        // 模拟500个请求
        for (int i = 0; i < count; i ++) {
            FetchConnectionThread requestThread = new FetchConnectionThread();
            requestThread.start();
            countDownLatch.countDown();
        }
        countDownLatch.await();

    }
}
