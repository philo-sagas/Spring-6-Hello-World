package com.philo.challenge.hello_world.messaging;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TacoOrder {
    private String deliveryName;

    private String deliveryStreet;

    private String deliveryCity;

    private String deliveryState;

    private String ccNumber;

    private String ccExpiration;

    private String ccCVV;
}
