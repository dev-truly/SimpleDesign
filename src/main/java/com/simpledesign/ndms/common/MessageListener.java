package com.simpledesign.ndms.common;

import com.simpledesign.ndms.common.cdc.MessageObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile({"test-43110", "local"})
public class MessageListener {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue, // 큐 이름
            exchange = @Exchange(name = "maxwell", type = ExchangeTypes.TOPIC),
            key = "#.#" // 라우팅 키 패턴
    ), messageConverter = "jsonMessageConverter")
    public void receiveMessage(MessageObject message) {
        log.info(message.toString());
    }
}
