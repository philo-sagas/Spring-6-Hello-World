package com.philo.challenge.hello_world.mongodb;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface TacoRepository extends ReactiveMongoRepository<Taco, Long> {
    Flux<Taco> findByNameContains(String name);
}
