package com.learn.elsearch;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import vo.UserVO;

@Component
@RabbitListener(queues = "hello")
public class RabbitMqWorkerRecive {
    /**
     * 接受简单消息
     * @param hello
     */
    @RabbitHandler
    public void process(String hello) {
        System.out.println("Receiver  work : " + hello);
    }

    /**
     * 接受对象
     * @param userVO
     */
    @RabbitHandler
    public void process(UserVO userVO) {
        System.out.println("Receiver  work: " + userVO.toString());
    }

}
