package com.codepath.apps.mysimpletweets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.codepath.apps.mysimpletweets.Utility.EndlessScrollListener;
import com.codepath.apps.mysimpletweets.models.MySelf;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TimelineActivity extends ActionBarActivity {

    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter aTweets;
    private ListView lvTweets;
    private final int numberOfTweetsToDownload = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("DEBUG", "TimelineActivity onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        lvTweets = (ListView)findViewById(R.id.lvTweets);
        tweets = new ArrayList<>();
        aTweets = new TweetsArrayAdapter(this, tweets);
        lvTweets.setAdapter((aTweets));

        lvTweets.setOnScrollListener(new EndlessScrollListener(){
            @Override
            public void onLoadMore(int page, int totalItemsCount){
                Log.d("debug","totalItemsCount= " + totalItemsCount +"  tweets length= " + tweets.size());
                if (totalItemsCount < numberOfTweetsToDownload){
                    populateTimeline(tweets.get(totalItemsCount-1).getIdStr() , true);
                }
            }
        });

        client = TwitterApplication.getRestClient();
        MySelf.populate();
        populateTimeline("1", false);
    }

    private void populateTimeline(String id, boolean isMaxId){
        client.getHomeTimeline(id, isMaxId, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                //Log.d("DEBUG", json.toString());
                aTweets.addAll(Tweet.fromJSONArray(json));
                //Log.d("DEBUG", aTweets.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Intent i = new Intent(this, ComposeTweetActivity.class);
        startActivityForResult(i, 0);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("DEBUG", "TimelineActivity onActivityResult called");
       if (resultCode == RESULT_OK){
            aTweets.clear();
            populateTimeline("1", false);
        }
    }
}
