package com.philo.challenge.hello_world.messaging.kafka;

import com.philo.challenge.hello_world.messaging.TacoOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Profile("kafka-listener")
@Component
public class KafkaOrderListener {

    @KafkaListener(topics = "tacocloud.order.topic")
    public void listeningOrder(TacoOrder order) {
        log.info("Listening order: {}", order);
    }
}
