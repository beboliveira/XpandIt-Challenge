package com.example.xpanditchallenge;

import com.example.xpanditchallenge.models.Movie;
import com.example.xpanditchallenge.services.MovieService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MovieService movieService;

    @Autowired
    private ObjectMapper mapper;


    @Test
    public void testGET_then200() throws Exception {
        Movie m = new Movie("AA", LocalDate.parse("1999-03-12"), 3, 673);

        Mockito.when(movieService.getOne(1)).thenReturn(Optional.of(m));

        MvcResult result = mvc.perform(get("/movies/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        assert(mapper.readValue(content, Movie.class).getTitle().equals(m.getTitle()));
    }

    @Test
    public void testGET_then404() throws Exception {
        Mockito.when(movieService.getOne(1)).thenReturn(Optional.empty());

        mvc.perform(get("/movies/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCREATE_then201() throws Exception {
        Movie m = new Movie(0,"BB", LocalDate.parse("1999-03-12"), 3.0f, 673);

        Mockito.when(movieService.addOne(any(Movie.class))).thenReturn(m);
        Mockito.when(movieService.findByTitle(m.getTitle())).thenReturn(new ArrayList<>());


        MvcResult result = mvc.perform(post("/movies")
                        .content(mapper.writeValueAsString(m))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assert(mapper.readValue(content, Movie.class).getTitle().equals(m.getTitle()));
    }

    @Test
    public void testCREATE_then400_BadRank() throws Exception {
        Movie m = new Movie("CC", LocalDate.parse("1999-03-12"), 11, 673);

        Mockito.when(movieService.addOne(any(Movie.class))).thenReturn(m);
        Mockito.when(movieService.findByTitle(m.getTitle())).thenReturn(new ArrayList<>());


        mvc.perform(post("/movies")
                        .content(mapper.writeValueAsString(m))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCREATE_then400_BadRevenue() throws Exception {
        Movie m = new Movie("DD", LocalDate.parse("1999-03-12"), 6, -673);

        Mockito.when(movieService.addOne(any(Movie.class))).thenReturn(m);
        Mockito.when(movieService.findByTitle(m.getTitle())).thenReturn(new ArrayList<>());


        mvc.perform(post("/movies")
                        .content(mapper.writeValueAsString(m))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCREATE_then400_BadDate() throws Exception {
        Movie m = new Movie("EE", LocalDate.parse("2027-03-12"), 5, 673);

        Mockito.when(movieService.addOne(any(Movie.class))).thenReturn(m);
        Mockito.when(movieService.findByTitle(m.getTitle())).thenReturn(new ArrayList<>());


        mvc.perform(post("/movies")
                        .content(mapper.writeValueAsString(m))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCREATE_then400_DuplicateTitle() throws Exception {
        Movie m = new Movie("DD", LocalDate.parse("1999-03-12"), 5, 673);

        List<Movie> lst = new ArrayList<>(){};
        lst.add(m);
        Mockito.when(movieService.findByTitle(m.getTitle())).thenReturn(lst);
        Mockito.when(movieService.addOne(any(Movie.class))).thenReturn(m);
        mvc.perform(post("/movies")
                        .content(mapper.writeValueAsString(m))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUPDATE_then200() throws Exception {
        Movie m = new Movie("FF", LocalDate.parse("1999-03-12"), 3, 673);

        Mockito.when(movieService.updateOne(anyLong(), any(Movie.class))).thenReturn(Optional.of(m));
        Mockito.when(movieService.findByTitle(m.getTitle())).thenReturn(new ArrayList<>());

        MvcResult result = mvc.perform(put("/movies/1")
                        .content(mapper.writeValueAsString(m))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assert(mapper.readValue(content, Movie.class).getTitle().equals(m.getTitle()));
    }

    @Test
    public void testUPDATE_then400_BadRank() throws Exception {
        Movie m = new Movie("FF", LocalDate.parse("1999-03-12"), 11, 673);

        Mockito.when(movieService.updateOne(anyLong(), any(Movie.class))).thenReturn(Optional.of(m));
        Mockito.when(movieService.findByTitle(m.getTitle())).thenReturn(new ArrayList<>());

        mvc.perform(put("/movies/1")
                        .content(mapper.writeValueAsString(m))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUPDATE_then400_BadRevenue() throws Exception {
        Movie m = new Movie("FF", LocalDate.parse("1999-03-12"), 7, -673);

        Mockito.when(movieService.updateOne(anyLong(), any(Movie.class))).thenReturn(Optional.of(m));
        Mockito.when(movieService.findByTitle(m.getTitle())).thenReturn(new ArrayList<>());

        mvc.perform(put("/movies/1")
                        .content(mapper.writeValueAsString(m))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUPDATE_then400_BadDate() throws Exception {
        Movie m = new Movie("FF", LocalDate.parse("2024-03-12"), 5, 673);

        Mockito.when(movieService.updateOne(anyLong(), any(Movie.class))).thenReturn(Optional.of(m));
        Mockito.when(movieService.findByTitle(m.getTitle())).thenReturn(new ArrayList<>());

        mvc.perform(put("/movies/1")
                        .content(mapper.writeValueAsString(m))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDELETE_then200() throws Exception {
        Mockito.when(movieService.deleteOne(anyLong())).thenReturn(true);

        mvc.perform(delete("/movies/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDELETE_then404() throws Exception {
        Mockito.when(movieService.deleteOne(anyLong())).thenReturn(false);

        mvc.perform(delete("/movies/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testfindByLaunchDate_then200() throws Exception {
        Movie m = new Movie("FF", LocalDate.parse("2006-03-12"), 7, 673);
        LocalDate lDate = LocalDate.parse("2006-03-12");
        List<Movie> lst = new LinkedList<>(){};
        lst.add(m);

        Mockito.when(movieService.findByLaunchDate(lDate)).thenReturn(lst);

         MvcResult result =  mvc.perform(get("/movies?launchDate=2006-03-12"))
                .andExpect(status().isOk())
                 .andReturn();

        String content = result.getResponse().getContentAsString();
        List<Movie> lstReturn = mapper.readValue(content, new TypeReference<>(){});
        assert(lstReturn.size() == lst.size());
        assert(lstReturn.get(0).getTitle().equals(m.getTitle()));
    }

    @Test
    public void testfindByLaunchDate_then404() throws Exception {
        LocalDate lDate = LocalDate.parse("2006-03-12");
        List<Movie> lst = new LinkedList<>(){};

        Mockito.when(movieService.findByLaunchDate(lDate)).thenReturn(lst);

        mvc.perform(get("/movies?launchDate=2006-03-12"))
                .andExpect(status().isNoContent());
    }
}
