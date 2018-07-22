package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.util.Pair;
import android.util.Log;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;
import com.udacity.jokedisplaylibrary.AppConstants;
import com.udacity.jokedisplaylibrary.DisplayJokeActivity;

import java.io.IOException;

public class EndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {
    private MyApi myApiService = null;
    private Context context;
    private EndpointsAsyncTaskListener endpointsAsyncTaskListener;

    @Override
    protected String doInBackground(Pair<Context, String>... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
//                       .setRootUrl("http://192.168.0.105:8080/_ah/api/");
            // end options for devappserver

            myApiService = builder.build();

            Log.d("Async","-------Async api service created");
        }

        context = params[0].first;
        String name = params[0].second;

        try {
            return myApiService.tellMeAJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Intent showJokeIntent = new Intent(context, DisplayJokeActivity.class);
        showJokeIntent.putExtra(AppConstants.INTENT_JOKE_INFO, ""+result);
        context.startActivity(showJokeIntent);
        if(this.endpointsAsyncTaskListener != null)
            endpointsAsyncTaskListener.onComplete(result);
    }

    public EndpointsAsyncTask setListener(EndpointsAsyncTaskListener endpointsAsyncTaskListener){
     this.endpointsAsyncTaskListener = endpointsAsyncTaskListener;
     return this;
    }
    public static interface EndpointsAsyncTaskListener{
        public void onComplete(String result);
    }
}
