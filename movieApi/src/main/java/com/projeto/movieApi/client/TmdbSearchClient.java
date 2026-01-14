package com.projeto.movieApi.client;

import com.projeto.movieApi.dto.MovieResponseDTO;
import com.projeto.movieApi.dto.TmdbResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(
        name = "TmdbSearchClient",
        url =  "${tmdb.api.url}"
)
public interface TmdbSearchClient {

    @GetMapping("/search/movie")
    TmdbResponseDTO getMovies(
            @RequestHeader("Authorization") String BearerToken,
            @RequestParam("query") String query,
            @RequestParam("language") String language
    );

    @GetMapping("/movie/{id}")
    MovieResponseDTO getMovie(
            @RequestHeader("Authorization") String BearerToken,
            @PathVariable Long id
    );
}
