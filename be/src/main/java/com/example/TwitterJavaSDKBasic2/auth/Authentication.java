package com.example.TwitterJavaSDKBasic2.auth;


import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.pkce.PKCE;
import com.github.scribejava.core.pkce.PKCECodeChallengeMethod;
import com.twitter.clientlib.TwitterCredentialsBearer;
import com.twitter.clientlib.TwitterCredentialsOAuth2;
import com.twitter.clientlib.auth.TwitterOAuth20Service;

import java.util.Scanner;

public class Authentication {


    public TwitterCredentialsBearer getOAuth20AppOnlyGetAccessToken(String bearerToken) {

        // Setting the bearer token into TwitterCredentials
        TwitterCredentialsBearer credentials = new TwitterCredentialsBearer(bearerToken);
        return credentials;
    }

    public static TwitterCredentialsOAuth2 getCredentials(String clientID, String clientSecret, String accessToken1, String refreshToken) {
        TwitterCredentialsOAuth2 credentials = new TwitterCredentialsOAuth2(clientID, clientSecret, accessToken1, refreshToken);

        OAuth2AccessToken accessToken = getAccessToken(credentials);
        if (accessToken == null) {
            return null;
        }

        // Setting the access & refresh tokens into TwitterCredentialsOAuth2
        credentials.setTwitterOauth2AccessToken(accessToken.getAccessToken());
        credentials.setTwitterOauth2RefreshToken(accessToken.getRefreshToken());
        return credentials;
    }

    public static OAuth2AccessToken getAccessToken(TwitterCredentialsOAuth2 credentials) {
        TwitterOAuth20Service service = new TwitterOAuth20Service(
                credentials.getTwitterOauth2ClientId(),
                credentials.getTwitterOAuth2ClientSecret(),
                "http://myapp.com/oauth/twitter",
                "offline.access tweet.read users.read bookmark.read tweet.write tweet.moderate.write follows.read" +
                        " follows.write space.read mute.read mute.write like.read like.write list.read list.write block.read" +
                        " block.write bookmark.write");

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
