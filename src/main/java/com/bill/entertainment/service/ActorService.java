package com.bill.entertainment.service;

import com.bill.entertainment.dao.ActorRepository;
import com.bill.entertainment.dao.MovieRepository;
import com.bill.entertainment.entity.Actor;
import com.bill.entertainment.entity.Movie;
import com.bill.entertainment.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActorService extends CreativesServiceImpl<Actor, ActorRepository> {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public Actor create(Actor actor) throws CreativesValidationException  {

        return repository.save(actor);
    }

    @Override
    public Actor update(Long id, Actor actor) throws   CreativesNotFoundException, CreativesValidationException {
        Actor existingActor = getById(id);
        existingActor.setName(actor.getName());

        return repository.save(existingActor);
    }

    @Override
    public void delete(Long id) throws CreativesNotFoundException,CreativesDeletionException {


        Optional<Actor> actorOpt = repository.findById(id);
        if (actorOpt.isEmpty()) {
            throw new CreativesNotFoundException("Actor with ID " + id + " not found");
        }

        Actor actor = actorOpt.get();

        List<Movie> movies = movieRepository.findMoviesByActorId(id);
        if (!movies.isEmpty()) {
            throw new CreativesDeletionException("Actor with ID " + id + " cannot be deleted because they are part of one more movies.");
        }

        repository.delete(actor);

    }
}
