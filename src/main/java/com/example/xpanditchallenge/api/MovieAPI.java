package com.example.xpanditchallenge.api;

import com.example.xpanditchallenge.models.Movie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/movies")
public interface MovieAPI {

    @GetMapping("/test")
    ResponseEntity<String> test();

    @GetMapping("/{id}")
    ResponseEntity<Movie> getMovieById(@PathVariable("id") long id);

    @PostMapping
    ResponseEntity<HttpStatus> createMovie(@RequestBody Movie movie);

    @PutMapping("/{id}")
    ResponseEntity<HttpStatus> updateMovie(@PathVariable("id") long id, @RequestBody Movie movie);

    @DeleteMapping("/{id}")
    ResponseEntity<HttpStatus> deleteMovie(@PathVariable("id") long id);

    @GetMapping
    ResponseEntity<List<Movie>> findByLaunchDate();

}