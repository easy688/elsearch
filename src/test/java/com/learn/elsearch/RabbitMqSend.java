package com.learn.elsearch;

import org.elasticsearch.action.ActionListener;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vo.UserVO;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class RabbitMqSend{
    @Autowired
    private AmqpTemplate rabbitTemplate;
    private  static final String TOPIC_EXCHANGE="topic_exchange";
    private  static final String MESSAGE = "topic.message";
    private  static final String TOPIC_ROUTE_KEY="topic.messages";

    /**
     * 发送简单消息
     */
    public void send() {
        String context = "hello "+ LocalDateTime.now();
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend("hello", context);
    }

    /**
     * 发送对象
     */
    public void sendUser() {
        UserVO userVO=new UserVO();
        userVO.setAge(15);
        userVO.setAddress("北京");
        userVO.setName("张三");
        this.rabbitTemplate.convertAndSend("hello", userVO);
    }

    /**
     * 发送topic模式消息
     */
    public void sendTopic() {
        String context = "topic message";
        System.out.println("Sender : " + context);
        rabbitTemplate.convertAndSend(RabbitMqSend.TOPIC_EXCHANGE, RabbitMqSend.MESSAGE, context);
    }

    /**
     * 发送topic模式
     */
    public void sendTopicMatch() {
        String context = "topic match message";
        System.out.println("Sender : " + context);
        rabbitTemplate.convertAndSend(RabbitMqSend.TOPIC_EXCHANGE,RabbitMqSend.TOPIC_ROUTE_KEY, context);
    }

}
