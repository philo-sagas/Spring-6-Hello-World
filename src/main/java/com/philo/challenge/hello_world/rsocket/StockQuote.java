package com.philo.challenge.hello_world.rsocket;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
public class StockQuote {
    private String symbol;

    private long sequence;

    private BigDecimal price;

    private Instant timestamp;
}
