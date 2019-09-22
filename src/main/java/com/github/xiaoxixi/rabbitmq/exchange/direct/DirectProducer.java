package com.github.xiaoxixi.rabbitmq.exchange.direct;

import com.github.xiaoxixi.rabbitmq.exchange.util.RabbitmqUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;


/**
 * 使用direct交换器
 * routerKey和队列完全匹配
 */
public class DirectProducer {

    public static final String EXCHANGE_NAME = "direct_logs";
    public static final  String[] ROUTE_KEYS = {"king", "mark", "james"};

    public static void main(String[] args) throws Exception {
        // 创建连接
        Connection connection = RabbitmqUtil.buildConnection();
        // 创建信道
        Channel channel = RabbitmqUtil.buildChannel(connection);
        // 在信道中设置交换器
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        // 声明队列，路由键
        for (int i = 0; i < 3; i ++) {
            String routeKey = ROUTE_KEYS[i%3];
            String message = "hello, rabbit mq" + i%3;
            // 发布消息 exchange name， route， props，消息体
            channel.basicPublish(EXCHANGE_NAME, routeKey, null, message.getBytes());
            System.out.println("i created message, "+ message);
        }
        channel.close();
        connection.close();
    }
}
