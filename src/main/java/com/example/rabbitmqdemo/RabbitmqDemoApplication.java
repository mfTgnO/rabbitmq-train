package com.example.rabbitmqdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.rabbitmqdemo.mapper")
public class RabbitmqDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbitmqDemoApplication.class, args);
	}

}
