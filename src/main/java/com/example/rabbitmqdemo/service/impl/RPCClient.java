package com.example.rabbitmqdemo.service.impl;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class RPCClient {
    private Connection connection;
    private Channel channel;
    private String requestQueueName = "rpc_queue";
    private String replyQueueName;
//    private QueueingConsumer queueingConsumer;

}
