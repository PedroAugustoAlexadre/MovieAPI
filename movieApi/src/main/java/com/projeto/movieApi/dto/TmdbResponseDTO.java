package com.projeto.movieApi.dto;

import java.util.List;

public record TmdbResponseDTO(
        Integer page,
        List<MovieResponseDTO> results,
        Integer total_pages,
        Integer total_results
) {}