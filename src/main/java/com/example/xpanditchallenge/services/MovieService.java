package com.example.xpanditchallenge.services;

import com.example.xpanditchallenge.models.Movie;
import com.example.xpanditchallenge.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    MovieRepository movieRepository;

    public Optional<Movie> getOne(long id){
        return movieRepository.findById(id);
    }

    public Movie addOne(Movie movie){
        return movieRepository.save(movie);
    }

    public Optional<Movie> updateOne(long id, Movie movie){
        Optional<Movie> res = Optional.empty();

        Optional<Movie> m = movieRepository.findById(id);
        if (m.isPresent()) {
            Movie _m = m.get();
            _m.setTitle(movie.getTitle());
            _m.setLaunchDate(movie.getLaunchDate());
            _m.setRank(movie.getRank());
            _m.setRevenue(movie.getRevenue());
            res = Optional.of(movieRepository.save(_m));
        }

        return res;
    }

    public boolean deleteOne(long id){
        if(!movieRepository.existsById(id))
            return false;

        movieRepository.deleteById(id);

        return true;
    }

    public List<Movie> findByLaunchDate(LocalDate launchDate){
        return movieRepository.findByLaunchDate(launchDate);
    }

    public List<Movie> findByTitle(Movie movie){
        return movieRepository.findByTitle(movie.getTitle());
    }
}
