package com.jsonfeed.newsfeed.model;

import com.jsonfeed.newsfeed.model.async.Threading;
import com.jsonfeed.newsfeed.model.data.FeedData;
import com.jsonfeed.newsfeed.model.network.ImageLoader;
import com.jsonfeed.newsfeed.model.network.JSONLoader;
import com.jsonfeed.newsfeed.model.translate.Converter;
import java.io.File;
import io.reactivex.functions.Consumer;

/**
 * Created by juliuscanute on 20/02/18.
 */

public class FeederImpl implements Feeder {
    private static final String TAG = "FeederImpl";

    private Converter mConverter;
    private JSONLoader mJsonLoader;
    private ImageLoader mImageLoader;
    private FeedData mFeedData;

    public FeederImpl(Converter converter, JSONLoader jsonLoader, ImageLoader imageLoader){
        mConverter = converter;
        mJsonLoader = jsonLoader;
        mImageLoader = imageLoader;
    }

    @Override
    public void loadData(Consumer<FeedData> results, Consumer<String> notify) {
        mJsonLoader.loadJson(json -> {
            notify.accept("Data Received");
            FeedData feedData = mConverter.convertJsonToFeed(json);
            mFeedData = (feedData!=null)?feedData:mFeedData;
            Threading.dispatchMain(()->results.accept(mFeedData));
        });
    }

    @Override
    public FeedData getCache(){
        return mFeedData;
    }

    @Override
    public void loadImage(File directory, String url, Consumer<String> result, Consumer<String> notify, Consumer<Throwable> error) {
        mImageLoader.loadImage(directory,url,image -> {
            notify.accept("Image Received");
            Threading.dispatchMain(()->result.accept(image));
        },error);
    }
}
