package com.philo.challenge.hello_world.messaging;

public interface OrderMessagingService {
    void sendOrder(TacoOrder order);

    TacoOrder receiveOrder();
}
