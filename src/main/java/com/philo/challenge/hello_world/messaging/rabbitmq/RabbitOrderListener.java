package com.philo.challenge.hello_world.messaging.rabbitmq;

import com.philo.challenge.hello_world.messaging.TacoOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Profile("rabbit-listener")
@Component
public class RabbitOrderListener {

    @RabbitListener(queues = "tacocloud.order")
    public void listeningOrder(TacoOrder order) {
        log.info("Listening order: {}", order);
    }
}
