package auth;

import com.twitter.clientlib.ApiException;
import com.twitter.clientlib.TwitterCredentialsBearer;
import com.twitter.clientlib.api.TwitterApi;
import com.twitter.clientlib.model.Get2TweetsIdResponse;
import com.twitter.clientlib.model.ResourceUnauthorizedProblem;

import java.util.HashSet;
import java.util.Set;

public class OAuth20AppOnlyGetAccessToken {

    public TwitterCredentialsBearer getCredentials() {

        // Setting the bearer token into TwitterCredentials
        TwitterCredentialsBearer credentials = new TwitterCredentialsBearer("AAAAAAAAAAAAAAAAAAAAAE%2F6jQEAAAAA%2FPc1REkGKtGPnyMygYMv1tnvS68%3D1StuxoZFpnhblu4ZHZYXmBKowtohQ41YRE3mE9kAUSZe39G8WA");
        return credentials;
    }

}
