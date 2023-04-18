package com.bill.entertainment.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "movies",
        uniqueConstraints = @UniqueConstraint(columnNames = {"title", "releaseDate"}))

public class Movie extends Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Nonnull
    private String title;

    @Column(nullable = false)
    @Nonnull
    private LocalDate releaseDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "movie_actor",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"movie_id", "actor_id"}),

            }

    )/*

    */
    private Set<Actor> actors = new HashSet<>();


}

