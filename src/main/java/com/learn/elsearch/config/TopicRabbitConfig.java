package com.learn.elsearch.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicRabbitConfig {

    private  static final String MESSAGE = "topic.message";
    private  static final String MESSAGES = "topic.messages";
    private  static final String TOPIC_EXCHANGE="topic_exchange";
    private  static final  String TOPIC_ROUTE_KEY="topic.#";

    /**
     * 创建对列
     * @return
     */
    @Bean
    public Queue queueMessage() {
        return new Queue(TopicRabbitConfig.MESSAGE);
    }

    /**
     * 创建对列
     * @return
     */
    @Bean
    public Queue queueMessages() {
        return new Queue(TopicRabbitConfig.MESSAGES);
    }

    /**
     * 创建交换机
     * @return
     */
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(TopicRabbitConfig.TOPIC_EXCHANGE);
    }

    /**
     * 将队列和交换机进行绑定
     * @param queueMessage
     * @param exchange
     * @return
     */
   @Bean
    public Binding bindingExchangeMessage(Queue queueMessage, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with(TopicRabbitConfig.MESSAGE);
    }

/*    *//**
     * 将队列和交换机进行绑定，用topic方式
     * @param queueMessages
     * @param exchange
     * @return
     */
    @Bean
    public Binding bindingExchangeMessages(Queue queueMessages, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessages).to(exchange).with(TopicRabbitConfig.TOPIC_ROUTE_KEY);
    }
}
