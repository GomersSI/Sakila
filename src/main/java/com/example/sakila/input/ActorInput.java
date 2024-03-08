package com.example.sakila.input;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ActorInput {
    @Size(min = 1, max = 45)
    private String firstName;

    @Size(min = 1, max = 45)
    private String lastName;
}
