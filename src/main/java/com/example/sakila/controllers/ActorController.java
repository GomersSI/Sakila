package com.example.sakila.controllers;

import com.example.sakila.entities.Actor;
import com.example.sakila.entities.PartialFilm;
import com.example.sakila.input.ActorFilmsInput;
import com.example.sakila.input.ActorInput;
import com.example.sakila.services.ActorService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class ActorController {
    @Autowired
    ActorService actorService;

    @GetMapping("/actors")
    public List<Actor> listActors(@RequestParam(required = false) Map<String, String> params){
        return actorService.findActorLogic(params);
    }

    @GetMapping("/actors/{id}")
    public Actor getActorById(@PathVariable Short id){
        return actorService.getActorById(id);
    }

    @PostMapping("/actors")
    public Actor createActor(@Validated @RequestBody ActorInput data){
        return actorService.createActor(data);
    }

    @DeleteMapping("/actors/{id}")
    public void deleteActor(@PathVariable Short id){
        actorService.deleteActor(id);
    }

    @PatchMapping("/actors/{id}")
    public Actor patchActor(@PathVariable Short id, @Validated @RequestBody ActorInput data){
        return actorService.patchActor(id, data);
    }

    @GetMapping("/actors/{actorId}/films")
    public List<PartialFilm> getActorFilms(@PathVariable Short actorId){
        return actorService.getActorFilms(actorId);
    }

    @DeleteMapping("/actors/{actorId}/films/{filmId}")
    public void deleteActorFilm(@PathVariable Short actorId, @PathVariable Short filmId){
        actorService.deleteActorFilm(actorId, filmId);
    }
    @DeleteMapping("/actors/{actorId}/films")
    public void deleteActorFilm(@PathVariable Short actorId){
        actorService.clearActorFilms(actorId);
    }

    @PostMapping("/actors/{actorId}/films")
    public List<PartialFilm> createActorFilms(@PathVariable Short actorId,@RequestBody ActorFilmsInput data){
        return actorService.addActorFilms(actorId, data);
    }

    @PatchMapping("/actors/{actorId}/films")
    public List<PartialFilm> patchActorFilms(@PathVariable Short actorId,@RequestBody ActorFilmsInput data){
        return actorService.setActorFilms(actorId, data);
    }

}
