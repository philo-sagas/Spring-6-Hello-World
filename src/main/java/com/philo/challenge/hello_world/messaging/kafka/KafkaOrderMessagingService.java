package com.philo.challenge.hello_world.messaging.kafka;

import com.philo.challenge.hello_world.messaging.OrderMessagingService;
import com.philo.challenge.hello_world.messaging.TacoOrder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaOrderMessagingService implements OrderMessagingService {

    @NonNull
    private KafkaTemplate kafkaTemplate;

    @Override
    public void sendOrder(TacoOrder order) {
        kafkaTemplate.send("tacocloud.order.topic", order);
        log.info("Sent order: {}", order);
    }

    @Override
    public TacoOrder receiveOrder() {
        return null;
    }
}
