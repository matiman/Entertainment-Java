package com.bill.entertainment.service;

import com.bill.entertainment.dao.ActorRepository;
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
    private MovieService movieService;

    public Optional<Actor> findById(Long id){
        return creativesRepository.findById(id);
    }

    @Override
    public Actor create(Actor actor) throws CreativesValidationException  {
        if(!validate(actor))
            throw new CreativesValidationException("Actor already exists");
        return creativesRepository.save(actor);
    }

    public boolean validate(Actor actor) {
        if(actor!=null && actor.getName()!=null){
            if(creativesRepository.findByName(actor.getName())==null){
                return true;
            }
        }
        return false;
    }

    @Override
    public Actor update(Long id, Actor actor) throws   CreativesNotFoundException, CreativesValidationException {
        Actor existingActor = getById(id);
        existingActor.setName(actor.getName());

        return creativesRepository.save(existingActor);
    }

    @Override
    public void delete(Long id) throws CreativesNotFoundException,CreativesDeletionException {


        Optional<Actor> actorOpt = creativesRepository.findById(id);
        if (actorOpt.isEmpty()) {
            throw new CreativesNotFoundException("Actor with ID " + id + " not found");
        }

        Actor actor = actorOpt.get();

        List<Movie> movies = movieService.getMoviesByActor(id);
        if (!movies.isEmpty()) {
            throw new CreativesDeletionException("Actor with ID " + id + " cannot be deleted because they are part of one more movies.");
        }

        creativesRepository.delete(actor);

    }

}
