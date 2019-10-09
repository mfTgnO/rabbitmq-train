package com.example.rabbitmqdemo.service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @createDate: 2019-08-30 16:39
 * @description:
 */
public interface IRabbitConsumerService {
    void consumerV1() throws IOException, TimeoutException, InterruptedException;

    void consumerV2();
}
