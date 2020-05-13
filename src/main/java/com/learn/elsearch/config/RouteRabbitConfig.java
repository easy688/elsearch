package com.learn.elsearch.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteRabbitConfig {
    /**
     * 创建对列
     * @return
     */
    @Bean
    public Queue oneMessage() {
        return new Queue("direct.A");
    }

    /**
     * 创建对列
     * @return
     */
    @Bean
    public Queue twoMessage() {
        return new Queue("direct.B");
    }

    /**
     * 创建对列
     * @return
     */
    @Bean
    public Queue threeMessage() {
        return new Queue("direct.C");
    }

    /**
     * 创建fanout交换机
     * @return
     */
    @Bean
    DirectExchange directExchange() {
        return new DirectExchange("directExchange");
    }

    /**
     * 将对列绑定到交换机
     * @param oneMessage
     * @param directExchange
     * @return
     */
    @Bean
    Binding bindingExchangeOne(Queue oneMessage, DirectExchange directExchange) {
        return BindingBuilder.bind(oneMessage).to(directExchange).with("direct");
    }

    /**
     * 将队列绑定到交换机
     * @param twoMessage
     * @param directExchange
     * @return
     */
    @Bean
    Binding bindingExchangeTwo(Queue twoMessage, DirectExchange directExchange) {
        return BindingBuilder.bind(twoMessage).to(directExchange).with("direct");
    }

    /**
     * 将队列绑定到交换机
     * @param threeMessage
     * @param directExchange
     * @return
     */
    @Bean
    Binding bindingExchangeThree(Queue threeMessage, DirectExchange directExchange) {
        return BindingBuilder.bind(threeMessage).to(directExchange).with("direct.simple");
    }

}
