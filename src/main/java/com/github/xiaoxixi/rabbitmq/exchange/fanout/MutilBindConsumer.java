package com.github.xiaoxixi.rabbitmq.exchange.fanout;

import com.github.xiaoxixi.rabbitmq.exchange.direct.DirectProducer;
import com.github.xiaoxixi.rabbitmq.exchange.util.RabbitmqUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.UUID;

/**
 * 多重绑定
 * 多个路由键绑定到同一个队列中
 * 路由键没有任何影响，广播
 */
public class MutilBindConsumer {

    public static void main(String[] args) throws Exception {
        // 创建连接
        Connection connection = RabbitmqUtil.buildConnection();
        // 创建信道
        Channel channel = RabbitmqUtil.buildChannel(connection);
        // 在信道中设置交换器
        channel.exchangeDeclare(DirectProducer.EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

        // 生成一个随机队列名称
        String queueName  = UUID.randomUUID().toString();
        channel.queueDeclare(queueName, false, false,false, null);
        // 队列绑定路由键
        for (String routeKey: DirectProducer.ROUTE_KEYS) {
            channel.queueBind(queueName, DirectProducer.EXCHANGE_NAME, routeKey);
        }
        System.out.println("waiting for message...");

        // 声明一个消费者
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                String routeKey = envelope.getRoutingKey();
                System.out.println("Recieved [" + message +"]from route key[" + routeKey + "]");
            }
        };
        // 在信道中绑定队列与消费者
        channel.basicConsume(queueName, true, consumer);
    }
}
