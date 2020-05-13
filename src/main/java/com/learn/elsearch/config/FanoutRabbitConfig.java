package com.learn.elsearch.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutRabbitConfig {
    /**
     * 创建对列
     * @return
     */
    @Bean
    public Queue aMessage() {
        return new Queue("fanout.A");
    }

    /**
     * 创建对列
     * @return
     */
    @Bean
    public Queue bMessage() {
        return new Queue("fanout.B");
    }

    /**
     * 创建对列
     * @return
     */
    @Bean
    public Queue cMessage() {
        return new Queue("fanout.C");
    }

    /**
     * 创建fanout交换机
     * @return
     */
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    /**
     * 将对列绑定到交换机
     * @param aMessage
     * @param fanoutExchange
     * @return
     */
    @Bean
    Binding bindingExchangeA(Queue aMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(aMessage).to(fanoutExchange);
    }

    /**
     * 将队列绑定到交换机
     * @param bMessage
     * @param fanoutExchange
     * @return
     */
    @Bean
    Binding bindingExchangeB(Queue bMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(bMessage).to(fanoutExchange);
    }

    /**
     * 将队列绑定到交换机
     * @param cMessage
     * @param fanoutExchange
     * @return
     */
    @Bean
    Binding bindingExchangeC(Queue cMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(cMessage).to(fanoutExchange);
    }

}
