package com.philo.challenge.hello_world.rsocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;

@Slf4j
@Controller
public class MessageController {

    @MessageMapping("greeting/{name}")
    public Mono<String> handleGreeting(@DestinationVariable("name") String name, Mono<String> greetingMono) {
        return greetingMono.doOnNext(greeting -> log.info("Received a greeting from {}: {}", name, greeting))
                .map(greeting -> "Hello to you, too, " + name);
    }

    @MessageMapping("stock/{symbol}")
    public Flux<StockQuote> getStockPrice(@DestinationVariable("symbol") String symbol) {
        return Flux.interval(Duration.ofSeconds(1L))
                .map(i -> {
                    BigDecimal price = BigDecimal.valueOf(Math.random() * 10);
                    if (i == 3) {
                        throw new RuntimeException("Three seconds to break!");
                    }
                    StockQuote stockQuote = new StockQuote(symbol, i, price, Instant.now());
                    log.info("stockQuote: {}", stockQuote);
                    return stockQuote;
                });
    }

    @MessageMapping("alert")
    public Mono<Void> setAlert(Mono<Alert> alertMono) {
        return alertMono.doOnNext(alert ->
                        log.info("{} alert ordered by {} at {}",
                                alert.getLevel(), alert.getOrderedBy(), alert.getOrderedAt()))
                .thenEmpty(Mono.empty());
    }

    @MessageMapping("gratuity")
    public Flux<GratuityOut> calculate(Flux<GratuityIn> gratuityInFlux) {
        return gratuityInFlux.doOnNext(in -> log.info("Calculating gratuity: {}", in))
                .map(in -> {
                    double percentAsDecimal = in.getPercent() / 100.0;
                    BigDecimal gratuity = in.getBillTotal()
                            .multiply(BigDecimal.valueOf(percentAsDecimal));
                    return new GratuityOut(in.getBillTotal(), in.getPercent(), gratuity);
                });
    }

}
