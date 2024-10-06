package com.philo.challenge.hello_world.messaging.activemq;

import com.philo.challenge.hello_world.messaging.OrderMessagingService;
import com.philo.challenge.hello_world.messaging.TacoOrder;
import jakarta.jms.Destination;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JmsOrderMessagingService implements OrderMessagingService {

    @NonNull
    private JmsTemplate jmsTemplate;

    @NonNull
    private Destination orderQueue;

    @Override
    public void sendOrder(TacoOrder order) {
        jmsTemplate.convertAndSend(orderQueue, order, message -> {
            message.setStringProperty("X_ORDER_SOURCE", "WEB");
            return message;
        });
        log.info("Sent order: {}", order);
    }

    @Override
    public TacoOrder receiveOrder() {
        TacoOrder order = (TacoOrder) jmsTemplate.receiveAndConvert(orderQueue);
        log.info("Received order: {}", order);
        return order;
    }
}
