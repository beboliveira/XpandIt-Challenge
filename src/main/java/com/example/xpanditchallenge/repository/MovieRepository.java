package com.example.xpanditchallenge.repository;

import com.example.xpanditchallenge.models.Movie;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;


public interface MovieRepository extends CrudRepository<Movie, Long> {
    List<Movie> findByLaunchDate(LocalDate launchDate);
    List<Movie> findByTitle(String title);
}
