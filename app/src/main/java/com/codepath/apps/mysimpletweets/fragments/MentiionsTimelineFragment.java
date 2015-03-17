package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;

/**
 * Created by Karl on 3/8/2015.
 */
public class MentiionsTimelineFragment extends TweetsListFragment{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        whichTimeline = "mentions_timeline";
        super.onCreate(savedInstanceState);
    }
}
