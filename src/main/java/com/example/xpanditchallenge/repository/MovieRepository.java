package com.example.xpanditchallenge.repository;

import com.example.xpanditchallenge.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MovieRepository extends JpaRepository<Movie, Long> {

}
