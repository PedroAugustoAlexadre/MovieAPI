package com.projeto.movieApi.service;

import com.projeto.movieApi.model.Movie;
import com.projeto.movieApi.model.User;
import com.projeto.movieApi.model.UserMovie;
import com.projeto.movieApi.model.WatchStatus;
import com.projeto.movieApi.repository.MovieRepository;
import com.projeto.movieApi.repository.UserMovieRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import javax.print.DocFlavor;
import java.util.Optional;

@Service
public class UserMovieService {

    private final UserMovieRepository userMovieRepository;
    private final UserService userService;
    private final MovieService movieService;

    public UserMovieService(UserMovieRepository userMovieRepository, UserService userService, MovieService movieService) {
        this.userMovieRepository = userMovieRepository;
        this.userService = userService;
        this.movieService = movieService;
    }


    @Transactional
    public UserMovie addMovieToList(Long userId, Long tmdbId, WatchStatus status) {

        User user = userService.findById(userId);
        Movie movie = movieService.getOrCreateMovie(tmdbId);

        Optional<UserMovie> userMovieOptional =
                userMovieRepository.findByUserIdAndMovieId(userId, movie.getId());

        UserMovie userMovie;

        if (userMovieOptional.isPresent()) {
            userMovie = userMovieOptional.get();
            userMovie.setStatus(status);
        } else {
            userMovie = new UserMovie();
            userMovie.setUser(user);
            userMovie.setMovie(movie);
            userMovie.setStatus(status);
        }

        return userMovieRepository.save(userMovie);
    }

}
