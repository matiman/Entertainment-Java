package com.bill.entertainment.service;

import com.bill.entertainment.dao.MovieRepository;
import com.bill.entertainment.entity.Actor;
import com.bill.entertainment.entity.Media;
import com.bill.entertainment.entity.Movie;
import com.bill.entertainment.exception.CreativesNotFoundException;
import com.bill.entertainment.exception.MediaDeletionException;
import com.bill.entertainment.exception.MediaNotFoundException;
import com.bill.entertainment.exception.MediaValidationException;
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
            throw new CreativesNotFoundException(e.getMessage());
        }
    }

    @Override
    public Movie update(Long id, Movie updatedMovie) throws MediaNotFoundException, MediaValidationException, CreativesNotFoundException {
        Movie movie = mediaRepository.findById(id)
                .orElseThrow(() -> new MediaNotFoundException("Movie with id " + id + " not found"));

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
                .orElseThrow(() -> new MediaNotFoundException("Movie with id " + id + " not found"));

        try {
            mediaRepository.delete(movie);
        } catch (Exception e) {
            throw new MediaDeletionException("Failed to delete movie id: " + id, e);
        }
    }

    @Override
    public void validateMedia(Media media,boolean isNewMedia) throws MediaValidationException, CreativesNotFoundException {
        if (!(media instanceof Movie)) {
            throw new IllegalArgumentException("Media object is not an instance of Movie");
        }
        Movie movie = (Movie) media;

        if (isNewMedia && !mediaRepository.findByTitleAndReleaseDate(movie.getTitle(), movie.getReleaseDate()).isEmpty()) {
            throw new MediaValidationException("Movie with title " + movie.getTitle() + " and release date " + movie.getReleaseDate() + " already exists");
        }
        if (movie.getActors().isEmpty()) {
            throw new MediaValidationException("Movie must have at least an actor.");
        }

        for(Actor actor: movie.getActors()){
            if(actorService.findById(actor.getId()).isEmpty())
                throw new CreativesNotFoundException("At least one of the actors isn't present in our database");
        }
    }

}
