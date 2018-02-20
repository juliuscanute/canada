package com.jsonfeed.newsfeed.presenter;

import android.util.Log;

import com.jsonfeed.newsfeed.model.Feeder;
import com.jsonfeed.newsfeed.model.data.FeedData;
import java.io.File;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by juliuscanute on 20/02/18.
 */

public class FeedPresenterImpl implements FeedPresenter {
    private static final String TAG = "FeedPresenterImpl";
    private BehaviorSubject<FeedData> mFeedData = BehaviorSubject.create();
    private BehaviorSubject<String> mImageData = BehaviorSubject.create();
    private Feeder mFeeder;

    public FeedPresenterImpl(Feeder feeder){
        mFeeder = feeder;
    }

    @Override
    public void loadData(Consumer<String> received) {
        mFeeder.loadData(this::onDataLoaded, received);
    }

    @Override
    public void loadImage(File directory, String url, Consumer<String> received,Consumer<Throwable> error) {
        mFeeder.loadImage(directory,url,this::onImageLoaded,received,error);
    }

    @Override
    public FeedData getCache() {
        return mFeeder.getCache();
    }

    private void onDataLoaded(FeedData feedData) {
        if(feedData == null){
            feedData = new FeedData();
        }
        mFeedData.onNext(feedData);
    }

    private void onImageLoaded(String imagePath){
        Log.d(TAG,"Image path:"+imagePath);
    }

    @Override
    public BehaviorSubject<FeedData> feeds() {
        return mFeedData;
    }


}
