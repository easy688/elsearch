package com.learn.elsearch;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "topic.message")
public class TopicRecive {

        @RabbitHandler
        public void process(String message) {
            System.out.println("Topic Receiver: " + message);
        }
}
