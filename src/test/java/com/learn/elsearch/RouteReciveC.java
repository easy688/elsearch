package com.learn.elsearch;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "direct.C")
public class RouteReciveC {
    @RabbitHandler
    public void recive(String message){
        System.out.println("direct  C recive："+message);
    }
}
