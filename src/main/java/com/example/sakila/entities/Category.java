package com.example.sakila.entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
@Getter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Byte id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(
            name = "film_category",
            joinColumns = {@JoinColumn(name = "category_id")},
            inverseJoinColumns = {@JoinColumn(name = "film_id")}
    )
    private List<PartialFilm> films = new ArrayList<>();
}
