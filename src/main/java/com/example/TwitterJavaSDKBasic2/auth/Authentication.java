package com.example.TwitterJavaSDKBasic2.auth;


import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.pkce.PKCE;
import com.github.scribejava.core.pkce.PKCECodeChallengeMethod;
import com.twitter.clientlib.TwitterCredentialsBearer;
import com.twitter.clientlib.TwitterCredentialsOAuth2;
import com.twitter.clientlib.auth.TwitterOAuth20AppOnlyService;
import com.twitter.clientlib.auth.TwitterOAuth20Service;

import java.util.Scanner;

public class OAuth20AppOnlyGetAccessToken {


    public TwitterCredentialsBearer getCredentials(String bearerToken) {

        // Setting the bearer token into TwitterCredentials
        TwitterCredentialsBearer credentials = new TwitterCredentialsBearer(bearerToken);
        return credentials;
    }

    public static TwitterCredentialsBearer getCredentials(String twitterConsumerKey, String twitterConsumerKeySecret) {
        TwitterOAuth20AppOnlyService service = new TwitterOAuth20AppOnlyService(
                twitterConsumerKey, twitterConsumerKeySecret);

        OAuth2AccessToken accessToken = null;
        try {
            accessToken = service.getAccessTokenClientCredentialsGrant();

            System.out.println("Access token: " + accessToken.getAccessToken());
            System.out.println("Token type: " + accessToken.getTokenType());
        } catch (Exception e) {
            System.err.println("Error while getting the access token:\n " + e);
            e.printStackTrace();
        }

        TwitterCredentialsBearer credentials = new TwitterCredentialsBearer(accessToken.getAccessToken());
        return credentials;
    }

    public static OAuth2AccessToken getAccessToken(TwitterCredentialsOAuth2 credentials) {
        TwitterOAuth20Service service = new TwitterOAuth20Service(
                credentials.getTwitterOauth2ClientId(),
                credentials.getTwitterOAuth2ClientSecret(),
                "http://twitter.com",
                "offline.access tweet.read users.read");

        OAuth2AccessToken accessToken = null;
        try {
            final Scanner in = new Scanner(System.in, "UTF-8");
            System.out.println("Fetching the Authorization URL...");

            final String secretState = "state";
            PKCE pkce = new PKCE();
            pkce.setCodeChallenge("challenge");
            pkce.setCodeChallengeMethod(PKCECodeChallengeMethod.PLAIN);
            pkce.setCodeVerifier("challenge");
            String authorizationUrl = service.getAuthorizationUrl(pkce, secretState);

            System.out.println("Go to the Authorization URL and authorize your App:\n" +
                    authorizationUrl + "\nAfter that paste the authorization code here\n>>");
            final String code = in.nextLine();
            System.out.println("\nTrading the Authorization Code for an Access Token...");
            accessToken = service.getAccessToken(pkce, code);

            System.out.println("Access token: " + accessToken.getAccessToken());
            System.out.println("Refresh token: " + accessToken.getRefreshToken());
        } catch (Exception e) {
            System.err.println("Error while getting the access token:\n " + e);
            e.printStackTrace();
        }
        return accessToken;
    }

}
