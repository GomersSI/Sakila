package com.example.sakila.services;

import com.example.sakila.entities.*;
import com.example.sakila.input.FilmActorsInput;
import com.example.sakila.input.FilmInput;
import com.example.sakila.repositories.ActorRepository;
import com.example.sakila.repositories.CategoryRepository;
import com.example.sakila.repositories.FilmRepository;
import com.example.sakila.repositories.LanguageRepository;
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
public class FilmService {
    @Autowired
    ActorRepository actorRepository;
    @Autowired
    FilmRepository filmRepository;
    @Autowired
    LanguageRepository languageRepository;
    @Autowired
    CategoryRepository categoryRepository;


    public List<Film> listFilms(@RequestParam(required = false) Map<String, String> params){
        String filmTitle = params.get("title");
        if (filmTitle != null){
            return getFilmByName(filmTitle);
        }
        return filmRepository.findAll();
    }

    private List<Film> getFilmByName(String filmTitle) {
        return filmRepository.findByTitleContainingIgnoreCase(filmTitle);
    }

    public Film getFilmById(@PathVariable Short id){
        return filmRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("No such film."));
    }

    public List<PartialActor> getFilmActorsById(@PathVariable Short id){
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("No such actor."));
        return film.getActors();
    }

    public Film createFilm (@Validated @RequestBody FilmInput data){
        final var film = new Film();
        film.setTitle(data.getTitle());
        Language language = languageRepository.findById(data.getLanguageId().shortValue())
                .orElseThrow(() -> new ResourceAccessException("No such language."));
        film.setLanguage(language);
        film.setDesc(data.getDesc());
        List<Short> categoriesId = data.getCategories();
        List<PartialCategory> categories = new ArrayList<PartialCategory>();
        for (short categoryId: categoriesId){
            if (categoryRepository.existsById(categoryId)){
                Category category = categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new ResourceAccessException("No such Category"));
                PartialCategory newCategory = new PartialCategory();
                newCategory.setId(category.getId());
                newCategory.setName(category.getName());
                categories.add(newCategory);
            }
        }
        film.setCategories(categories);

        return filmRepository.save(film);
    }

    public void deleteFilm(@PathVariable Short id){
        filmRepository.deleteById(id);
    }

    public Film patchFilm(@PathVariable Short id, @Validated @RequestBody FilmInput data){
        final var film = getFilmById(id);
        if (data.getTitle() != null){
            film.setTitle(data.getTitle());
        }
        if (data.getLanguageId() != null){
            Language language = languageRepository.findById(data.getLanguageId().shortValue())
                    .orElseThrow(() -> new ResourceAccessException("No such language."));
            film.setLanguage(language);
        }
        if (data.getDesc() != null){
            film.setDesc(data.getDesc());
        }
        if (data.getCategories() != null){
            List<Short> categoriesId = data.getCategories();
            List<PartialCategory> categories = new ArrayList<PartialCategory>();
            for (short categoryId: categoriesId){
                if (categoryRepository.existsById(categoryId)){
                    Category category = categoryRepository.findById(categoryId)
                            .orElseThrow(() -> new ResourceAccessException("No such Category"));
                    PartialCategory newCategory = new PartialCategory();
                    newCategory.setId(category.getId());
                    newCategory.setName(category.getName());
                    categories.add(newCategory);
                }
            }
            film.setCategories(categories);
        }
        return filmRepository.save(film);
    }

    public List<PartialActor> setFilmActorsById(@PathVariable Short id, @RequestBody FilmActorsInput data){
        Film film = getFilmById(id);
        List<Short> actorsId = data.getActorsId();
        List<PartialActor> actors = new ArrayList<PartialActor>();
        for (Short actorId: actorsId){
            Actor currentActor = actorRepository.getById(actorId);
            PartialActor newActor = new PartialActor();
            newActor.setId(currentActor.getId());
            newActor.setLastName(currentActor.getLastName());
            newActor.setFirstName(currentActor.getFirstName());
            actors.add(newActor);
        }
        film.setActors(actors);
        filmRepository.save(film);
        return actors;
    }

    public List<PartialActor> patchFilmActorsById(@PathVariable Short id, @RequestBody FilmActorsInput data){
        Film film = getFilmById(id);
        List<Short> actorsId = data.getActorsId();
        List <PartialActor> actors = film.getActors();
        for (Short actorId: actorsId) {
            for (PartialActor actor : actors) {
                if (actor.getId() == actorId){
                    break;
                }
            }
            Actor currentActor = actorRepository.getById(actorId);
            PartialActor newActor = new PartialActor();
            newActor.setId(currentActor.getId());
            newActor.setLastName(currentActor.getLastName());
            newActor.setFirstName(currentActor.getFirstName());
            actors.add(newActor);
        }
        film.setActors(actors);
        filmRepository.save(film);
        return actors;
    }

    public void clearFilmActors(@PathVariable Short id){
        Film film = getFilmById(id);
        film.setActors(new ArrayList<PartialActor>());
        filmRepository.save(film);
    }

    public List<PartialActor> deleteFilmActor(@PathVariable Short filmId, @PathVariable Short actorId){
        Film film = getFilmById(filmId);
        List<PartialActor> actors = film.getActors();
        for (int actorPos = 0; actorPos < actors.size(); actorPos++){
            if (actors.get(actorPos).getId() == actorId){
                actors.remove(actorPos);
            }
        }
        film.setActors(actors);
        filmRepository.save(film);
        return actors;
    }
}
