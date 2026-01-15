package com.projeto.movieApi.controller;

import com.projeto.movieApi.dto.UserMovieDTO;
import com.projeto.movieApi.model.UserMovie;
import com.projeto.movieApi.model.WatchStatus;
import com.projeto.movieApi.service.UserMovieService;
import org.apache.catalina.webresources.war.WarURLConnection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/my-list")
public class UserMovieController {

    private final UserMovieService userMovieService;

    public UserMovieController(UserMovieService userMovieService) {
        this.userMovieService = userMovieService;
    }

    @PostMapping
    public ResponseEntity<UserMovieDTO> addMovieList(
            @RequestParam Long userId,
            @RequestParam Long tmdbId,
            @RequestParam WatchStatus status
    ){
        UserMovieDTO userMovie = userMovieService.addMovieToList(userId, tmdbId, status);

        return ResponseEntity.status(HttpStatus.CREATED).body(userMovie);
    }
}
