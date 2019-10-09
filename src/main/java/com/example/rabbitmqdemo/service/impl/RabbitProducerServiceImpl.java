package com.example.rabbitmqdemo.service.impl;

import com.example.rabbitmqdemo.service.IRabbitProducerService;
import com.rabbitmq.client.*;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

/**
 * @createDate: 2019-08-30 16:06
 * @description:
 */
@Service
public class RabbitProducerServiceImpl implements IRabbitProducerService {
    private AmqpTemplate amqpTemplate;

    public RabbitProducerServiceImpl(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    private static final String USERNAME = "admin";
    private static final String PASSWORD = "mypassword";
    private static final String EXCHANGE_NAME = "exchange_demo";
    private static final String ROUTING_KEY = "routingkey_demo";
    private static final String QUEUE_NAME = "queue_demo";
    private static final String IP_ADDRESS = "10.0.0.40";
    private static final int PORT = 5672;
    private static final String msg = "Hello World!";

    /**
     * 发送消息，版本1
     */
    @Override
    public void producerV1() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(IP_ADDRESS);
        factory.setPort(PORT);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);

        // 创建连接
        Connection connection = factory.newConnection();
        // 创建信道
        Channel channel = connection.createChannel();
        // 创建一个type="direct"、持久化的、非自动删除的交换器
        channel.exchangeDeclare(EXCHANGE_NAME, "direct", true, false, null);
        // 创建一个持久化、非排他的、非自动删除的队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        // 将交换器与队列通过路由键绑定
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
        // 发送一条持久化的消息 ： helloworld!
        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
        // 关闭资源
        channel.close();
        connection.close();
    }

    @Override
    public void producerV2() {
        amqpTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, msg);
        System.out.println("Send msg = " + msg);
    }

    /**
     * 消息的TTL，方法一：通过设置队列的TTL，队列中的所有消息都有相同的过期时间。
     * 如果不设置 TTL，则表示此消息不会过期 ； 如果将 TTL 设置为 0， 则表示除非此时可以直接将消息投递到消费者，否则该消息会被立即丢弃，
     * 这个特性可以部分替代 RabbitMQ 3.0 版本之前的 immediate 参数，之所以部分代替，
     * 是因为 immediate 参数在投递失败时会用Basic . Return 将消息返回（这个功能可以用死信队列来实现，详细参考 4.3 节）。
     *
     * @throws IOException
     * @throws TimeoutException
     */
    @Override
    public void producerV3() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(IP_ADDRESS);
        factory.setPort(PORT);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);

        // 创建连接
        Connection connection = factory.newConnection();
        // 创建信道
        Channel channel = connection.createChannel();

        HashMap<String, Object> args = new HashMap<>();
        // 过期时间单位为毫秒
        args.put("x-message-ttl", 11000);
        // 创建一个type="direct"、持久化的、过期自动删除的交换器
        channel.exchangeDeclare(EXCHANGE_NAME, "direct", true, false, null);
        // 创建一个持久化、非排他的、非自动删除的队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, args);
        // 将交换器与队列通过路由键绑定
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
        // 发送一条持久化的消息 ： helloworld!
        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
        // 关闭资源
        channel.close();
        connection.close();
    }

    /**
     * 针对每条消息设置 TTL 的方法是在 channel.basicPublish 方法中加入 expiration的属性参数，单位为毫秒。
     *
     * @throws IOException
     * @throws TimeoutException
     */
    @Override
    public void producerV4() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(IP_ADDRESS);
        factory.setPort(PORT);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);

        // 创建连接
        Connection connection = factory.newConnection();
        // 创建信道
        Channel channel = connection.createChannel();
        // 创建一个type="direct"、持久化的、过期自动删除的交换器
        channel.exchangeDeclare(EXCHANGE_NAME, "direct", true, false, null);
        // 创建一个持久化、非排他的、非自动删除的队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        // 将交换器与队列通过路由键绑定
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);

        AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
        // 持久化消息
        builder.deliveryMode(2);
        // 设置TTL=6000ms
        builder.expiration("11000");
        AMQP.BasicProperties properties = builder.build();
        // 发送一条持久化的消息 ： helloworld!
        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, properties, msg.getBytes());
        // 关闭资源
        channel.close();
        connection.close();
    }

    /**
     * 设置队列的 TTL；该示例中“x-expires”参数错误
     * <p>
     * 通过 channel . queueDeclare 方法中的 x-expires 参数可以控制队列被自动删除前处于未使用状态的时间。未使用的意思是队列上没有任何的消费者，队列也没有被重新声明，并且在过期时间段内也未调用过 Basic . Get 命令。
     * <p>
     * 设置队列里的 TTL 可以应用于类似 RPC 方式的回复队列，在 RPC 中，许多队列会被创建出来，但是却是未被使用的。
     * RabbitMQ 会确保在过期时间到达后将队列删除，但是不保障删除的动作有多及时 。在RabbitMQ 重启后，持久化的队列的过期时间会被重新计算。用于表示过期时间的 x-expires 参数以毫秒为单位，井且服从和 x-message-ttl 一样的约束条件，不过不能设置为 0。比如该参数设置为 1000，则表示该队列如果在 1 秒钟之内未使用则会被删除。
     * 代码清单 4-6 演示了创建一个过期时间为 30 分钟的队列：
     */
    @Override
    public void producerV5() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(IP_ADDRESS);
        factory.setPort(PORT);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);

        // 创建连接
        Connection connection = factory.newConnection();
        // 创建信道
        Channel channel = connection.createChannel();

        HashMap<String, Object> args = new HashMap<>();
        // 过期时间单位为毫秒
