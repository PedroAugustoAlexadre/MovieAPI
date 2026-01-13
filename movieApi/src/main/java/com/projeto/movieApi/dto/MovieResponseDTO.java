package com.projeto.movieApi.dto;

public record MovieResponseDTO(
        Long idTmdb,
        String title,
        String original_title,
        String original_language,
        Double popularity
) {
}
