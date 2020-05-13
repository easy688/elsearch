package com.learn.elsearch;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FanoutSend {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(){
        String message="fanout send message";
        System.out.println("==================="+message);
        rabbitTemplate.convertAndSend("fanoutExchange","",message);
    }

}
