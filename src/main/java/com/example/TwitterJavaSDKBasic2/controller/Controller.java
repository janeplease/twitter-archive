package com.example.TwitterJavaSDKBasic2.controller;

import com.example.TwitterJavaSDKBasic2.AppConfig;
import com.example.TwitterJavaSDKBasic2.GetLikes;
import com.example.TwitterJavaSDKBasic2.GetUser;
import com.example.TwitterJavaSDKBasic2.MyValues;
import com.example.TwitterJavaSDKBasic2.auth.OAuth20AppOnlyGetAccessToken;
import com.twitter.clientlib.TwitterCredentialsBearer;
import com.twitter.clientlib.api.TwitterApi;
import com.twitter.clientlib.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    //TODO: Write proper responses for get requests
    //TODO: Check if user is created before each get request for likedTweets, retweets, bookmarks etc
    //TODO: Add different errors for different things
    //TODO: Find a way to find out how many requests are left. Find a way to deal with the requests limit basically

    @Autowired
    AppConfig appConfig;

    private TwitterApi apiInstance;
    private User currentUser;

    @GetMapping("/createuser")
    public void createUser() {
        System.out.println("Creating new user");
        MyValues myValues = appConfig.myValues();
        OAuth20AppOnlyGetAccessToken oAuth20AppOnlyGetAccessToken = new OAuth20AppOnlyGetAccessToken();
        TwitterCredentialsBearer credentials = oAuth20AppOnlyGetAccessToken.getCredentials(myValues.getBearerToken());
        apiInstance = new TwitterApi(credentials);
        GetUser getUser = new GetUser();
        currentUser = getUser.getUser(apiInstance, myValues.getUsername());
        System.out.println("New user created");
    }

    @GetMapping("/likedtweets")
    public void getLikedTweets() {
        System.out.println("Getting all liked tweets");
        GetLikes currentUserLikes = new GetLikes();
        System.out.println(currentUser.getId());
        currentUserLikes.getLikesByUserId(apiInstance, currentUser.getId());

    }
}
