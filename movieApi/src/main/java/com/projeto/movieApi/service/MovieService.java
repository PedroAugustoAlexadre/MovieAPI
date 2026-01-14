package com.projeto.movieApi.service;

import com.projeto.movieApi.client.TmdbSearchClient;
import com.projeto.movieApi.dto.MovieResponseDTO;
import com.projeto.movieApi.dto.TmdbResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final TmdbSearchClient tmdbSearchClient;

    public MovieService(TmdbSearchClient tmdbSearchClient) {
        this.tmdbSearchClient = tmdbSearchClient;
    }

    @Value("${tmdb.api.token}")
    private String apiToken;

    public List<MovieResponseDTO> getMovies(String query, String language) {

        String bearerToken = "Bearer " + apiToken;

        TmdbResponseDTO response = tmdbSearchClient.getMovies(bearerToken, query, "pt-BR");

        return response.results();
    }

    public List<MovieResponseDTO> getMovie(Long id) {

        String bearerToken = "Bearer " + apiToken;

        TmdbResponseDTO response = tmdbSearchClient.getMovie(id, bearerToken);

        return  response.results();

    }
}
