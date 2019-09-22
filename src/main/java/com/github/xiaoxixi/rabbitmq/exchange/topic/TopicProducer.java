package com.github.xiaoxixi.rabbitmq.exchange.topic;

import com.github.xiaoxixi.rabbitmq.exchange.util.RabbitmqUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 使用fanout交换器，即广播交换器
 * 广播交换器，路由键不管存在不存在，都会广播到所有队列
 */
public class TopicProducer {

    public static final String EXCHANGE_NAME = "topic_logs";
    public static final  String[] ROUTE_KEYS = {"king", "mark", "james"};
    public static final String[] MODULES = {"jvm", "kafka", "redis"};
    public static final String[] SERVERS = {"A", "B", "C"};

    public static void main(String[] args) throws Exception {
        // 创建连接
        Connection connection = RabbitmqUtil.buildConnection();
        // 创建信道
        Channel channel = RabbitmqUtil.buildChannel(connection);
        // 在信道中设置交换器
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        // 声明队列，路由键
        for (int i = 0; i < 3; i ++) {
            for (int j = 0; j < 3; j ++) {
                for (int k = 0; k < 3; k ++) {
                    String routeKey = ROUTE_KEYS[i%3] + "." + MODULES[j%3] + "." + SERVERS[k%3];
                    String message = "hello, topic [" + i + "," + j +","+k+"]";
                    // 发布消息 exchange name， route， props，消息体
                    channel.basicPublish(EXCHANGE_NAME, routeKey, null, message.getBytes());
                    System.out.println("i created message, "+ message);
                }
            }
        }
        channel.close();
        connection.close();
    }
}
