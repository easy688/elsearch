package com.learn.elsearch.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liyuhui
 */
@Configuration
public class HeadersConfigure{

    @Bean
    public Queue creditBankQueue() {
        return new Queue("credit.bank");
    }
     @Bean
    public Queue credFinanceQueue(){
        return new Queue("credit.finance");
     }
     @Bean
    public HeadersExchange creditBankExchange() {
         return new HeadersExchange("creditBankExchange");
     }

     @Bean
    public HeadersExchange creditFinanceExchange() {
         return new HeadersExchange("creditFinanceExchange");
     }
     @Bean
     public Binding bindingCreditAExchange(Queue creditBankQueue, HeadersExchange creditBankExchange) {
         Map<String, Object> headerValues = new HashMap<>(16);
         headerValues.put("type", "cash");
         headerValues.put("aging", "fast");
         return BindingBuilder.bind(creditBankQueue).to(creditBankExchange).whereAll(headerValues).match();
     }
    @Bean
    public Binding bindingCreditBExchange(Queue credFinanceQueue, HeadersExchange creditFinanceExchange) {
        Map<String, Object> headerValues = new HashMap<>(16);
        headerValues.put("type", "cash");
        headerValues.put("aging", "fast");
        return BindingBuilder.bind(credFinanceQueue).to(creditFinanceExchange).whereAny(headerValues).match();
    }

}
