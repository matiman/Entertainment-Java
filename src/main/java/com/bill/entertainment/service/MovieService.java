package com.bill.entertainment.service;

import com.bill.entertainment.dao.ActorRepository;
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

@Service
public class MovieService extends MediaServiceImpl<Movie, MovieRepository> {

    @Autowired
    private ActorRepository actorRepository;

    @Override
    public Movie create(Movie movie) throws MediaValidationException, IllegalArgumentException {
        try {
            validateMedia(movie);
            return repository.save(movie);
        }
        catch (MediaValidationException e){
            throw new MediaValidationException(e.getMessage());
        }
        catch (IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }
        catch (CreativesNotFoundException e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public Movie update(Long id, Movie updatedMovie) throws MediaNotFoundException, MediaValidationException {
        Movie movie = repository.findById(id)
                .orElseThrow(() -> new MediaNotFoundException("Movie with id " + id + " not found"));

        movie.setTitle(updatedMovie.getTitle());
        movie.setReleaseDate(updatedMovie.getReleaseDate());
        movie.setActors(updatedMovie.getActors());

        return repository.save(movie);
    }

    @Override
    public void delete(Long id) throws MediaNotFoundException, MediaDeletionException {
        Movie movie = repository.findById(id)
                .orElseThrow(() -> new MediaNotFoundException("Movie with id " + id + " not found"));

        try {
            repository.delete(movie);
        } catch (Exception e) {
            throw new MediaDeletionException("Failed to delete movie id: " + id, e);
        }
    }

    @Override
    public void validateMedia(Media media) throws MediaValidationException, CreativesNotFoundException {
        if (!(media instanceof Movie)) {
            throw new IllegalArgumentException("Media object is not an instance of Movie");
        }
        Movie movie = (Movie) media;

        if (!repository.findByTitleAndReleaseDate(movie.getTitle(), movie.getReleaseDate()).isEmpty()) {
            throw new MediaValidationException("Movie with title " + movie.getTitle() + " and release date " + movie.getReleaseDate() + " already exists");
        }

        for(Actor actor: movie.getActors()){
            if(actorRepository.findById(actor.getId()).isEmpty())
                throw new CreativesNotFoundException("At least one of the actors isn't present in our database");
        }
    }
}
