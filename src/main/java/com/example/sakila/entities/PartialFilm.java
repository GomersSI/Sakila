package com.example.sakila.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "film")
@Getter
@Setter
public class PartialFilm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    private Short id;

    @Column(name = "title")
    private String title;

    @Column(name = "language_id")
    private Byte languageId;

    @Column(name = "description")
    private String desc;
}
