package com.codepath.apps.mysimpletweets;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.fragments.TweetsListFragment;
import com.codepath.apps.mysimpletweets.fragments.UserTimelineFragment;
import com.codepath.apps.mysimpletweets.models.MySelf;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.squareup.picasso.Picasso;

public class MyProfileActivity extends ActionBarActivity implements TweetsListFragment.TweetsListFragmentCallback{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setTitle("@" + MySelf.myScreenName);
        populateHeader();

        String screenName = getIntent().getStringExtra("screen_name");
        if(savedInstanceState == null) {
            UserTimelineFragment fragmaneUserTimeline = UserTimelineFragment.newInstance(screenName);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer, fragmaneUserTimeline);
            ft.commit();
        }
    }

    private void populateHeader() {
        TextView tvName = (TextView)findViewById(R.id.tv_Name);
        TextView tvTagline =(TextView)findViewById(R.id.tvTagline);
        TextView tvFollowers = (TextView)findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView)findViewById(R.id.tvFollowing);
        ImageView ivProfileImage = (ImageView)findViewById(R.id.ivProfileImage);
        tvName.setText(MySelf.myName);
        tvTagline.setText(MySelf.myTagline);
        tvFollowers.setText("" + MySelf.myFollowersCount + " Followers");
        tvFollowing.setText("" + MySelf.myFollowingsCount + " Following");
        Picasso.with(this).load(MySelf.myProfileImageUrl).into(ivProfileImage);
    }

    @Override
    public void onTweetsReady(Tweet tweet) {

    }
}
