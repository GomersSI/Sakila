package com.example.sakila.services;

import com.example.sakila.entities.Actor;
import com.example.sakila.entities.Film;
import com.example.sakila.entities.PartialFilm;
import com.example.sakila.input.ActorFilmsInput;
import com.example.sakila.input.ActorInput;
import com.example.sakila.repositories.ActorRepository;
import com.example.sakila.repositories.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ActorService {
    @Autowired
    ActorRepository actorRepository;
    @Autowired
    FilmRepository filmRepository;

    public List<Actor> findAll(){
        return actorRepository.findAll();
    }

    public Actor getActorById(@PathVariable Short id){
        return actorRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("No such actor."));
    }

    public Actor createActor(@Validated @RequestBody ActorInput data){
        final var actor = new Actor();
        actor.setFirstName(data.getFirstName());
        actor.setLastName(data.getLastName());

        return actorRepository.save(actor);
    }

    public void deleteActor(@PathVariable Short id){
        actorRepository.deleteById(id);
    }

    public Actor patchActor(@PathVariable Short id, @Validated @RequestBody ActorInput data){
        final var actor = getActorById(id);
        if (data.getFirstName() != null){
            actor.setFirstName(data.getFirstName());
        }
        if (data.getLastName() != null){
            actor.setLastName(data.getLastName());
        }
        return actorRepository.save(actor);
    }

    public List<PartialFilm> getActorFilms(@PathVariable Short id){
        return getActorById(id).getFilms();
    }

    public void deleteActorFilm(@PathVariable Short actorId, @PathVariable Short filmId){
        Actor actor = getActorById(actorId);
        List<PartialFilm> films = actor.getFilms();
        for (int filmNumber = 0; filmNumber < films.size(); filmNumber++){
            if (films.get(filmNumber).getId() == filmId){
                films.remove(filmNumber);
                actor.setFilms(films);
                actorRepository.save(actor);
                return;
            }
        }
    }

    public void clearActorFilms(@PathVariable Short actorId){
        Actor actor = getActorById(actorId);
        actor.setFilms(new ArrayList<PartialFilm>());
        actorRepository.save(actor);
    }

    public List<PartialFilm> addActorFilms(@PathVariable Short actorId, @RequestBody ActorFilmsInput data){
        Actor actor = getActorById(actorId);
        List<PartialFilm> films = actor.getFilms();
        List<Short> filmsId = data.getFilms();
        for (Short id: filmsId){
            Film film = filmRepository.findById(id)
                    .orElseThrow(() -> new ResourceAccessException("No such actor."));
            PartialFilm partialFilm = new PartialFilm();
            partialFilm.setId(film.getId());
            partialFilm.setTitle(film.getTitle());
            //partialFilm.setLanguageId(film.getLanguageId());
            partialFilm.setDesc(film.getDesc());
            films.add(partialFilm);
        }
        actor.setFilms(films);
        actorRepository.save(actor);
        return films;
    }

    public List<PartialFilm> setActorFilms(@PathVariable Short actorId, @RequestBody ActorFilmsInput data){
        Actor actor = getActorById(actorId);
        List<PartialFilm> films = new ArrayList<PartialFilm>();
        List<Short> filmsId = data.getFilms();
        for (Short filmId: filmsId){
            Film film = filmRepository.findById(filmId)
                    .orElseThrow(() -> new ResourceAccessException("No such actor."));
            PartialFilm partialFilm = new PartialFilm();
            partialFilm.setId(film.getId());
            partialFilm.setTitle(film.getTitle());
            //partialFilm.setLanguageId(film.getLanguageId());
            partialFilm.setDesc(film.getDesc());
            films.add(partialFilm);
        }
        actor.setFilms(films);
        actorRepository.save(actor);
        return films;
    }

    public List<Actor> getActorByFirstName(String firstName) {
        return actorRepository.findByFirstName(firstName);
    }

    public List<Actor> findActorLogic(@RequestParam(required = false) Map<String, String> params){
        String firstName = params.get("firstName");
        String lastName = params.get("lastName");
        if (firstName != null && lastName != null){
            return getActorByFullName(firstName, lastName);
        }
        if (firstName != null){
            return getActorByFirstName(firstName);
        }
        if (lastName != null){
            return getActorByLastName(lastName);
        }
        return findAll();
    }

    private List<Actor> getActorByLastName(String lastName){
        return actorRepository.findByLastName(lastName);
    }

    private List<Actor> getActorByFullName(String firstName, String lastName){
        return actorRepository.findByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCase(firstName, lastName);
    }
}
