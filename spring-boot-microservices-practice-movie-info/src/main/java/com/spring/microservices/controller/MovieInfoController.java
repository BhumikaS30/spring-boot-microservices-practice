package com.spring.microservices.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.spring.microservices.models.Movie;
import com.spring.microservices.models.MovieSummary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/movies")
public class MovieInfoController {

    @Value("${api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/{movieId}")
    public Movie getMovieInfo(@PathVariable("movieId") String movieId) {
        MovieSummary movieSummary =
            restTemplate.getForObject("https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" + apiKey,
                                      MovieSummary.class);
        return new Movie(movieId, movieSummary.getTitle(), movieSummary.getOverview());
    }

    @GetMapping("/bulkDemo")
    public String bulkDemo(@RequestParam("task") Integer task) {
        ExecutorService executorService = Executors.newFixedThreadPool(task);
        try {
            List<TestCallable> tasks = new ArrayList<>();
            for (int i = 0; i < task; i++) {
                tasks.add(new TestCallable());
            }
            executorService.invokeAll(tasks);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
        return "Call Completed!!";
    }

}
