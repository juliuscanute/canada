package com.jsonfeed.newsfeed;

import android.app.Application;

import com.jsonfeed.newsfeed.inject.DependencyProvider;

/**
 * Created by juliuscanute on 20/02/18.
 */

public class FeedsApplication extends Application {
    private DependencyProvider mRegistry;

    @Override
    public void onCreate() {
        super.onCreate();
        mRegistry = DependencyProvider.shared;
    }
}
