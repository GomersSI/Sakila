package com.example.sakila.input;

import lombok.Data;

import java.util.List;

@Data
public class FilmInput {
    private String title;

    private Byte languageId;
    private String desc;

    private List<Short> categories;
}
