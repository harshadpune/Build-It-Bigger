package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivity extends AppCompatActivity {

    private ProgressBar pbLoading;
    private InterstitialAd interstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    private void initComponents() {
        pbLoading = (ProgressBar) findViewById(R.id.pbLoading);
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(AppConfig.AD_TEST_KEY);       //test key for Ad
        interstitialAd.loadAd(new AdRequest.Builder().build());
        interstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                getJokeFromServer();
            }
        });
    }


    public void tellJoke(View view) {
        if(getResources().getString(R.string.flavor).equalsIgnoreCase(AppConfig.FREE)) {
            interstitialAd.show();
        }else if(getResources().getString(R.string.flavor).equalsIgnoreCase(AppConfig.PAID)){
            getJokeFromServer();
        }
    }

    public void getJokeFromServer(){
        EndpointsAsyncTask endpointsAsyncTask = new EndpointsAsyncTask();
        pbLoading.setVisibility(View.VISIBLE);
        endpointsAsyncTask.setListener(new EndpointsAsyncTask.EndpointsAsyncTaskListener() {
            @Override
            public void onComplete(String result) {
                pbLoading.setVisibility(View.GONE);
            }
        });
        endpointsAsyncTask.execute(new Pair<Context, String>(MainActivity.this, ""));
    }

}
