package com.example.TwitterJavaSDKBasic2.auth;


import com.twitter.clientlib.TwitterCredentialsBearer;

public class OAuth20AppOnlyGetAccessToken {


    public TwitterCredentialsBearer getCredentials(String bearerToken) {

        // Setting the bearer token into TwitterCredentials
        TwitterCredentialsBearer credentials = new TwitterCredentialsBearer(bearerToken);
        return credentials;
    }

}
