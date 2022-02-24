package com.example.xpanditchallenge.controllers;

import com.example.xpanditchallenge.api.MovieAPI;
import com.example.xpanditchallenge.exceptions.DuplicateTitleException;
import com.example.xpanditchallenge.models.Movie;
import com.example.xpanditchallenge.services.MovieService;
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
    MovieService movieService;

    @Override
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("It's Working", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Movie> getMovieById(@PathVariable("id") long id){
        Optional<Movie> movie = movieService.getOne(id);
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
            Movie _movie = movieService.addOne(movie);
            return new ResponseEntity<>(_movie, HttpStatus.OK);
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
            Optional<Movie> _m =  movieService.updateOne(id, movie);
            if(_m.isPresent()){
                return new ResponseEntity<>(_m.get(), HttpStatus.OK);
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
        if (movieService.deleteOne(id)){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<List<Movie>> findByLaunchDate(@RequestParam String launchDate){
        LocalDate lDate = LocalDate.parse(launchDate);
        List<Movie> lst = movieService.findByLaunchDate(lDate);
        return new ResponseEntity<>(lst, HttpStatus.OK);

    }

    private void verifications(Movie m, boolean isCreate) throws DuplicateTitleException {
        if(m.getRevenue() < 0
            || m.getRank() < 0 || m.getRank() > 10
            || m.getLaunchDate().isAfter(LocalDate.now())){
            throw new IllegalArgumentException();
        }else if (isCreate && !movieService.findByTitle(m).isEmpty()){
            throw new DuplicateTitleException
                    ("There is another Movie with Title: " + m.getTitle());
        }
    }
}
