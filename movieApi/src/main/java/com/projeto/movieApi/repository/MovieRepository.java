package com.projeto.movieApi.repository;

import com.projeto.movieApi.model.Movie;
import com.projeto.movieApi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

   Optional<Movie> findByTmdbId(Long tmdbId);

   Optional<Movie> findByTitle(String title);

    boolean existsByTmdbId(Long tmdbId);

}
