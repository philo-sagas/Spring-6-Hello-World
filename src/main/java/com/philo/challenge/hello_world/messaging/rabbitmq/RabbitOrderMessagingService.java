package com.philo.challenge.hello_world.messaging.rabbitmq;

import com.philo.challenge.hello_world.messaging.OrderMessagingService;
import com.philo.challenge.hello_world.messaging.TacoOrder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitOrderMessagingService implements OrderMessagingService {

    @NonNull
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendOrder(TacoOrder order) {
        rabbitTemplate.convertAndSend("tacocloud.order", order, message -> {
            MessageProperties props = message.getMessageProperties();
            props.setHeader("X_ORDER_SOURCE", "WEB");
            return message;
        });
        log.info("Sent order: {}", order);
    }

    @Override
    public TacoOrder receiveOrder() {
        TacoOrder order = rabbitTemplate.receiveAndConvert("tacocloud.order", ParameterizedTypeReference.forType(TacoOrder.class));
        log.info("Received order: {}", order);
        return order;
    }
}
