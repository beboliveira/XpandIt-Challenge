package com.example.xpanditchallenge;

import com.example.xpanditchallenge.models.Movie;
import com.example.xpanditchallenge.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class XpandItChallengeApplicationTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MovieRepository movieRepository;

    @Test
    void getBook() {
    }

    @Test
    void getBook_NOTFOUND() {
    }

    @Test
    void createBook(){

    }

    @Test
    void createBook_BAD_RANK(){

    }

    @Test
    void createBook_BAD_REVENUE(){

    }

    @Test
    void createBook_BAD_LAUNCHDATE(){

    }

    @Test
    void createBook_DUPLICATE_TITLE(){

    }

}
