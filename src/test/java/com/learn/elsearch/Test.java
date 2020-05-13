package com.learn.elsearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class Test {

    @Autowired
    private RabbitMqSend rabbitMqSend;
    @Autowired
    private  SendHeaders sendHeaders;
    @Autowired
    private  FanoutSend fanoutSend;
    @Autowired
    private  RouteSend routeSend;


    /**
     * 简单对列
     */
    @org.junit.jupiter.api.Test
    public void contextLoads() {
        rabbitMqSend.sendUser();
    }
    /**
     * 工作对列，即在简单对列上增加多个消费者
     */
    @org.junit.jupiter.api.Test
    public void testWorkQueue() {
        for (int i = 0; i < 10; i++) {
            rabbitMqSend.send();
        }

    }
    /**
     * topic模式
     */
    @org.junit.jupiter.api.Test
    public void testTopic() {
        rabbitMqSend.sendTopic();
    }

    /**
     * topic 模式
     */
    @org.junit.jupiter.api.Test
    public void testTopicMatch() {
        rabbitMqSend.sendTopicMatch();
    }

    /**
     * headers 部分匹配测试，配置的whereAll，所以部分匹配收不到
     */
    @org.junit.jupiter.api.Test
    public void testHeaders(){
            Map<String,Object> head = new HashMap<>();
            head.put("type", "cash");
        sendHeaders.creditBank(head, "银行授信(部分匹配)");
    }

    /**
     * headers 完全匹配测试，配置的whereAll，所以可以收到
     */
    @org.junit.jupiter.api.Test
    public void testAllHeaders(){
        Map<String,Object> head = new HashMap<>();
        head.put("type", "cash");
        head.put("aging", "fast");
        sendHeaders.creditBank(head, "银行授信(完全匹配)");
    }
    /**
     * headers 部分匹配测试，配置的whereAny，所以部分匹配可以收到
     */
    @org.junit.jupiter.api.Test
    public void testFinanceHeaders(){
        Map<String,Object> head = new HashMap<>();
        head.put("type", "cash");
        sendHeaders.creditFinance(head, "金融授信(部分匹配)");
    }

    /**
     * headers 完全匹配测试，配置的whereAny，所以可以收到
     */
    @org.junit.jupiter.api.Test
    public void testAllFinanceHeaders(){
        Map<String,Object> head = new HashMap<>();
        head.put("type", "cash");
        head.put("aging", "fast");
        sendHeaders.creditBank(head, "金融授信(完全匹配)");
    }

    /**
     * fanout 交换机模式测试 ，发布订阅模式，不处理路由，所以路由直接“”
     */
    @org.junit.jupiter.api.Test
    public void testFanout(){
        fanoutSend.send();
    }

    /**
     * route模式测试,路由key为direct，a和b收到，为direct.simple，c收到
     */
    @org.junit.jupiter.api.Test
    public void testRoute(){
       routeSend.send();
    }

}
