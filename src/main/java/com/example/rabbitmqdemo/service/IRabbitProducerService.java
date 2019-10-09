package com.example.rabbitmqdemo.service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @createDate: 2019-08-30 16:06
 * @description:
 */
public interface IRabbitProducerService {
    void producerV1() throws IOException, TimeoutException;

    void producerV2();

    void producerV3() throws IOException, TimeoutException;

    void producerV4() throws IOException, TimeoutException;

    void producerV5() throws IOException, TimeoutException;

    /**
     * 死信队列
     *
     * @throws IOException
     * @throws TimeoutException
     */
    void producerV6() throws IOException, TimeoutException;

    /**
     * 优先级队列
     *
     * @throws IOException
     * @throws TimeoutException
     */
    void producerV7() throws IOException, TimeoutException;

    /**
     * 生产者确认,事务机制
     *
     * @throws IOException
     * @throws TimeoutException
     */
    void producerV8() throws IOException, TimeoutException;
}
