package com.example.rabbitmqdemo.service.impl;

import com.example.rabbitmqdemo.service.IRabbitConsumerService;
import com.rabbitmq.client.*;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @createDate: 2019-08-30 16:39
 * @description:
 */
@Service
public class RabbitConsumerServiceImpl implements IRabbitConsumerService {
    private AmqpTemplate amqpTemplate;

    public RabbitConsumerServiceImpl(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    private static final String USERNAME = "admin";
    private static final String PASSWORD = "mypassword";
    private static final String QUEUE_NAME = "queue_demo";
    private static final String IP_ADDRESS = "10.0.0.40";
    private static final int PORT = 5672;

    /**
     * 消费者，推模式
     *
     * @throws IOException
     * @throws TimeoutException
     * @throws InterruptedException
     */
    @Override
    public void consumerV1() throws IOException, TimeoutException, InterruptedException {
        Address[] addresses = {new Address(IP_ADDRESS, PORT)};
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);

        // 这里的连接方式与生产者的 demo 略有不同，注意辨别区别

        // 创建连接
        Connection connection = factory.newConnection(addresses);
        // 创建信道
        Channel channel = connection.createChannel();
        // 设置客户端最多接收未被 ack 的消息的个数
        channel.basicQos(64);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("recv message : " + new String(body));
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        channel.basicConsume(QUEUE_NAME, consumer);
        // 等待回调函数执行完毕之后 ， 关闭资源
        TimeUnit.SECONDS.sleep(5);
        channel.close();
        connection.close();
    }

    @Override
    public void consumerV2() {
        Object convert = amqpTemplate.receiveAndConvert(QUEUE_NAME);
        System.out.println(convert);
    }
}
