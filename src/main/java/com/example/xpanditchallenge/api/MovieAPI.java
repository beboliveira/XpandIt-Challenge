package com.example.xpanditchallenge.api;

import com.example.xpanditchallenge.exceptions.DuplicateTitleException;
import com.example.xpanditchallenge.models.Movie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/movies")
public interface MovieAPI {

    @GetMapping("/{id}")
    ResponseEntity<Movie> getMovieById(@PathVariable("id") long id);

    @PostMapping
    ResponseEntity<Movie> createMovie(@RequestBody Movie movie) throws DuplicateTitleException;

    @PutMapping("/{id}")
    ResponseEntity<Movie> updateMovie(@PathVariable("id") long id, @RequestBody Movie movie) throws DuplicateTitleException;

    @DeleteMapping("/{id}")
    ResponseEntity<HttpStatus> deleteMovie(@PathVariable("id") long id);

    @GetMapping
    ResponseEntity<List<Movie>> findByLaunchDate(@RequestParam String launchDate);

}
