package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ProgressBar pbLoading;
    private Button btnTellJoke;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        pbLoading = (ProgressBar) root.findViewById(R.id.pbLoading);

        btnTellJoke = (Button) root.findViewById(R.id.btnTellJoke);
        btnTellJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getJokeFromServer();
            }
        });
        return root;
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
        endpointsAsyncTask.execute(new Pair<Context, String>(getContext(), ""));
    }
}
