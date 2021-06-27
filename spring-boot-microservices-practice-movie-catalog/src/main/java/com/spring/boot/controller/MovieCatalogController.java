package com.spring.boot.controller;

import com.spring.boot.models.CatalogItem;
import com.spring.boot.models.Movie;
import com.spring.boot.models.Ratings;
import com.spring.boot.models.UserCatalog;
import com.spring.boot.models.UserRating;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
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
  public UserCatalog getCatalog(@PathVariable("userId")  String userId) {
    UserRating userRating = restTemplate.getForObject("http://movie-ratings-service/ratings/users/" + userId,
        UserRating.class);
    List<CatalogItem> catalogItems = userRating.getRatings().stream().map(rating -> {
      Movie movie = restTemplate
          .getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
      return new CatalogItem(movie.getName(), "Test", rating.getRating());
    }).collect(Collectors.toList());
    return new UserCatalog(catalogItems);
  }
}
