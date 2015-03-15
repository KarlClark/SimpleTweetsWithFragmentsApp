package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;

/**
 * Created by Karl on 3/8/2015.
 */
public class UserTimelineFragment extends TweetsListFragment{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        whichTimeline = "user_timeline";
        screenName = getArguments().getString("screen_name");
        super.onCreate(savedInstanceState);
    }

    // Creates a new fragment given an int and title
    // DemoFragment.newInstance(5, "Hello");
    public static UserTimelineFragment newInstance(String screenName) {
        UserTimelineFragment userFragment = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putString("screen_name", screenName);
        userFragment.setArguments(args);
        return userFragment;
    }
}
