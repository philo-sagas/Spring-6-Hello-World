package com.philo.challenge.hello_world.r2dbc;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;

@DataR2dbcTest
public class IngredientRepositoryTest {

    @Autowired
    IngredientRepository ingredientRepository;

    @BeforeEach
    public void setUp() {
        Flux<Ingredient> deleteAndInsert = ingredientRepository.deleteAll()
                .thenMany(ingredientRepository.saveAll(
                        Flux.just(
                                new Ingredient("FLTO", "Flour Tortillo", Ingredient.Type.WRAP),
                                new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN),
                                new Ingredient("CHED", "Cheddar Cheese", Ingredient.Type.CHEESE)
                        )
                ));

        StepVerifier.create(deleteAndInsert)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    public void shouldSaveAndFetchIngredients() {
        ingredientRepository.findAll()
                .as(StepVerifier::create)
                .recordWith(ArrayList::new)
                .thenConsumeWhile(x -> true)
                .consumeRecordedWith(ingredients -> {
                    Assertions.assertThat(ingredients).hasSize(3);
                    Assertions.assertThat(ingredients).contains(new Ingredient("FLTO", "Flour Tortillo", Ingredient.Type.WRAP));
                    Assertions.assertThat(ingredients).contains(new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN));
                    Assertions.assertThat(ingredients).contains(new Ingredient("CHED", "Cheddar Cheese", Ingredient.Type.CHEESE));
                })
                .verifyComplete();

        ingredientRepository.findBySlug("FLTO")
                .as(StepVerifier::create)
                .expectNext(new Ingredient("FLTO", "Flour Tortillo", Ingredient.Type.WRAP))
                .verifyComplete();
    }
}
