package com.projeto.movieApi.repository;

import com.projeto.movieApi.model.UserMovie;
import com.projeto.movieApi.model.WatchStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserMovieRepository extends JpaRepository<UserMovie, Long> {

    List<UserMovie> findByUserId(Long userId);

    List<UserMovie> findByUserIdAndStatus(Long userId, WatchStatus status);

    Optional<UserMovie> findByUserIdAndMovieId(Long userId, Long movieId);

    boolean existsByUserIdAndMovieId(Long userId, Long movieId);
}