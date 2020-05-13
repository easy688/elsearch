package com.learn.elsearch;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "direct.B")
public class RouteReciveB {
    @RabbitHandler
    public void recive(String message){
        System.out.println("direct  B recive："+message);
    }
}
