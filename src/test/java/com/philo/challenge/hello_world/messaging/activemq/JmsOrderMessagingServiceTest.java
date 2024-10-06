package com.philo.challenge.hello_world.messaging.activemq;

import com.philo.challenge.hello_world.messaging.TacoOrder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class JmsOrderMessagingServiceTest {

    private final TacoOrder tacoOrder = new TacoOrder()
            .setDeliveryName("Philo")
            .setDeliveryStreet("Luogang")
            .setDeliveryCity("Guangzhou")
            .setDeliveryState("Guangdong")
            .setCcNumber("123456")
            .setCcExpiration("2024-10-31")
            .setCcCVV("789");

    @Autowired
    JmsOrderMessagingService jmsOrderMessagingService;

    @Test
    @Order(1)
    public void testSendOrder() {
        jmsOrderMessagingService.sendOrder(tacoOrder);
    }

    @Test
    @Order(2)
    public void testReceiveOrder() {
        TacoOrder order = jmsOrderMessagingService.receiveOrder();
        Assertions.assertThat(order).isEqualTo(tacoOrder);
    }
}
