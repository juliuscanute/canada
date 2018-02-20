package com.jsonfeed.newsfeed.model.network;

import java.io.File;

import io.reactivex.functions.Consumer;

/**
 * Created by juliuscanute on 20/02/18.
 */

public interface JSONLoader {
    void loadJson(Consumer<String> callback);
}
