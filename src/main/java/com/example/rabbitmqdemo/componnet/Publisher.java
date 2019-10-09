package com.example.rabbitmqdemo.componnet;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @createDate: 2019-08-30 15:34
 * @description:
 */
@Component
public class Publisher {
    public Publisher(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    private AmqpTemplate amqpTemplate;

    @Value("${jsa.rabbitmq.exchange}")
    private String exchange;

    @Value("${jsa.rabbitmq.routingkey}")
    private String routingKey;

    public void produceMsg(String msg) {
        amqpTemplate.convertAndSend(exchange, routingKey, msg);
        System.out.println("Send msg = " + msg);
    }
}
