package com.example.TwitterJavaSDKBasic2.service.bookmarks;

import com.example.TwitterJavaSDKBasic2.config.SQSConfiguration;
import com.twitter.clientlib.model.Get2UsersIdBookmarksResponse;
import com.twitter.clientlib.model.Tweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookmarksSQSSender {

    @Value("${cloud.aws.end-point.uri}")
    private String endpoint;

    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    public void sendMessage(List<Tweet> message) {
        Message payload = MessageBuilder.withPayload(message)
                .setHeader("sender", "Vaishanavi")
                .build();
        queueMessagingTemplate.send(endpoint, payload);
    }

}
