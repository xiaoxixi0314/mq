package com.github.xiaoxixi.rabbitmq.exchange.producer_balance.mandatory;

import com.github.xiaoxixi.rabbitmq.exchange.util.RabbitmqUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class ReturnListenerConsumer {

    public static void main(String[] args) throws Exception {
        // 创建连接
        Connection connection = RabbitmqUtil.buildConnection();
        // 创建信道
        Channel channel = RabbitmqUtil.buildChannel(connection);
        // 在信道中设置交换器
        channel.exchangeDeclare(ReturnListenerDirectProducer.EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        String queueName  = "queue-king-return-listener";
        channel.queueDeclare(queueName, false, false,false, null);
        // 队列绑定路由键
        String routeKey = "king";
        channel.queueBind(queueName, ReturnListenerDirectProducer.EXCHANGE_NAME, routeKey);

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
