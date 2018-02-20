package com.jsonfeed.newsfeed.model;

import android.graphics.Bitmap;

import com.jsonfeed.newsfeed.model.data.FeedData;
import com.jsonfeed.newsfeed.model.data.FeedItem;

import java.io.File;
import java.util.List;
import javax.xml.transform.Source;
import io.reactivex.functions.Consumer;
/**
 * Created by juliuscanute on 20/02/18.
 */

public interface Feeder {
    void loadData(Consumer<FeedData> results, Consumer<String> notify);
    void loadImage(File directory, String url, Consumer<String> result, Consumer<String> notify,Consumer<Throwable> error);
    FeedData getCache();
}
