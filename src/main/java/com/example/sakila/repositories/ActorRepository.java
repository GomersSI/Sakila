package com.example.sakila.repositories;

import com.example.sakila.entities.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActorRepository extends JpaRepository<Actor, Short> {

    List<Actor> findByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCase(String firstName, String lastName);

    @Query(value="SELECT * FROM actor WHERE first_name = :firstName", nativeQuery = true)
    List<Actor> findByFirstName(String firstName);

    @Query(value="SELECT * FROM actor WHERE last_name = :lastName", nativeQuery = true)
    List<Actor> findByLastName(String lastName);
}
