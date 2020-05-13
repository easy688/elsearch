package com.learn.elsearch;

import ch.qos.logback.classic.pattern.MessageConverter;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SendHeaders {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void creditBank(Map<String, Object> head, String msg){
        System.out.println("creditBank send----"+msg);
        rabbitTemplate.convertAndSend("creditBankExchange", "credit.bank",getMessage(head,msg));
    }

    public void creditFinance(Map<String, Object> head, String msg){
        System.out.println("creditFinance send----"+msg);
        rabbitTemplate.convertAndSend("creditFinanceExchange", "credit.finance",getMessage(head,msg));
    }
    private Message getMessage(Map<String, Object> head, Object msg){
        MessageProperties messageProperties = new MessageProperties();
        head.forEach((t,u)-> messageProperties.setHeader(t,u));
        SimpleMessageConverter messageConverter = new SimpleMessageConverter();
        return messageConverter.toMessage(msg, messageProperties);
    }
}
