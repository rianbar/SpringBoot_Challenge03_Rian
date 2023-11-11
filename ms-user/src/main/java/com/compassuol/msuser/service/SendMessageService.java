package com.compassuol.msuser.service;

import com.compassuol.msuser.configuration.RabbitMqConfig;
import com.compassuol.msuser.enumerate.EventEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendMessageService {

    private final RabbitTemplate rabbitTemplate;
    private final DataTransferUserObject parseObject;

    public void sendMessage(String email, EventEnum eventType) {
        var objectMessage = parseObject.setPayloadMessage(email, eventType);
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_NAME, "routingJsonKey",
                parseObject.parseObjectToJson(objectMessage));
    }
}
