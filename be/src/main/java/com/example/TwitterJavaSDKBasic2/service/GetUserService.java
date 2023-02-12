package com.example.TwitterJavaSDKBasic2.service;

import com.twitter.clientlib.ApiException;
import com.twitter.clientlib.api.TwitterApi;
import com.twitter.clientlib.model.Get2UsersByUsernameUsernameResponse;
import com.twitter.clientlib.model.User;

import java.util.HashSet;
import java.util.Set;

public class GetUserService {
    public User getUser(TwitterApi apiInstance, String username) {
        Set<String> userFields = new HashSet<>();
        userFields.add("created_at");
        userFields.add("description");
        userFields.add("entities");
        userFields.add("location");
        userFields.add("pinned_tweet_id");
        userFields.add("profile_image_url");
        userFields.add("protected");
        userFields.add("public_metrics");
        userFields.add("url");
        userFields.add("verified");
        userFields.add("withheld");
        User user = null;
        // Set<String> | A comma separated list of User fields to display.
//        Set<String> expansions = new HashSet<>(Arrays.asList()); // Set<String> | A comma separated list of fields to expand.
//        Set<String> tweetFields = new HashSet<>(Arrays.asList()); // Set<String> | A comma separated list of Tweet fields to display.
        try {
            Get2UsersByUsernameUsernameResponse result = apiInstance.users().findUserByUsername(username)
                    .userFields(userFields)
                    .execute();
            user = result.getData();
        } catch (ApiException e) {
            System.err.println("Exception when calling UsersApi#findUsersByUsername");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
        return user;
    }
}
