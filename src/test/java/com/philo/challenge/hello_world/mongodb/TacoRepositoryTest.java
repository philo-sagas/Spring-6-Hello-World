package com.philo.challenge.hello_world.mongodb;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;

@DataMongoTest
public class TacoRepositoryTest {

    @Autowired
    TacoRepository tacoRepository;

    @BeforeEach
    public void setUp() {
        Flux<Taco> deleteAndInsert = tacoRepository.deleteAll()
                .thenMany(tacoRepository.saveAll(
                        Flux.just(
                                new Taco("FLTO", "Flour Tortillo"),
                                new Taco("GRBF", "Ground Beef"),
                                new Taco("CHED", "Cheddar Cheese")
                        )
                ));

        StepVerifier.create(deleteAndInsert)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    public void shouldSaveAndFetchIngredients() {
        tacoRepository.findAll()
                .as(StepVerifier::create)
                .recordWith(ArrayList::new)
                .thenConsumeWhile(x -> true)
                .consumeRecordedWith(tacos -> {
                    Assertions.assertThat(tacos).hasSize(3);
                    Assertions.assertThat(tacos).contains(new Taco("FLTO", "Flour Tortillo"));
                    Assertions.assertThat(tacos).contains(new Taco("GRBF", "Ground Beef"));
                    Assertions.assertThat(tacos).contains(new Taco("CHED", "Cheddar Cheese"));
                })
                .verifyComplete();

        tacoRepository.findByNameContains("o")
                .as(StepVerifier::create)
                .expectNextCount(2)
                .verifyComplete();
    }
}
