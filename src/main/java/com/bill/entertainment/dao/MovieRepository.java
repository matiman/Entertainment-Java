package com.bill.entertainment.dao;

import com.bill.entertainment.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("SELECT m FROM Movie m JOIN m.actors a WHERE a.id = :actorId")
    List<Movie> findMoviesByActorId(@Param("actorId") Long actorId);

    @Query("SELECT m FROM Movie m WHERE m.title = :title AND m.releaseDate = :releaseDate")
    List<Movie> findByTitleAndReleaseDate(@Param("title") String title, @Param("releaseDate") LocalDate releaseDate);

}
