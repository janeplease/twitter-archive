import auth.OAuth20AppOnlyGetAccessToken;
import com.twitter.clientlib.ApiException;
import com.twitter.clientlib.TwitterCredentialsBearer;
import com.twitter.clientlib.api.TwitterApi;
import com.twitter.clientlib.model.Get2UsersByResponse;
import com.twitter.clientlib.model.Get2UsersByUsernameUsernameResponse;
import com.twitter.clientlib.model.User;

import java.util.*;

public class MainClass {
    public static void main(String args[]) {

        OAuth20AppOnlyGetAccessToken oAuth20AppOnlyGetAccessToken = new OAuth20AppOnlyGetAccessToken();

        TwitterCredentialsBearer credentials = oAuth20AppOnlyGetAccessToken.getCredentials();

        TwitterApi apiInstance = new TwitterApi(credentials);

        GetUser getUser = new GetUser();

        User currentUser = getUser.getUser(apiInstance, "luctusia");

        System.out.println(currentUser.getId());

        GetLikes currentUserLikes = new GetLikes();

        currentUserLikes.getLikesByUserId(apiInstance, currentUser.getId());


    }


}
