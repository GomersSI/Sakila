package com.example.sakila.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Table(name = "language")
@Getter
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "language_id")
    private Byte id;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "language", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Film> films;
}
