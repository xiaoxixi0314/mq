package com.github.xiaoxixi.rabbitmq.exchange.util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitmqUtil {

    /**
     * 创建connection
     * @return
     * @throws Exception
     */
    public static Connection buildConnection() throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        // 端口默认5672
        factory.setHost("192.168.1.10");
        factory.setUsername("admin");
        factory.setPassword("123456");
        factory.setVirtualHost("enjoyedu");
        Connection connection = factory.newConnection();
        return connection;
    }

    /**
     * 从connection中创建channel
     * @param connection
     * @return
     * @throws Exception
     */
    public static Channel buildChannel(Connection connection)  throws Exception{
        // 创建信道
        Channel channel = connection.createChannel();
        return channel;
    }
}
