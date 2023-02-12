package com.example.TwitterJavaSDKBasic2.service.bookmarks;

import com.example.TwitterJavaSDKBasic2.model.BookmarkedTweet;
import com.example.TwitterJavaSDKBasic2.repository.BookmarksRepository;
import com.example.TwitterJavaSDKBasic2.utils.DirectoryPreparation;
import com.twitter.clientlib.ApiException;
import com.twitter.clientlib.api.TwitterApi;
import com.twitter.clientlib.model.Get2UsersIdBookmarksResponse;
import com.twitter.clientlib.model.Tweet;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


@Component
public class GetBookmarksService {

    private int numTotalBookmarks = 0;
    private HashSet<String> bookmarkedTweetsId;

//    @Autowired
    GetBookmarksService getBookmarksService;

    @Autowired
    BookmarksSQSSender bookmarksSQSSender;

    @Autowired
    BookmarksRepository bookmarksRepository;

    public HashSet<String> getTweetFields() {
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

    public HashSet<String> getExpansions() {
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

    public HashSet<String> getMediaFields() {
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

    public HashSet<String> getUserFields() {
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

    public HashSet<String> getPlaceFields() {
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

    public HashSet<String> getPollFields() {
        HashSet<String> pollFields = new HashSet<String>();
        pollFields.add("duration_minutes");
        pollFields.add("end_datetime");
        pollFields.add("id");
        pollFields.add("options");
        pollFields.add("voting_status");
        return pollFields;
    }

    public void writeDataInFile(Get2UsersIdBookmarksResponse response, String filename) {
        try {
            FileWriter fw = new FileWriter("D:\\Projects\\TwitterJavaSDKBasic2\\be\\data\\bookmarks\\" + filename);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(String.valueOf(response.getData()));
            bw.newLine();
            bw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Get2UsersIdBookmarksResponse processOneBatch(TwitterApi apiInstance, String userId, String paginationToken) {
        Integer maxResults = 100;
        int numberOfCalls = 0;
        Get2UsersIdBookmarksResponse result = null;
        AtomicInteger check = new AtomicInteger(0);
        do {
            try{
                result = apiInstance.bookmarks().getUsersIdBookmarks(userId)
                        .maxResults(maxResults)
                        .paginationToken(paginationToken)
                        .tweetFields(getBookmarksService.getTweetFields())
                        .expansions(getBookmarksService.getExpansions())
                        .mediaFields(getBookmarksService.getMediaFields())
                        .pollFields(getBookmarksService.getPollFields())
                        .userFields(getBookmarksService.getUserFields())
                        .placeFields(getBookmarksService.getPlaceFields())
                        .execute();

                if(result!=null && result.getData()!=null) {
                    numTotalBookmarks = numTotalBookmarks + result.getData().size();

                    List<Tweet> tweets = result.getData();
                    for(int i=0; i<tweets.size(); i++) {
                        Tweet tweet = tweets.get(i);

                        BookmarkedTweet bookmarkedTweet = new BookmarkedTweet();
                        bookmarkedTweet.setTweetId(tweet.getId());
                        if(tweet.getReferencedTweets()!=null)
                            bookmarkedTweet.setReplyTweetId(tweet.getReferencedTweets().get(0).toString());
                        else
                            bookmarkedTweet.setReplyTweetId(null);
                        bookmarksRepository.save(bookmarkedTweet);
                    }
                }


                getBookmarksService.writeDataInFile(result, "Bookmarked_Tweets_"+numTotalBookmarks + ".txt");
                System.out.println("Tweets liked till now is "+numTotalBookmarks);
                result.getData().forEach((tweet) -> {
                    if(!bookmarkedTweetsId.contains(tweet.getId())) {
                        bookmarkedTweetsId.add(tweet.getId());
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
                System.err.println("Exception when calling TweetsApi#bookmarked");
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

    public void getBookmarksByUserId(TwitterApi apiInstance, String userId) {
        getBookmarksService = new GetBookmarksService();

        DirectoryPreparation directoryPreparation = new DirectoryPreparation();
        directoryPreparation.clearDirectoryBookmarks();

        bookmarkedTweetsId = new HashSet<String>();

        Integer maxResults = 100; // Integer | The maximum number of results.

        Get2UsersIdBookmarksResponse result;
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
        System.out.println("Total number of tweets bookmarked is "+numTotalBookmarks);
    }
}
