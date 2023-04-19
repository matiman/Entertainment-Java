package com.bill.entertainment.service;

import com.bill.entertainment.dao.ActorRepository;
import com.bill.entertainment.entity.Actor;
import com.bill.entertainment.exception.*;
import com.bill.entertainment.util.ErrorMessages;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ActorService extends CreativesServiceImpl<Actor, ActorRepository> {

    public Optional<Actor> findById(Long id){
        return creativesRepository.findById(id);
    }

    @Override
    public Actor create(Actor actor) throws CreativesValidationException  {
        if(!validate(actor))
            throw new CreativesValidationException(ErrorMessages.DUPLICATE_ACTOR);
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
        if (!actorExists(id)) {
            throw new CreativesNotFoundException(ErrorMessages.ACTOR_NOT_FOUND);
        }
        try {

            creativesRepository.delete(actorOpt.get());
        }catch (Exception e){
            throw new CreativesDeletionException(ErrorMessages.CAN_NOT_DELETE_ACTOR_IN_MOVIE);
        }
    }

    public boolean actorExists(Long actorId) {
        Optional<Actor> actorOpt = creativesRepository.findById(actorId);

        return actorOpt.isPresent();
    }

}
