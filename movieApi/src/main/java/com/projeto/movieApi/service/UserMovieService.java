package com.projeto.movieApi.service;

import com.projeto.movieApi.dto.MovieResponseDTO;
import com.projeto.movieApi.dto.UserDTO;
import com.projeto.movieApi.dto.UserMovieDTO;
import com.projeto.movieApi.model.Movie;
import com.projeto.movieApi.model.User;
import com.projeto.movieApi.model.UserMovie;
import com.projeto.movieApi.model.WatchStatus;
import com.projeto.movieApi.repository.UserMovieRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public UserMovieDTO addMovieToList(Long userId, Long tmdbId, WatchStatus status) {

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

        userMovieRepository.save(userMovie);

        UserDTO userDTO = new UserDTO(user.getUsername(), user.getEmail(), user.getPassword());

        MovieResponseDTO movieResponseDTO = new MovieResponseDTO(
                movie.getId(),
                movie.getTitle(),
                movie.getOriginalTitle(),
                movie.getOverview(),
                movie.getOriginalLanguage(),
                movie.getPopularity(),
                movie.getPosterPath(),
                movie.getBackdropPath(),
                movie.getReleaseDate(),
                movie.getVoteAverage()
                );

        UserMovieDTO userMovieDTO = new UserMovieDTO(userMovie.getId(), userDTO, movieResponseDTO, status);

        return userMovieDTO;
    }

    public List<UserMovieDTO> getMoviesList(Long userId) {
        List<UserMovie> userMovies = userMovieRepository.findByUserId(userId);

        List<UserMovieDTO> userMovieDTOList = userMovies.stream().map(
                userMovie -> {

                    User user = userMovie.getUser();
                    Movie movie = userMovie.getMovie();



                    UserDTO userDTO = new UserDTO(
                            user.getUsername(),
                            user.getEmail(),
                            user.getPassword());

                    MovieResponseDTO movieResponseDTO = new MovieResponseDTO(
                            movie.getId(),
                            movie.getTitle(),
                            movie.getOriginalTitle(),
                            movie.getOverview(),
                            movie.getOriginalLanguage(),
                            movie.getPopularity(),
                            movie.getPosterPath(),
                            movie.getBackdropPath(),
                            movie.getReleaseDate(),
                            movie.getVoteAverage()
                    );

                    UserMovieDTO userMovieDTO = new UserMovieDTO(userMovie.getId(), userDTO, movieResponseDTO, userMovie.getStatus());

                    return  userMovieDTO;
                }


        ).toList();

        return userMovieDTOList;
    }

    @Transactional
    public void  deleteMovieMyList(Long userMovieId) {
        if (!userMovieRepository.existsById(userMovieId)) {
            throw new RuntimeException("Item not found in your list!");
        }

        userMovieRepository.deleteById(userMovieId);

    }

}
