package com.spring.boot.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.spring.boot.models.CatalogItem;
import com.spring.boot.models.Movie;
import com.spring.boot.models.UserCatalog;
import com.spring.boot.models.UserRating;

import org.springframework.beans.factory.annotation.Autowired;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/")
    public String welcomePage() {
        return "Welcome to Movie ratings app!! ";
    }

  @RequestMapping("/{userId}")
  @CircuitBreaker(name = "movies-catalog-service", fallbackMethod = "getCatalogFallBack")
  public UserCatalog getCatalog(@PathVariable("userId")  String userId) {
    UserRating userRating = restTemplate.getForObject("http://movie-ratings-service/ratings/users/" + userId,
        UserRating.class);
    List<CatalogItem> catalogItems = userRating.getRatings().stream().map(rating -> {
      Movie movie = restTemplate
          .getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
      return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
    }).collect(Collectors.toList());
    return new UserCatalog(catalogItems);
  }

    public UserCatalog getCatalogFallBack(Exception e) {
        return new UserCatalog(List.of((new CatalogItem("No movie", "", 0))));
    }
}
