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

    /**
     * 过期时间
     * <p>
     * fanout
     * 它会把所有发送到该交换器的消息路由到所有与该交换器绑定的队列中，即无视RoutingKey和BindingKey的匹配规则。
     * fanout类型exchange的routingKey为空字符串
     *
     * @param msg 消息
     */
    public void produceMsgV2(String msg) {
        amqpTemplate.convertAndSend("exchange.normal", "", msg);
        System.out.println("过期时间 Send msg = " + msg);
    }
}
