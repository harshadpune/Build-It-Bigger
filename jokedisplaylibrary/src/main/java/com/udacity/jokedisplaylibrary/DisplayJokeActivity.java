package com.udacity.jokedisplaylibrary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class DisplayJokeActivity extends AppCompatActivity {

    private TextView tvJoke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_joke);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initComponent();
        tvJoke.setText(""+getIntent().getStringExtra(AppConstants.INTENT_JOKE_INFO));
    }

    private void initComponent() {
        tvJoke = (TextView) findViewById(R.id.tvJoke);
    }

}
