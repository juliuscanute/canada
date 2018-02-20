package com.jsonfeed.newsfeed.presenter;

import com.jsonfeed.newsfeed.model.data.FeedData;
import java.io.File;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by juliuscanute on 20/02/18.
 */

public interface FeedPresenter {
    void loadData(Consumer<String> received);
    void loadImage(File directory, String url, Consumer<String> received,Consumer<Throwable> error);
    FeedData getCache();
    BehaviorSubject<FeedData> feeds();
}
