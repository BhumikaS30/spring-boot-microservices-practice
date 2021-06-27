package com.spring.microServices.models;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRating {
  private List<Ratings> ratings;

}
