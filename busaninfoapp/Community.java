package com.example.busaninfoapp;

import android.net.Uri;

public class Community {
    public String reviewId;
    //String writerId;
    public String message;
    //public Map<String, String> writeTime;
    public long writeTime;
    public String imageUri;
    public int heartCnt = 0;
    public int commentCnt = 0;

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
}
