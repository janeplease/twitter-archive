package com.example.TwitterJavaSDKBasic2.service;

import com.example.TwitterJavaSDKBasic2.utils.DirectoryPreparation;
import com.twitter.clientlib.ApiException;
import com.twitter.clientlib.api.TwitterApi;
import com.twitter.clientlib.model.Get2UsersIdTweetsResponse;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class GetProfileService {

    private static int numTotalTweets = 0;

    static HashSet<String> tweetsId;

    public static HashSet<String> getTweetFields() {
        HashSet<String> tweetFields = new HashSet<String>();
        tweetFields.add("attachments");
        tweetFields.add("author_id");
        tweetFields.add("conversation_id");
        tweetFields.add("created_at");
        tweetFields.add("edit_controls");
        tweetFields.add("edit_history_tweet_ids");
        tweetFields.add("entities");
        tweetFields.add("geo");
        tweetFields.add("id");
        tweetFields.add("in_reply_to_user_id");
        tweetFields.add("lang");
        tweetFields.add("possibly_sensitive");
        tweetFields.add("public_metrics");
        tweetFields.add("referenced_tweets");
        tweetFields.add("reply_settings");
        tweetFields.add("source");
        tweetFields.add("text");
        return tweetFields;
    }

    public static HashSet<String> getExpansions() {
        HashSet<String> expansions = new HashSet<String>();
        expansions.add("attachments.media_keys");
        expansions.add("attachments.poll_ids");
        expansions.add("author_id");
        expansions.add("edit_history_tweet_ids");
        expansions.add("entities.mentions.username");
        expansions.add("geo.place_id");
        expansions.add("in_reply_to_user_id");
        expansions.add("referenced_tweets.id");
        expansions.add("referenced_tweets.id.author_id");
        return expansions;
    }

    public static HashSet<String> getMediaFields() {
        HashSet<String> mediaFields = new HashSet<String>();
        mediaFields.add("alt_text");
        mediaFields.add("duration_ms");
        mediaFields.add("height");
        mediaFields.add("media_key");
        mediaFields.add("preview_image_url");
        mediaFields.add("public_metrics");
        mediaFields.add("type");
        mediaFields.add("url");
        mediaFields.add("variants");
        mediaFields.add("width");
        return mediaFields;
    }

    public static HashSet<String> getUserFields() {
        HashSet<String> userFields = new HashSet<String>();
        userFields.add("created_at");
        userFields.add("description");
        userFields.add("entities");
        userFields.add("id");
        userFields.add("location");
        userFields.add("name");
        userFields.add("pinned_tweet_id");
        userFields.add("profile_image_url");
        userFields.add("protected");
        userFields.add("public_metrics");
        userFields.add("url");
        userFields.add("username");
        userFields.add("verified");
        userFields.add("withheld");
        return userFields;
    }

    public static HashSet<String> getPlaceFields() {
        HashSet<String> placeFields = new HashSet<String>();
        placeFields.add("contained_within");
        placeFields.add("country");
        placeFields.add("country_code");
        placeFields.add("full_name");
        placeFields.add("geo");
        placeFields.add("id");
        placeFields.add("name");
        placeFields.add("place_type");
        return placeFields;
    }

    public static HashSet<String> getPollFields() {
        HashSet<String> pollFields = new HashSet<String>();
        pollFields.add("duration_minutes");
        pollFields.add("end_datetime");
        pollFields.add("id");
        pollFields.add("options");
        pollFields.add("voting_status");
        return pollFields;
    }

    public static void writeDataInFile(Get2UsersIdTweetsResponse response, String filename) {
        try {
            FileWriter fw = new FileWriter("D:\\Projects\\TwitterJavaSDKBasic2\\be\\data\\ProfileTweets\\" + filename, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(String.valueOf(response.getData()));
            bw.newLine();
            bw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Get2UsersIdTweetsResponse processOneBatch(TwitterApi apiInstance, String userId, String paginationToken) {
        Integer maxResults = 100;
        int numberOfCalls = 0;
        Get2UsersIdTweetsResponse result = null;
        AtomicInteger check = new AtomicInteger(0);
        do {
            try{
                numberOfCalls++;
                result = apiInstance.tweets().usersIdTweets(userId)
                        .maxResults(maxResults)
                        .paginationToken(paginationToken)
                        .tweetFields(GetProfileService.getTweetFields())
                        .expansions(GetProfileService.getExpansions())
                        .mediaFields(GetProfileService.getMediaFields())
                        .pollFields(GetProfileService.getPollFields())
//                        .userFields(GetProfile.getUserFields())
                        .placeFields(GetProfileService.getPlaceFields())
                        .execute();
                if(result!=null &&
                        result.getData()!=null)
                    numTotalTweets = numTotalTweets + result.getData().size();
                GetProfileService.writeDataInFile(result, "ProfileTweets"+numTotalTweets + ".txt");
                System.out.println("Tweets received till now is "+numTotalTweets);
                result.getData().forEach((tweet) -> {
                    if(!tweetsId.contains(tweet.getId())) {
                        tweetsId.add(tweet.getId());
                    }
                    else {
                        System.out.println("Duplicate "+tweet.getId());
                        check.set(1);
                    }
                });
//                if(check.get() ==1)
//                    break;
                paginationToken = result.getMeta().getNextToken();
            }
            catch (ApiException e) {
                System.err.println("Exception when calling TweetsApi#usersIdTweets");
                System.err.println("Status code: " + e.getCode());
                System.err.println("Reason: " + e.getResponseBody());
                System.err.println("Response headers: " + e.getResponseHeaders());
                e.printStackTrace();
            }
            catch (Exception e) {
                paginationToken = result.getMeta().getNextToken();
                e.printStackTrace();
            }
        }
        while(result.getMeta().getNextToken()!=null && numberOfCalls<75);

        return result;
    }

    public void getTweetsByUserId(TwitterApi apiInstance, String userId) {

        DirectoryPreparation directoryPreparation = new DirectoryPreparation();
        directoryPreparation.clearDirectoryTweets();

        tweetsId = new HashSet<String>();

        Integer maxResults = 100;

        Get2UsersIdTweetsResponse result;
        String paginationToken = null;
        do {
            result = processOneBatch(apiInstance, userId, paginationToken);
            paginationToken = result.getMeta().getNextToken();
            if(paginationToken==null)
                continue;
            try {
                System.out.println("Waiting for next batch");
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                System.out.println(dtf.format(now));

                TimeUnit.MINUTES.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while(result.getMeta().getNextToken()!=null);
        System.out.println("Total number of tweets received is "+numTotalTweets);
    }
}