//        args.put("x-expires", 11000);
        args.put("x expires", 11000);
        // 创建一个type="direct"、持久化的、过期自动删除的交换器
        channel.exchangeDeclare(EXCHANGE_NAME, "direct", true, false, null);
        // 创建一个持久化、非排他的、非自动删除的队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, args);
        // 将交换器与队列通过路由键绑定
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
        // 发送一条持久化的消息 ： helloworld!
        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
        // 关闭资源
        channel.close();
        connection.close();
    }

    /**
     * 死信队列
     *
     * @throws IOException
     * @throws TimeoutException
     */
    @Override
    public void producerV6() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(IP_ADDRESS);
        factory.setPort(PORT);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);

        // 创建连接
        Connection connection = factory.newConnection();
        // 创建信道
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("exchange.dlx", "direct", true);
        channel.exchangeDeclare("exchange.normal", "fanout", true);
        HashMap<String, Object> args = new HashMap<>(3);
        args.put("x-message-ttl", 10000);
        args.put("x-dead-letter-exchange", "exchange.dlx");
        args.put("x-dead-letter-routing-key", "routingkey");

        channel.queueDeclare("queue.normal", true, false, false, args);
        channel.queueBind("queue.normal", "exchange.normal", "");

        channel.queueDeclare("queue.dlx", true, false, false, null);
        channel.queueBind("queue.dlx", "exchange.dlx", "routingkey");

        channel.basicPublish("exchange.normal", "rk", MessageProperties.PERSISTENT_TEXT_PLAIN, "dlx".getBytes());
        // 关闭资源
        channel.close();
        connection.close();
    }

    /**
     * 优先级队列
     *
     * @throws IOException
     * @throws TimeoutException
     */
    @Override
    public void producerV7() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(IP_ADDRESS);
        factory.setPort(PORT);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);

        // 创建连接
        Connection connection = factory.newConnection();
        // 创建信道
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("exchange.priority", "direct", true);
        HashMap<String, Object> args = new HashMap<>(3);
        // 设置对立的最大优先级
        args.put("x-max-priority", 10);
        channel.queueDeclare("queue.priority", true, false, false, args);
        channel.queueBind("queue.priority", "exchange.priority", "rk_priority");

        AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
        builder.priority(5);
        AMQP.BasicProperties properties = builder.build();
        channel.basicPublish("exchange.priority", "rk_priority", properties, msg.getBytes());
        // 关闭资源
        channel.close();
        connection.close();
    }


    /**
     * 生产者确认,事务机制
     *
     * @throws IOException
     * @throws TimeoutException
     */
    @Override
    public void producerV8() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(IP_ADDRESS);
        factory.setPort(PORT);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);

        // 创建连接
        Connection connection = factory.newConnection();
        // 创建信道
        Channel channel = connection.createChannel();
        // 创建一个type="direct"、持久化的、非自动删除的交换器
//        channel.exchangeDeclare(EXCHANGE_NAME, "direct", true, false, null);
        // 创建一个持久化、非排他的、非自动删除的队列
//        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        // 将交换器与队列通过路由键绑定
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);

        try {
            channel.txSelect();
            // 发送一条持久化的消息 ： helloworld!
            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
            int result = 1 / 0;
            channel.txCommit();
        } catch (Exception e) {
            e.printStackTrace();
            channel.txRollback();
        }
        // 关闭资源
        channel.close();
        connection.close();
    }
}
