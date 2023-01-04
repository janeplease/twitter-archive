package com.example.TwitterJavaSDKBasic2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfig {

    @Value("${twitter.bearer_token}")
    private String bearerToken;

    @Value("${twitter.user_name}")
    private String username;

    @Bean
    public MyValues myValues() {
        MyValues myValues = new MyValues();
        myValues.setBearerToken(bearerToken);
        myValues.setUsername(username);
        return myValues;
    }
}