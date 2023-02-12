package com.example.TwitterJavaSDKBasic2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class TwitterJavaSDKBasic2 {



    public static void main(String[] args) {

        SpringApplication.run(TwitterJavaSDKBasic2.class, args);

    }

}
