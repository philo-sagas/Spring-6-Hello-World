package com.philo.challenge.hello_world.messaging.activemq;

import com.philo.challenge.hello_world.messaging.TacoOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Profile("jms-listener")
@Component
public class JmsOrderListener {

    @JmsListener(destination = "tacocloud.order.queue")
    public void listeningOrder(TacoOrder order) {
        log.info("Listening order: {}", order);
    }
}
