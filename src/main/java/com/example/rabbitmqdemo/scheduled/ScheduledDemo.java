package com.example.rabbitmqdemo.scheduled;

import com.example.rabbitmqdemo.componnet.Publisher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.UUID;

/**
 * 定时任务
 */
@Configuration
@EnableScheduling
public class ScheduledDemo {
    private Publisher publisher;

    public ScheduledDemo(Publisher publisher) {
        this.publisher = publisher;
    }
    /**
     * 让我们首先将任务配置为在固定延迟后运行：
     */
    /*@Scheduled(fixedDelay = 1000)
    public void scheduleFixedDealyTask() {
        System.out.println("Fixed delay task - " + System.currentTimeMillis() / 1000);
    }*/

    /**
     * 现在，让我们在固定的时间间隔内执行一项任务：
     */
    /*@Scheduled(fixedRate = 1000)
    public void scheduleFixedRateTask() {
        System.out.println(
                "Fixed rate task - " + System.currentTimeMillis() / 1000);
    }*/

    /**
     * 每分钟执行
     */
    /*@Scheduled(cron = "30 * * * * *")
    public void scheduleEveryMinuteTask() {
        System.out.println(LocalDateTime.now());
        System.out.println("Every minute task - " + System.currentTimeMillis() / 1000);
    }*/

    /**
     * 5000 milliseconds
     */
    @Scheduled(fixedRate = 5000)
    public void pubMsgTask() {
        UUID uuid = UUID.randomUUID();
        publisher.produceMsg(uuid.toString());
        System.out.println("Msg: " + uuid.toString());
    }
}
