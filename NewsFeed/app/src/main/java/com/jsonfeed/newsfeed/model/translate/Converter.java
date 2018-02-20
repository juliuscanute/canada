package com.jsonfeed.newsfeed.model.translate;

import com.jsonfeed.newsfeed.model.data.FeedData;
/**
 * Created by juliuscanute on 19/02/18.
 */

public interface Converter {
    FeedData convertJsonToFeed(String json);
}
