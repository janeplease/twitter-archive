package com.example.TwitterJavaSDKBasic2.controller;

import com.example.TwitterJavaSDKBasic2.auth.Authentication;
import com.example.TwitterJavaSDKBasic2.auth.twitterKeys;
import com.example.TwitterJavaSDKBasic2.config.AppConfig;
import com.example.TwitterJavaSDKBasic2.service.bookmarks.GetBookmarksService;
import com.example.TwitterJavaSDKBasic2.service.GetLikesService;
import com.example.TwitterJavaSDKBasic2.service.GetProfileService;
import com.example.TwitterJavaSDKBasic2.service.GetUserService;
import com.twitter.clientlib.TwitterCredentialsOAuth2;
import com.twitter.clientlib.api.TwitterApi;
import com.twitter.clientlib.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    //TODO: Write proper responses for get requests
    //TODO: Check if user is created before each get request for likedTweets, retweets, bookmarks etc
    //TODO: Add different errors for different things
    //TODO: Find a way to find out how many requests are left. Find a way to deal with the requests limit basically

    @Autowired
    AppConfig appConfig;

    @Autowired
    GetBookmarksService getBookmarksService;

    private TwitterApi apiInstance;
    private User currentUser;

    @GetMapping("/createuser")
    public void createUser() {
        System.out.println("Creating new user");
        twitterKeys myValues = appConfig.myValues();
        Authentication oAuth20AppOnlyGetAccessToken = new Authentication();
        System.out.println(myValues.getApiKey());
        System.out.println(myValues.getApiKeySecret());
        TwitterCredentialsOAuth2 credentials = oAuth20AppOnlyGetAccessToken.getCredentials(
                myValues.getClientID(),
                myValues.getClientSecret(),
                "",
                "");
        apiInstance = new TwitterApi(credentials);
        GetUserService getUser = new GetUserService();
        currentUser = getUser.getUser(apiInstance, myValues.getUsername());
        System.out.println("New user created");
    }

    @GetMapping("/likedtweets")
    public void getLikedTweets() {
        System.out.println("Getting all liked tweets");
        GetLikesService currentUserLikes = new GetLikesService();
        System.out.println(currentUser.getId());
        currentUserLikes.getLikesByUserId(apiInstance, currentUser.getId());

    }


    @GetMapping("/bookmarkedtweets")
    public void getBookmarkedTweets() {
        System.out.println("Getting all bookmarked tweets");
        System.out.println(currentUser.getId());
        getBookmarksService.getBookmarksByUserId(apiInstance, currentUser.getId());
    }

    @GetMapping("/profiletweets")
    public void getProfileTweets() {
        System.out.println("Getting all profile tweets");
        GetProfileService currentUserProfile = new GetProfileService();
        System.out.println(currentUser.getId());
        currentUserProfile.getTweetsByUserId(apiInstance, currentUser.getId());
    }


}
