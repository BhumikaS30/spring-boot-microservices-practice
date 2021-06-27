package com.spring.microServices.controller;

import com.spring.microServices.models.Ratings;
import com.spring.microServices.models.UserRating;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/ratings")
@RestController
public class RatingsController {

  @RequestMapping("/{movieId}")
  public Ratings getMovieInfo(@PathVariable("movieId") String movieId) {
    return new Ratings(movieId, 4);
  }
  @RequestMapping("users/{userId}")
  public UserRating getRatings(@PathVariable("userId") String userId) {
    List<Ratings> ratings = Arrays.asList(
        new Ratings("1234",3),
        new Ratings("5678",4)
    );
    UserRating userRating = new UserRating();
    userRating.setRatings(ratings);
    return userRating;
  }
}
