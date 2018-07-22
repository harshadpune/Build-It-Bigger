package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;
import android.text.TextUtils;

import junit.framework.Assert;

public class EndpointsAsyncTest extends AndroidTestCase {
    public void testEndpointsAsyncTask(){
        EndpointsAsyncTask endpointsAsyncTest = new EndpointsAsyncTask();
        endpointsAsyncTest.setListener(new EndpointsAsyncTask.EndpointsAsyncTaskListener() {
            @Override
            public void onComplete(String result) {
                Assert.assertFalse(TextUtils.isEmpty(result));
            }
        });
    }

}
