package com.projeto.movieApi.service;

import com.projeto.movieApi.client.TmdbSearchClient;
import com.projeto.movieApi.dto.MovieResponseDTO;
import com.projeto.movieApi.dto.TmdbResponseDTO;
import com.projeto.movieApi.model.Movie;
import com.projeto.movieApi.repository.MovieRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final TmdbSearchClient tmdbSearchClient;
    private final MovieRepository movieRepository;

    public MovieService(TmdbSearchClient tmdbSearchClient, MovieRepository movieRepository) {
        this.tmdbSearchClient = tmdbSearchClient;
        this.movieRepository = movieRepository;
    }

    @Value("${tmdb.api.token}")
    private String apiToken;

    public List<MovieResponseDTO> getMovies(String query, String language) {

        String bearerToken = "Bearer " + apiToken;

        TmdbResponseDTO response = tmdbSearchClient.getMovies(bearerToken, query, language);

        return response.results();
    }

    public MovieResponseDTO getMovie(Long id) {

        String bearerToken = "Bearer " + apiToken;

        MovieResponseDTO response = tmdbSearchClient.getMovie(bearerToken, id);

        return  response;

    }

    @Transactional
    public Movie getOrCreateMovie(Long tmdbId) {
        return movieRepository.findByTmdbId(tmdbId)
                .orElseGet(() -> {
                    String bearerToken = "Bearer " + apiToken;
                    MovieResponseDTO response = tmdbSearchClient.getMovie(bearerToken, tmdbId);

                    Movie movie = new Movie();
                    movie.setTmdbId(response.id());
                    movie.setTitle(response.title());
                    movie.setOriginalTitle(response.originalTitle());
                    movie.setOverview(response.overview());
                    movie.setOriginalLanguage(response.originalLanguage());
                    movie.setPopularity(response.popularity());
                    movie.setPosterPath(response.posterPath());
                    movie.setBackdropPath(response.backdropPath());
                    movie.setReleaseDate(response.releaseDate());
                    movie.setVoteAverage(response.voteAverage());

                    return movieRepository.save(movie);
                });
    }

}
