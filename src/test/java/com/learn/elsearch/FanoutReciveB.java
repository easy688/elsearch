package com.learn.elsearch;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "fanout.B")
public class FanoutReciveB {
    @RabbitHandler
    public void recive(String message){
        System.out.println("fanout B recive："+message);
    }
}
