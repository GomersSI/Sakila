package com.example.sakila.controllers;

import com.example.sakila.entities.Film;
import com.example.sakila.entities.PartialActor;
import com.example.sakila.input.FilmActorsInput;
import com.example.sakila.input.FilmInput;
import com.example.sakila.repositories.FilmRepository;
import com.example.sakila.services.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class FilmController {
    @Autowired
    FilmRepository filmRepository;

    @Autowired
    FilmService filmService;

    @GetMapping("/films")
    public List<Film> listFilms(@RequestParam(required = false) Map<String, String> params){
        return filmService.listFilms(params);
    }

    @GetMapping("/films/{id}")
    public Film getFilmById(@PathVariable Short id){
        return filmService.getFilmById(id);
    }

    @GetMapping("/films/{id}/actors")
    public List<PartialActor> getFilmActorsById(@PathVariable Short id){
        return filmService.getFilmActorsById(id);
    }
    @PostMapping("/films")
    public Film createFilm (@Validated @RequestBody FilmInput data){
        return filmService.createFilm(data);
    }

    @DeleteMapping("/films/{id}")
    public void deleteFilm(@PathVariable Short id){
        filmService.deleteFilm(id);
    }

    @PatchMapping("/films/{id}")
    public Film patchFilm(@PathVariable Short id, @Validated @RequestBody FilmInput data){
        return filmService.patchFilm(id, data);
    }

    @PostMapping("/films/{id}/actors")
    public List<PartialActor> setFilmActorsById(@PathVariable Short id, @RequestBody FilmActorsInput data){
        return filmService.setFilmActorsById(id, data);
    }

    @DeleteMapping("/films/{id}/actors")
    public void clearFilmActors(@PathVariable Short id){
        filmService.clearFilmActors(id);
    }

    @DeleteMapping("/films/{filmId}/actors/{actorId}")
    public List<PartialActor> deleteFilmActor(@PathVariable Short filmId, @PathVariable Short actorId){
        return filmService.deleteFilmActor(filmId, actorId);
    }

    @PatchMapping("/films/{id}/actors")
    public List<PartialActor> patchFilmActors(@PathVariable Short id, @RequestBody FilmActorsInput data){
        return filmService.patchFilmActorsById(id, data);
    }
}
