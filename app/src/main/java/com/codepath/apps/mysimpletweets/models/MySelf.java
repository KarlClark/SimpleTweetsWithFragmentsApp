package com.codepath.apps.mysimpletweets.models;

import android.util.Log;

import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Karl on 3/4/2015.
 */
public class MySelf {
    private static TwitterClient client = TwitterApplication.getRestClient();
    public static String myName = null;
    public static long myUserId = 0;
    public static String myScreenName  = null;
    public static String myProfileImageUrl = null;
    public static String myTagline = null;
    public static int myFollowersCount = 0;
    public static int myFollowingsCount = 0;

    public static void populate(){

        client.getUserInfo(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                //Log.d("DEBUG", json.toString());
                try {
                    myName = json.getString("name");
                    myUserId = json.getLong("id");
                    myProfileImageUrl = json.getString("profile_image_url");
                    myTagline = json.getString("description");
                    myFollowersCount = json.getInt("followers_count");
                    myFollowingsCount = json.getInt("friends_count");
                    Log.i("DEBUG", "My name = " + MySelf.myName);
                    Log.i("DEBUG", "my id = " + MySelf.myUserId);

                    Log.i("DEBUG", "My url= " + MySelf.myProfileImageUrl);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", "MySelf onFailure 1 " + errorResponse.toString());
            }
        });

        client.getMySettings(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                Log.d("DEBUG", json.toString());
                try {
                    myScreenName = json.getString("screen_name");
                    Log.i("DEBUG", "My screen name= " + MySelf.myScreenName);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", "MySelf onFailure 2" + errorResponse.toString());
            }

        });


    }
}
