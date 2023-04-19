package com.bill.entertainment.service;

import com.bill.entertainment.dao.MovieRepository;
import com.bill.entertainment.entity.Actor;
import com.bill.entertainment.entity.Media;
import com.bill.entertainment.entity.Movie;
import com.bill.entertainment.exception.*;
import com.bill.entertainment.util.ErrorMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService extends MediaServiceImpl<Movie, MovieRepository> {

    @Autowired
    private ActorService actorService;

    public List<Movie> getMoviesByActor(Long actorId){
        return mediaRepository.findMoviesByActorId(actorId);
    }

    @Override
    public Movie create(Movie movie) throws MediaValidationException, IllegalArgumentException, CreativesNotFoundException {
        try {
            validateMedia(movie,true);
            return mediaRepository.save(movie);
        }
        catch (MediaValidationException e){
            throw new MediaValidationException(e.getMessage());
        }
        catch (IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }
        catch (CreativesNotFoundException e){
            throw new CreativesNotFoundException(ErrorMessages.ACTOR_NOT_FOUND);
        }
    }

    @Override
    public Movie update(Long id, Movie updatedMovie) throws MediaNotFoundException, MediaValidationException, CreativesNotFoundException {
        Movie movie = mediaRepository.findById(id)
                .orElseThrow(() -> new MediaNotFoundException(ErrorMessages.MOVIE_NOT_FOUND));

        movie.setTitle(updatedMovie.getTitle());
        movie.setReleaseDate(updatedMovie.getReleaseDate());
        movie.setActors(updatedMovie.getActors());

        for (Actor actor: updatedMovie.getActors()){
            actorService.validate(actor);
        }
        validateMedia(movie,false);
        return mediaRepository.save(movie);
    }

    @Override
    public void delete(Long id) throws MediaNotFoundException, MediaDeletionException {
        Movie movie = mediaRepository.findById(id)
                .orElseThrow(() -> new MediaNotFoundException(ErrorMessages.MOVIE_NOT_FOUND));

        try {
            mediaRepository.delete(movie);
        } catch (Exception e) {
            throw new MediaDeletionException(ErrorMessages.CAN_NOT_DELETE_MOVIE);
        }
    }

    @Override
    public void validateMedia(Media media,boolean isNewMedia) throws MediaValidationException, CreativesNotFoundException {
        if (!(media instanceof Movie)) {
            throw new IllegalArgumentException("Media object is not an instance of Movie");
        }
        Movie movie = (Movie) media;

        if (isNewMedia && !mediaRepository.findByTitleAndReleaseDate(movie.getTitle(), movie.getReleaseDate()).isEmpty()) {
            throw new MediaValidationException(ErrorMessages.DUPLICATE_MOVIE_EXISTS);
        }
        if (movie.getActors().isEmpty()) {
            throw new MediaValidationException(ErrorMessages.ONE_ACTOR_AT_LEAST);
        }

        for(Actor actor: movie.getActors()){
            if(actorService.findById(actor.getId()).isEmpty())
                throw new CreativesNotFoundException(ErrorMessages.ONE_OR_MORE_ACTOR_NOT_IN_DB);
        }
    }

    public void deleteActorNotInMovie(Long actorId) throws CreativesDeletionException, CreativesNotFoundException {
        if(!actorService.actorExists(actorId)){
            throw new CreativesNotFoundException(ErrorMessages.ACTOR_NOT_FOUND);
        }
        List<Movie> movies = getMoviesByActor(actorId);
        if (!movies.isEmpty()) {
            throw new CreativesDeletionException(ErrorMessages.CAN_NOT_DELETE_ACTOR_IN_MOVIE);
        }
        try {
            actorService.delete(actorId);
        }catch (Exception e){
            throw new CreativesDeletionException(ErrorMessages.CAN_NOT_DELETE_ACTOR);
        }
    }

}
