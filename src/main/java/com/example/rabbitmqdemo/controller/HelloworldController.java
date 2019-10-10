package com.example.rabbitmqdemo.controller;

import com.example.rabbitmqdemo.componnet.Publisher;
import com.example.rabbitmqdemo.service.IRabbitConsumerService;
import com.example.rabbitmqdemo.service.IRabbitProducerService;
import com.example.rabbitmqdemo.utils.JsonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @createDate: 2019-08-30 15:39
 * @description:
 */
@RestController
@RequestMapping("/rabbitmq")
public class HelloworldController {
    private Publisher publisher;
    private IRabbitProducerService iRabbitProducerService;
    private IRabbitConsumerService iRabbitConsumerService;

    public HelloworldController(Publisher publisher, IRabbitProducerService iRabbitProducerService, IRabbitConsumerService iRabbitConsumerService) {
        this.publisher = publisher;
        this.iRabbitProducerService = iRabbitProducerService;
        this.iRabbitConsumerService = iRabbitConsumerService;
    }

    /**
     * 发送10条消息
     *
     * @param msg 消息
     * @return JsonResult
     */
    @GetMapping("/send")
    public JsonResult sendMessage(@RequestParam("msg") String msg) {
        System.out.println("msg: " + msg);
        for (int i = 0; i < 10; i++) {
            publisher.produceMsg(msg + " No." + i);
        }
        return new JsonResult<>("Successfully Msg Sent");
    }

    /**
     * Time to Live
     *
     * @param msg message
     * @return JsonResult
     */
    @GetMapping("/sendTTL")
    public JsonResult sendMessageTTL(@RequestParam("msg") String msg) {
        System.out.println("msg: " + msg);
        for (int i = 0; i < 10; i++) {
            publisher.produceMsgV2(msg + " No." + i);
        }
        return new JsonResult<>("Successfully Msg Sent");
    }

    @GetMapping("/producerV1")
    public JsonResult producerV1() throws IOException, TimeoutException {
        iRabbitProducerService.producerV1();
        return new JsonResult<>("Successfully Msg Sent");
    }

    @GetMapping("/producerV2")
    public JsonResult producerV2() {
        iRabbitProducerService.producerV2();
        return new JsonResult<>("Successfully Msg Sent");
    }

    @GetMapping("/producerV3")
    public JsonResult producerV3() throws IOException, TimeoutException {
        iRabbitProducerService.producerV3();
        return new JsonResult<>("Successfully Msg Sent");
    }

    @GetMapping("/producerV4")
    public JsonResult producerV4() throws IOException, TimeoutException {
        iRabbitProducerService.producerV4();
        return new JsonResult<>("Successfully Msg Sent");
    }

    @GetMapping("/producerV5")
    public JsonResult producerV5() throws IOException, TimeoutException {
        iRabbitProducerService.producerV5();
        return new JsonResult<>("Successfully Msg Sent");
    }

    /**
     * 死信队列
     *
     * @throws IOException
     * @throws TimeoutException
     */
    @GetMapping("/producerV6")
    public JsonResult producerV6() throws IOException, TimeoutException {
        iRabbitProducerService.producerV6();
        return new JsonResult<>("Successfully Msg Sent");
    }

    /**
     * 优先级队列
     *
     * @throws IOException
     * @throws TimeoutException
     */
    @GetMapping("/producerV7")
    public JsonResult producerV7() throws IOException, TimeoutException {
        iRabbitProducerService.producerV7();
        return new JsonResult<>("Successfully Msg Sent");
    }

    /**
     * 生产者确认,事务机制
     *
     * @throws IOException
     * @throws TimeoutException
     */
    @GetMapping("/producerV8")
    public JsonResult producerV8() throws IOException, TimeoutException {
        iRabbitProducerService.producerV8();
        return new JsonResult<>("Successfully Msg Sent");
    }

    @GetMapping("/consumerV1")
    public JsonResult consumerV1() throws IOException, TimeoutException, InterruptedException {
        iRabbitConsumerService.consumerV1();
        return new JsonResult<>("Successfully Msg Consumer");
    }

    @GetMapping("/consumerV2")
    public JsonResult consumerV2() {
        iRabbitConsumerService.consumerV2();
        return new JsonResult<>("Successfully Msg Consumer");
    }
}
