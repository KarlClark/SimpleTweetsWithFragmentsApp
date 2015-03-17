package com.codepath.apps.mysimpletweets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.mysimpletweets.fragments.HomeTimelineFragment;
import com.codepath.apps.mysimpletweets.fragments.MentiionsTimelineFragment;
import com.codepath.apps.mysimpletweets.fragments.TweetsListFragment;
import com.codepath.apps.mysimpletweets.models.Tweet;

public class TimelineActivity extends ActionBarActivity implements TweetsListFragment.TweetsListFragmentCallback{

    HomeTimelineFragment homeTimelineFragment;
    MentiionsTimelineFragment mentiionsTimelineFragment;
    ViewPager vpPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("DEBUG", "TimelineActivity onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        vpPager = (ViewPager)findViewById(R.id.viewpager);
        vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));
        PagerSlidingTabStrip tabStrip =  (PagerSlidingTabStrip)findViewById(R.id.tabs);
        tabStrip.setViewPager(vpPager);
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

    public void onProfileView(MenuItem mi) {
        Intent i = new Intent(this, MyProfileActivity.class);
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("DEBUG", "TimelineActivity onActivityResult called");
       if (resultCode == RESULT_OK){
           homeTimelineFragment.repopulateTimeline();
           mentiionsTimelineFragment.repopulateTimeline();
           vpPager.setCurrentItem(0);
        }
    }

    public class TweetsPagerAdapter extends FragmentPagerAdapter{
        private String[] tabTitles = {"Home" , "Mentions"};

        public TweetsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0){
                return homeTimelineFragment = new HomeTimelineFragment();
            }else{
                if(position == 1) {
                    return mentiionsTimelineFragment = new MentiionsTimelineFragment();
                }else {
                    return null;
                }
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object){
            if (position == 0) {
                getSupportActionBar().setTitle(getResources().getString((R.string.title_home_timeline)));
            }else {
                if (position == 1){
                    getSupportActionBar().setTitle(getResources().getString((R.string.title_mentions_timeline)));
                }
            }
        }
    }

    @Override
    public void onTweetsReady(Tweet tweet) {

    }
}
