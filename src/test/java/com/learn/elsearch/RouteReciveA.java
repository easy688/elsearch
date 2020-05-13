package com.learn.elsearch;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消息确认机制默认打开，消费者发生异常会一直重新发送
 */
@Component
@RabbitListener(queues = "direct.A")
public class RouteReciveA {
    @RabbitHandler
    public void recive(String message){
        System.out.println("direct  A recive："+message);
        int i=1/0;
        System.out.println("异常==========");

    }
}
