package com.example.xpanditchallenge.controllers;

import com.example.xpanditchallenge.api.MovieAPI;
import com.example.xpanditchallenge.models.Movie;
import com.example.xpanditchallenge.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class MovieController implements MovieAPI {

    @Autowired
    MovieRepository movieRepository;

    @Override
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("It's Working", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Movie> getMovieById(@PathVariable("id") long id){
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isPresent()) {
            return new ResponseEntity<>(movie.get(), HttpStatus.OK);
        } else  {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<HttpStatus> createMovie(@RequestBody Movie movie) {
        try{
            Movie m = movieRepository
                    .save(new Movie(
                            movie.getTitle(),
                            movie.getLaunchDate(),
                            movie.getRank(),
                            movie.getRevenue()));
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<HttpStatus> updateMovie(long id, Movie movie) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteMovie(long id) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<List<Movie>> findByLaunchDate() {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
