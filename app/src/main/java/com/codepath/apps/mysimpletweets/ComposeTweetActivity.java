package com.codepath.apps.mysimpletweets;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.models.MySelf;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;


public class ComposeTweetActivity extends ActionBarActivity {

    private static TwitterClient client = TwitterApplication.getRestClient();

    ImageView ivTweetProfilePic;
    TextView tvTweetName;
    TextView tvCharsRemaining;
    EditText etTweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_tweet);

        ivTweetProfilePic = (ImageView)findViewById(R.id.ivTweetProfilePic);
        tvTweetName = (TextView)findViewById(R.id.tvTweetName);
        tvCharsRemaining = (TextView)findViewById(R.id.tvCharsRemaining);
        etTweet = (EditText)findViewById(R.id.etTweet);

        Picasso.with(this).load(MySelf.myProfileImageUrl).into(ivTweetProfilePic);
        tvTweetName.setText("@" + MySelf.myScreenName);
        tvCharsRemaining.setText("140");

        etTweet.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s){
                Log.d("debug", "s length=" + s.length());
                tvCharsRemaining.setText("" + (140-s.length()));
            }

            @Override
            public void beforeTextChanged(CharSequence cs, int start, int count,int after){

            }

            @Override
            public void onTextChanged(CharSequence cs, int start, int count,int afte){

            }

        });

    }


    public void onTweet(View view) {
        String tweet = etTweet.getText().toString();
        if (! tweet.equals("")){
            client.postTweet(tweet , new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                   setResult(RESULT_OK);
                   finish();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.d("DEBUG", "ComposeTweetActivity onFailure " + errorResponse.toString());
                    setResult(RESULT_CANCELED);
                    finish();
                }
            });
        }
        else {
            finish();
        }
    }

    public void onCancel(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }
}
