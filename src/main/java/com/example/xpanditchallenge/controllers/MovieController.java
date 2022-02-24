package com.example.xpanditchallenge.controllers;

import com.example.xpanditchallenge.api.MovieAPI;
import com.example.xpanditchallenge.exceptions.DuplicateTitleException;
import com.example.xpanditchallenge.models.Movie;
import com.example.xpanditchallenge.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
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
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) throws DuplicateTitleException {
        try {
            verifications(movie, true);
            Movie m = movieRepository
                    .save(new Movie(
                            movie.getTitle(),
                            movie.getLaunchDate(),
                            movie.getRank(),
                            movie.getRevenue()));
            return new ResponseEntity<>(m, HttpStatus.OK);
        }catch (IllegalArgumentException ie) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (DuplicateTitleException dt){
            throw dt;
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Movie> updateMovie(@PathVariable("id") long id, @RequestBody Movie movie) throws DuplicateTitleException {
        try{
            verifications(movie, false);
            Optional<Movie> m = movieRepository.findById(id);
            if (m.isPresent()){
                Movie _m = m.get();
                _m.setTitle(movie.getTitle());
                _m.setLaunchDate(movie.getLaunchDate());
                _m.setRank(movie.getRank());
                _m.setRevenue(movie.getRevenue());
                return new ResponseEntity<>(movieRepository.save(_m), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (IllegalArgumentException ie) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (DuplicateTitleException dt){
            throw dt;
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<HttpStatus> deleteMovie(@PathVariable("id") long id) {
        if (movieRepository.existsById(id)){
            movieRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<List<Movie>> findByLaunchDate(@RequestParam String launchDateString){
        LocalDate launchDate = LocalDate.parse(launchDateString);
        return new ResponseEntity<>(movieRepository.findByLaunchDate(launchDate), HttpStatus.OK);
    }

    private void verifications(Movie m, boolean isCreate) throws DuplicateTitleException {
        if(m.getRevenue() < 0
            || m.getRank() < 0 || m.getRank() > 10
            || m.getLaunchDate().isAfter(LocalDate.now())){
            throw new IllegalArgumentException();
        }else if (isCreate && !movieRepository.findByTitle(m.getTitle()).isEmpty()){
            throw new DuplicateTitleException
                    ("There is another Movie with Title: " + m.getTitle());
        }
    }
}
