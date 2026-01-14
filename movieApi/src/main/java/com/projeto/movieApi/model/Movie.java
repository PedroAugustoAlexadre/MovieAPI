package com.projeto.movieApi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "movies")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "tmdb_id", unique = true, nullable = false)
    private Long tmdbId;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @Column(name = "original_title")
    private String originalTitle;

    @Column(columnDefinition = "TEXT")
    private String overview;

    @Column(columnDefinition = "TEXT")
    private String originalLanguage;

    @Column(name = "poster_path")
    private String posterPath;

    @Column(name = "backdrop_path")
    private String backdropPath;

    @Column(name = "release_date")
    private String releaseDate;

    private Double popularity;

    private Double voteAverage;

}