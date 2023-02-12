package com.example.TwitterJavaSDKBasic2.config;

import com.example.TwitterJavaSDKBasic2.auth.twitterKeys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfig {

    @Value("${twitter.bearer_token}")
    private String bearerToken;

    @Value("${twitter.user_name}")
    private String username;

    @Value("${twitter.api_key}")
    private String apiKey;

    @Value("${twitter.api_key_secret}")
    private String apiKeySecret;

    @Value("${twitter.client_id}")
    private String clientID;

    @Value("${twitter.client_secret}")
    private String clientSecret;

    @Bean
    public twitterKeys myValues() {
        twitterKeys myValues = new twitterKeys();
        myValues.setBearerToken(bearerToken);
        myValues.setUsername(username);
        myValues.setApiKey(apiKey);
        myValues.setApiKeySecret(apiKeySecret);
        myValues.setClientID(clientID);
        myValues.setClientSecret(clientSecret);
        return myValues;
    }
}