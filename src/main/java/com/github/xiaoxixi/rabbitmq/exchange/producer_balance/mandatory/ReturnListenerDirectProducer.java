package com.github.xiaoxixi.rabbitmq.exchange.producer_balance.mandatory;

import com.github.xiaoxixi.rabbitmq.exchange.util.RabbitmqUtil;
import com.rabbitmq.client.*;

import java.io.IOException;


/**
 * 使用direct交换器,失败者通知simple
 * routerKey和队列完全匹配
 */
public class ReturnListenerDirectProducer {

    public static final String EXCHANGE_NAME = "return_listener_direct_logs";
    public static final  String[] ROUTE_KEYS = {"king", "mark", "james"};

    public static void main(String[] args) throws Exception {
        // 创建连接
        Connection connection = RabbitmqUtil.buildConnection();
        // 创建信道
        Channel channel = RabbitmqUtil.buildChannel(connection);
        // 在信道中设置交换器
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        // 连接关闭通知
        connection.addShutdownListener(new ShutdownListener() {
            @Override
            public void shutdownCompleted(ShutdownSignalException cause) {
                System.out.println("connection is closed:" + cause.getMessage());
            }
        });
        // 信道关闭通知
        channel.addShutdownListener(new ShutdownListener() {
            @Override
            public void shutdownCompleted(ShutdownSignalException cause) {
                System.out.println("channel is closed:" + cause.getMessage());
            }
        });
        // 失败者通知
        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode,
                                     String replyText,
                                     String exchange,
                                     String routingKey,
                                     AMQP.BasicProperties properties,
                                     byte[] body) throws IOException {
                System.out.println("==============================>");
                String message = new String(body);
                System.out.println("reply code:"+ replyCode);
                System.out.println("replyText:" + replyText);
                System.out.println("exchange:" +  exchange);
                System.out.println("route key:" + routingKey);
                System.out.println("message:" + message);
            }
        });

        // 声明队列，路由键
        for (int i = 0; i < 3; i ++) {
            String routeKey = ROUTE_KEYS[i%3];
            String message = "hello, rabbit mq" + i%3;
            // 发布消息 exchange name， 是否确认，route， props，消息体
            channel.basicPublish(EXCHANGE_NAME, routeKey,true, null, message.getBytes());
            System.out.println("i created message, "+ message);
            Thread.sleep(200);
        }
        channel.close();
        connection.close();
    }
}
