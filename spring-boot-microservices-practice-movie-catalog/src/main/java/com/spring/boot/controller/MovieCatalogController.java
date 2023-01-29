package com.spring.boot.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.spring.boot.models.CatalogItem;
import com.spring.boot.models.Movie;
import com.spring.boot.models.UserCatalog;
import com.spring.boot.models.UserRating;

import org.springframework.beans.factory.annotation.Autowired;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/catalog")
@Slf4j
public class MovieCatalogController {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/")
    public String welcomePage() {
        return "Welcome to Movie ratings app!! ";
    }

    @RequestMapping("/{userId}")
    @CircuitBreaker(name = "movies-catalog-service", fallbackMethod = "getCatalogFallBack")
    public UserCatalog getCatalog(@PathVariable("userId") String userId) {
        UserRating userRating = restTemplate.getForObject("http://movie-ratings-service/ratings/users/" + userId,
                                                          UserRating.class);
        List<CatalogItem> catalogItems = userRating.getRatings().stream().map(rating -> {
            Movie movie = restTemplate
                .getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
            return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
        }).collect(Collectors.toList());
        return new UserCatalog(catalogItems);
    }

    @RequestMapping("/bulk")
    @Bulkhead(name = "movies-catalog-service", fallbackMethod = "bulkHeadFallBack")
    public ResponseEntity<String> bulkHeadDemo() throws InterruptedException {
        Thread.sleep(100);
        log.info("####### Response From bulk head #####");
        return new ResponseEntity<>("### Response body from Bulk Head ###", HttpStatus.OK);
    }

    public UserCatalog getCatalogFallBack(Exception e) {
        return new UserCatalog(List.of((new CatalogItem("No movie", "", 0))));
    }

    public ResponseEntity<String> bulkHeadFallBack(Exception e) {
        log.info("Bulkhead 'movies-catalog-service' is full and does not permit further calls");
        return new ResponseEntity<>("### Service cannot accept any more requests ###", HttpStatus.TOO_MANY_REQUESTS);
    }
}
