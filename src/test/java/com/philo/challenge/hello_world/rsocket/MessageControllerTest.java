package com.philo.challenge.hello_world.rsocket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;

@SpringBootTest
public class MessageControllerTest {

    String rsocketHost = "localhost";

    @Value("${spring.rsocket.server.port}")
    int rsocketPort;

    @Autowired
    RSocketRequester.Builder builder;

    RSocketRequester requester;

    @BeforeEach
    public void setUp() {
        requester = builder.tcp(rsocketHost, rsocketPort);
    }

    @Test
    public void testHandleGreeting() {
        String who = "Craig";
        requester.route("greeting/{name}", who)
                .data("Hello RSocket!")
                .retrieveMono(String.class)
                .as(StepVerifier::create)
                .expectNext("Hello to you, too, " + who)
                .verifyComplete();
    }

    @Test
    public void testGetStockPrice() {
        String stockSymbol = "XYZ";
        requester.route("stock/{symbol}", stockSymbol)
                .retrieveFlux(StockQuote.class)
                .as(StepVerifier::create)
                .expectNextCount(3)
                .verifyErrorMessage("Three seconds to break!");
    }

    @Test
    public void testSetAlert() {
        requester.route("alert")
                .data(new Alert(Alert.Level.RED, "Craig", Instant.now()))
                .send()
                .as(StepVerifier::create)
                .verifyComplete();
    }

    @Test
    public void testCalculate() {
        Flux<GratuityIn> gratuityInFlux = Flux.fromArray(new GratuityIn[]{
                new GratuityIn(BigDecimal.valueOf(35.50), 18),
                new GratuityIn(BigDecimal.valueOf(10.00), 15),
                new GratuityIn(BigDecimal.valueOf(23.25), 20),
                new GratuityIn(BigDecimal.valueOf(52.75), 18),
                new GratuityIn(BigDecimal.valueOf(80.00), 15)
        }).delayElements(Duration.ofSeconds(1L));

        requester.route("gratuity")
                .data(gratuityInFlux)
                .retrieveFlux(GratuityOut.class)
                .doOnNext(out -> System.out.printf("%d%% gratuity on %.2f is %.2f\n",
                        out.getPercent(), out.getBillTotal(), out.getGratuity()))
                .as(StepVerifier::create)
                .expectNextCount(5)
                .verifyComplete();
    }

}
