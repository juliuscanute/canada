package com.jsonfeed.newsfeed.inject;

import com.google.gson.Gson;
import com.jsonfeed.newsfeed.fragment.FeedFragment;
import com.jsonfeed.newsfeed.model.Feeder;
import com.jsonfeed.newsfeed.model.FeederImpl;
import com.jsonfeed.newsfeed.model.network.ImageLoader;
import com.jsonfeed.newsfeed.model.network.ImageNetworkLoader;
import com.jsonfeed.newsfeed.model.network.JSONLoader;
import com.jsonfeed.newsfeed.model.network.JSONNetworkLoader;
import com.jsonfeed.newsfeed.model.translate.Converter;
import com.jsonfeed.newsfeed.model.translate.JSONConverter;
import com.jsonfeed.newsfeed.presenter.FeedPresenter;
import com.jsonfeed.newsfeed.presenter.FeedPresenterImpl;
/**
 * Created by juliuscanute on 20/02/18.
 */

public class DependencyProvider {
    public static  DependencyProvider shared = new DependencyProvider();

    private Gson mGson = new Gson();
    private Converter mConverter = createConverter();
    private JSONLoader mJsonLoader = createJsonLoader();
    private ImageLoader mImageLoader = createImageLoader();
    private Feeder mJsonFeeder = createFeeder();

    private DependencyProvider(){}

    private Converter createConverter(){
        return new JSONConverter(mGson);
    }

    private JSONLoader createJsonLoader(){
        return new JSONNetworkLoader();
    }

    private ImageLoader createImageLoader(){
        return new ImageNetworkLoader();
    }

    private Feeder createFeeder() {
        return new FeederImpl(mConverter, mJsonLoader, mImageLoader);
    }

    public void inject(FeedFragment feedFragment){
        FeedPresenter presenter = new FeedPresenterImpl(mJsonFeeder);
        feedFragment.initWith(presenter);
    }

}
