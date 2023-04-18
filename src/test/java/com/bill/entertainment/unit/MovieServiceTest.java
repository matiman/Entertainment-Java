package com.bill.entertainment.unit;

import com.bill.entertainment.dao.MovieRepository;
import com.bill.entertainment.entity.Movie;
import com.bill.entertainment.exception.MediaValidationException;
import com.bill.entertainment.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDuplicateMovieCreation()  {

        Movie movie = new Movie();
        movie.setTitle("Existing movie");
        movie.setReleaseDate(LocalDate.now());

        Movie duplicateMovie = new Movie();
        duplicateMovie.setTitle(movie.getTitle());
        duplicateMovie.setReleaseDate(movie.getReleaseDate());

        when(movieRepository.findByTitleAndReleaseDate(movie.getTitle(), movie.getReleaseDate()))
                .thenReturn(Collections.singletonList(movie));

        assertThrows(MediaValidationException.class, () -> movieService.create(duplicateMovie),
                "Should throw MediaValidationException.");
    }
}

