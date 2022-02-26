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
    public ResponseEntity<Movie> getMovieById(@PathVariable("id") long id){
        Optional<Movie> movie = movieService.getOne(id);
        if (movie.isPresent()) {
            return new ResponseEntity<>(movie.get(), HttpStatus.OK);
        } else  {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        try {
            validation(movie, true);
            Movie _movie = movieService.addOne(movie);
            return new ResponseEntity<>(_movie, HttpStatus.CREATED);
        }catch (IllegalArgumentException | DuplicateTitleException ie) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Movie> updateMovie(@PathVariable("id") long id, @RequestBody Movie movie) throws DuplicateTitleException {
        try{
            validation(movie, false);
            if(movieService.existsById(id)){
                movieService.updateOne(id, movie);
                return new ResponseEntity<>(movie, HttpStatus.OK);
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
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<List<Movie>> findByLaunchDate(@RequestParam String launchDate){
        LocalDate lDate = LocalDate.parse(launchDate);
        List<Movie> lst = movieService.findByLaunchDate(lDate);
        if(lst.isEmpty())
            return new ResponseEntity<>(lst, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(lst, HttpStatus.OK);

    }

    private void validation(Movie m, boolean isCreate) throws DuplicateTitleException {
        List<Movie> lst = movieService.findByTitle(m.getTitle());
        if(m.getRevenue() < 0
            || m.getRank() < 0 || m.getRank() > 10
            || m.getLaunchDate().isAfter(LocalDate.now())){
            throw new IllegalArgumentException();
        }else if (isCreate && !lst.isEmpty()){
            throw new DuplicateTitleException
                    ("There is another Movie with Title: " + m.getTitle());
        }
    }
}
