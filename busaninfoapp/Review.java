package com.example.busaninfoapp;

import android.widget.RatingBar;

import java.util.Map;

public class Review {
    public String reviewId;
    //String writerId;
    public String message;
    public String title;
    //public Map<String, String> writeTime;
    public long writeTime;
    public float rating;

    public String getReviewId() {
        return reviewId;
    }

    public String getMessage() {
        return message;
    }

    public float getRating() {
        return rating;
    }

    public long getWriteTime() {
        return writeTime;
    }
    /*
    public Map<String, String> getWriteTime() {
        return writeTime;
    } */
}
