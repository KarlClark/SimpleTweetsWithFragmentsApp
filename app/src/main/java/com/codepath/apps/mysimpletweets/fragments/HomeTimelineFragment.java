package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;

import com.codepath.apps.mysimpletweets.models.MySelf;

/**
 * Created by Karl on 3/8/2015.
 */
public class HomeTimelineFragment extends TweetsListFragment{



    @Override
    public void onCreate(Bundle savedInstanceState) {
        MySelf.populate();
        whichTimeline = "home_timeline";
        super.onCreate(savedInstanceState);
    }
}
