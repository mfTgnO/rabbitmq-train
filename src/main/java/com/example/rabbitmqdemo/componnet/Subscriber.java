package com.example.rabbitmqdemo.componnet;

import com.example.rabbitmqdemo.model.Msg;
import com.example.rabbitmqdemo.service.IMsgService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @createDate: 2019-08-30 15:38
 * @description:
 */
@Component
public class Subscriber {
    private IMsgService iMsgService;

    public Subscriber(IMsgService iMsgService) {
        this.iMsgService = iMsgService;
    }

    @RabbitListener(queues = "${jsa.rabbitmq.queue}")
    public void receivedMsg(String msg) {
        System.out.println("Received Message: " + msg);
        Msg m = new Msg();
        m.setMsg(msg);
        iMsgService.save(m);
    }
}
