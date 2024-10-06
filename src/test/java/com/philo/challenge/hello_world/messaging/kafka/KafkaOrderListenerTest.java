package com.philo.challenge.hello_world.messaging.kafka;

import com.philo.challenge.hello_world.messaging.TacoOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.TimeUnit;

@ActiveProfiles("kafka-listener")
@SpringBootTest
public class KafkaOrderListenerTest {

    private final TacoOrder tacoOrder = new TacoOrder()
            .setDeliveryName("Philo")
            .setDeliveryStreet("Luogang")
            .setDeliveryCity("Guangzhou")
            .setDeliveryState("Guangdong")
            .setCcNumber("123456")
            .setCcExpiration("2024-10-31")
            .setCcCVV("789");

    @Autowired
    KafkaOrderMessagingService kafkaOrderMessagingService;

    @Test
    public void testSendOrder() throws InterruptedException {
        kafkaOrderMessagingService.sendOrder(tacoOrder);
        TimeUnit.SECONDS.sleep(3L);
    }
}
