package com.example.TwitterJavaSDKBasic2.service.bookmarks;

import com.twitter.clientlib.model.Tweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookmarksSQSReceiver {

    int count = 0;

    @Value("${cloud.aws.end-point.uri}")
    private String endpoint;

    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    @SqsListener(value = "Demo2Queue")
    public void receiveMessage(String tweetsList) {
        count++;
        System.out.println(tweetsList.substring(0,100));
        System.out.println("Messages received from queue: "+count + "\t\t");
    }
}
