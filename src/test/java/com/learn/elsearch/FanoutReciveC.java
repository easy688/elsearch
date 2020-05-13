package com.learn.elsearch;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "fanout.C")
public class FanoutReciveC {
    @RabbitHandler
    public void recive(String message){
        System.out.println("fanout C recive："+message);
    }
}
