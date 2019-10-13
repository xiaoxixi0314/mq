package com.github.xiaoxixi.concurrent.db_connection_pool;

import java.sql.Connection;
import java.util.LinkedList;

public class DBConnectionPool {

    private LinkedList<Connection> pool = new LinkedList<>();

    public DBConnectionPool(int connSize) {
        if (connSize == 0) {
            connSize = 5;
        }
        for (int i = 0; i < connSize; i ++) {
            Connection connection = SqlConnectionImpl.getInstance();
            pool.addLast(connection);
        }
    }

    /**
     * 释放数据库链接
     */
    public void releaseConnection(Connection connection) {
        if (connection != null) {
            synchronized (pool) {
                pool.addLast(connection);
                pool.notifyAll();
            }
        }
    }

    /**
     * 获取数据库链接
     * @param timeOut 超过此事件还获取不到则返回null
     * @return
     */
    public  Connection getConnection(int timeOut) {
        synchronized (pool) {
            try {
                // 永远不超时
                if (timeOut <= 0) {
                    while (pool.isEmpty()) {
                        pool.wait();
                    }
                    return pool.removeFirst();
                } else {
                    // 等待到什么时候
                    long overTime = System.currentTimeMillis() + timeOut;
                    // 持续等待到什么时候
                    long remain = timeOut;
                    while (pool.isEmpty() && remain > 0) {
                        pool.wait(remain);
                        remain = overTime - System.currentTimeMillis();
                    }
                    Connection connection = null;
                    if (!pool.isEmpty()) {
                        connection = pool.removeFirst();
                    }
                    return connection;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
