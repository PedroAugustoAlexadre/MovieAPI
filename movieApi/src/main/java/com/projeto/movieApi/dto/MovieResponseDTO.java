package com.projeto.movieApi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MovieResponseDTO(
        Long id,
        String title,

        @JsonProperty("original_title")
        String originalTitle,

        String overview,

        @JsonProperty("original_language")
        String originalLanguage,

        Double popularity,

        @JsonProperty("poster_path")
        String posterPath,

        @JsonProperty("backdrop_path")
        String backdropPath,

        @JsonProperty("release_date")
        String releaseDate,

        @JsonProperty("vote_average")
        Double voteAverage // Adicionei esse, pois é muito usado para mostrar a nota!
) {
}