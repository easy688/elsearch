package com.learn.elsearch;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ReciveHead {
    @RabbitHandler
    @RabbitListener(queues = "credit.bank")
    public void creditBank(String msg) {
        System.out.println("credit.bank receive message: "+msg);
    }

    @RabbitHandler
    @RabbitListener(queues = "credit.finance")
    public void creditFinance(String msg) {
        System.out.println("credit.finance receive message: "+msg);
    }
}
