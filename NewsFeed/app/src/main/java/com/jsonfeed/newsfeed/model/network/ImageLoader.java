package com.jsonfeed.newsfeed.model.network;

import java.io.File;

import io.reactivex.functions.Consumer;

/**
 * Created by juliuscanute on 20/02/18.
 */

public interface ImageLoader {
    void loadImage(File directory, String url, Consumer<String> callback,Consumer<Throwable> error);
}
