package com.philo.challenge.hello_world.r2dbc;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = "id")
@Table
public class Ingredient {

    @Id
    private long id;

    @NonNull
    private String slug;

    @NonNull
    private String name;

    @NonNull
    private Type type;

    public enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
