package com.projeto.movieApi.dto;

import com.projeto.movieApi.model.WatchStatus;

public record UserMovieDTO(
        Long id,
        UserDTO user,
        MovieResponseDTO movie,
        WatchStatus status

) {
}
