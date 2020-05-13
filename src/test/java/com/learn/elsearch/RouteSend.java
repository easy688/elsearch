package com.learn.elsearch;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RouteSend {
    @Autowired
    private AmqpTemplate rabbitTemplate;
    public void send(){
        String message="send direct message";
        System.out.println("============="+message);
        //第一个参数是交换机，第二个参数是路由，第三个参数是消息
        rabbitTemplate.convertAndSend("directExchange","direct",message);
    }
}
