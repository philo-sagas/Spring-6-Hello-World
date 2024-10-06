package com.philo.challenge.hello_world.r2dbc;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface IngredientRepository extends R2dbcRepository<Ingredient, Long> {
    Mono<Ingredient> findBySlug(String slug);
}
