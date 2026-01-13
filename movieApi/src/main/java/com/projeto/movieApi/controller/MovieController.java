package com.projeto.movieApi.controller;

import com.projeto.movieApi.dto.MovieResponseDTO;
import com.projeto.movieApi.dto.TmdbResponseDTO;
import com.projeto.movieApi.service.MovieService;
import feign.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }


    @GetMapping
    public ResponseEntity<List<MovieResponseDTO>> getMovies(@RequestParam("query") String query, @RequestParam("lang") String lang) {

        List<MovieResponseDTO> movies = movieService.getMovies(query, lang);

        return ResponseEntity.ok(movies);
    }

}
