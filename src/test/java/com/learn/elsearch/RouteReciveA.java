package com.learn.elsearch;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "direct.A")
public class RouteReciveA {
    @RabbitHandler
    public void recive(String message){
        System.out.println("direct  A recive："+message);
    }
}
