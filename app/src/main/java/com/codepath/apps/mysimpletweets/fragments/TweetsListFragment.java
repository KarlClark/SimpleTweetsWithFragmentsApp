package com.codepath.apps.mysimpletweets.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TweetsArrayAdapter;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.Utility.EndlessScrollListener;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karl on 3/8/2015.
 */
public class TweetsListFragment extends Fragment{

    protected ArrayList<Tweet> tweets;
    protected TweetsArrayAdapter aTweets;
    protected ListView lvTweets;
    private TwitterClient client;
    protected String whichTimeline;
    protected String screenName=null;
    private int numberOfTweetsToDownload = 150;
    private TweetsListFragmentCallback tlfCallback;

    public interface TweetsListFragmentCallback{
        public void onTweetsReady(Tweet tweet);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        tlfCallback = (TweetsListFragmentCallback)activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragments_tweets_list, parent, false);
        lvTweets = (ListView)v.findViewById(R.id.lvTweets);
        lvTweets.setAdapter((aTweets));
        lvTweets.setOnScrollListener(new EndlessScrollListener(){
            @Override
            public void onLoadMore(int page, int totalItemsCount){
                Log.d("debug", "totalItemsCount= " + totalItemsCount + "  tweets length= " + tweets.size());
                if (totalItemsCount < numberOfTweetsToDownload){
                    populateTimeline(tweets.get(totalItemsCount-1).getIdStr() , true);
                }
            }
        });

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweets = new ArrayList<>();
        aTweets = new TweetsArrayAdapter(getActivity(), tweets);
        client = TwitterApplication.getRestClient();
        populateTimeline("1", false);
    }

    public void addAll(List<Tweet> tweets){
        aTweets.addAll(tweets);
    }

    private void populateTimeline(String id, boolean isMaxId){
        client.getTimeline(whichTimeline, screenName,id, isMaxId, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                //Log.d("DEBUG", json.toString());
                addAll(Tweet.fromJSONArray(json));
                tlfCallback.onTweetsReady(tweets.get(0));
                //Log.d("DEBUG", aTweets.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        });
    }

    public void repopulateTimeline(){
        aTweets.clear();
        populateTimeline("1", false);
    }
}
