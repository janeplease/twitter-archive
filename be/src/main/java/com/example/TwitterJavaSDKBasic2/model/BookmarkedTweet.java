package com.example.TwitterJavaSDKBasic2.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BookmarkedTweet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String tweetId;
    private String replyTweetId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTweetId() {
        return tweetId;
    }

    public void setTweetId(String tweetId) {
        this.tweetId = tweetId;
    }

    public String getReplyTweetId() {
        return replyTweetId;
    }

    public void setReplyTweetId(String replyTweetId) {
        this.replyTweetId = replyTweetId;
    }
}
