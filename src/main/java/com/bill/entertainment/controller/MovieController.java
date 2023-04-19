package com.bill.entertainment.controller;

import com.bill.entertainment.entity.Movie;
import com.bill.entertainment.exception.CreativesNotFoundException;
import com.bill.entertainment.exception.MediaDeletionException;
import com.bill.entertainment.exception.MediaNotFoundException;
import com.bill.entertainment.exception.MediaValidationException;
import com.bill.entertainment.service.MovieService;
import com.bill.entertainment.util.ErrorMessages;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;


    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        try {
            List<Movie> movies = movieService.getAll();
            return ResponseEntity.ok(movies);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getMovieById(@PathVariable(value = "id") Long id) {
        try {
            Movie movie = movieService.getById(id);
            return ResponseEntity.ok(movie.toString());
        } catch (MediaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
     }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createMovie(@Valid @RequestBody Movie movie) {
        try {

            movieService.create(movie);
            return ResponseEntity.status(HttpStatus.CREATED).body(ErrorMessages.SUCCESS);
        } catch (  MediaValidationException | IllegalArgumentException e ) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(movie.toString());
        }

    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateMovie(@PathVariable Long id, @RequestBody Movie movie) {
        try {
            Movie updatedMovie = movieService.update(id, movie);
            return ResponseEntity.ok(updatedMovie.toString());
        } catch (MediaNotFoundException | MediaValidationException  e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (CreativesNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable Long id) {
        try {
            movieService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(ErrorMessages.SUCCESS);
        } catch (MediaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (MediaDeletionException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/actors/{id}")
    public ResponseEntity<String> deleteActor(@PathVariable Long id) {
        try {
            movieService.deleteActorNotInMovie(id);
            return ResponseEntity.ok().body(ErrorMessages.SUCCESS);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
