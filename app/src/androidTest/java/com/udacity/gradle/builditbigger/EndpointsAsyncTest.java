package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.v4.util.Pair;
import android.test.AndroidTestCase;
import android.text.TextUtils;

import junit.framework.Assert;

import java.util.concurrent.CountDownLatch;

public class EndpointsAsyncTest extends AndroidTestCase {

    private Context context;

    public void testEndpointsAsyncTask(){
        final CountDownLatch signal = new CountDownLatch(1);
        context = InstrumentationRegistry.getContext();
        EndpointsAsyncTask endpointsAsyncTest = new EndpointsAsyncTask();
        endpointsAsyncTest.setListener(new EndpointsAsyncTask.EndpointsAsyncTaskListener() {
            @Override
            public void onComplete(String result) {
                Assert.assertFalse(TextUtils.isEmpty(result));
                signal.countDown();
            }
        });
        endpointsAsyncTest.execute(new Pair<Context, String>(context, ""));
        try {
            signal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
