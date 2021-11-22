package com.example.busaninfoapp;

import android.net.Uri;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Community {
    public String reviewId;
    //String writerId;
    public String message;
    //public Map<String, String> writeTime;
    public long writeTime;
    public String imageUri;
    public int heartCnt = 0;
    public int commentCnt = 0;
    public String postId;
    public Map<String, Boolean> hearts = new HashMap<>();

    public String getPostId() { return postId; }


    public String getReviewId() {
        return reviewId;
    }

    public String getMessage() {
        return message;
    }

    public long getWriteTime() {
        return writeTime;
    }

    public String getImageUri() { return imageUri; }

    public int getHeartCnt() { return heartCnt; }

    public int getCommentCnt() { return commentCnt; }

    public Map<String, Boolean> getHearts() { return hearts; }

    public void setHeartCnt(int heartCnt) {
        this.heartCnt = heartCnt;
    }
}
