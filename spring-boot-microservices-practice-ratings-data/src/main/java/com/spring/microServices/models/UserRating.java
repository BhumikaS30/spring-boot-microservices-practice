package com.spring.microServices.models;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRating {

    private String userId;

    private List<Ratings> ratings;

    public void initData(String userId) {
        this.setUserId(userId);
        this.setRatings(Arrays.asList(
            new Ratings("550", 3),
            new Ratings("551", 4)
        ));
    }
}
