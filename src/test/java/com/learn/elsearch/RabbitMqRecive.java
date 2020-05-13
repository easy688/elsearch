package com.learn.elsearch;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;
import vo.UserVO;

/**
 * 在@Queue和@Exchange注解中都有autoDelete属性，值是布尔类型的字符串。如：autoDelete=“false”。
 * @Queue：当所有消费客户端断开连接后，是否自动删除队列： true：删除，false:不删除。
 * @Exchange:当所有绑定队列都不在使用时，是否自动删除交换器： true：删除，false：不删除。
 * 当所有消费客户端断开连接时，而我们对RabbitMQ消息进行了持久化，那么这时未被消费的消息存于RabbitMQ服务
 * 器的内存中，如果RabbitMQ服务器都关闭了，那么未被消费的数据也都会丢失了。
 * @RabbitListener(queues = "hello",
 *         bindings = @QueueBinding(
 *                 value = @Queue(value = "${mq.config.queue.error}",autoDelete = "false"),
 *                 exchange = @Exchange(value = "${mq.config.exchange}", type = ExchangeTypes.DIRECT,autoDelete = "false"),
 *                 key = "${mq.config.queue.error.routing.key}"
 *         ))
 */
@Component
@RabbitListener(queues = "hello")
public class RabbitMqRecive {
    /**
     * 接受简单消息
     * @param hello
     */
    @RabbitHandler
    public void process(String hello) {
        System.out.println("Receiver  : " + hello);
    }

    /**
     * 接受对象
     * @param userVO
     */
    @RabbitHandler
    public void process(UserVO userVO) {
        System.out.println("Receiver  : " + userVO.toString());
    }

}
