package com.philo.challenge.hello_world.rsocket;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class GratuityIn {
    private BigDecimal billTotal;

    private int percent;
}
