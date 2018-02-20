package com.jsonfeed.newsfeed.model.network;

import android.util.Log;
import com.jsonfeed.newsfeed.model.async.Threading;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import io.reactivex.functions.Consumer;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by juliuscanute on 20/02/18.
 */

public class JSONNetworkLoader implements JSONLoader {
    private static final String TAG = "JSONNetworkLoader";
    public static final String JSON_URL = "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/facts.json";

    @Override
    public void loadJson(Consumer<String> callback) {
        Threading.async(()-> createJSONRequest(),callback,null);
    }

    private String createJSONRequest() {
        String result = "";
        try {
            result = requestJSON(JSON_URL);
        } catch (Exception e) {
            Log.d(TAG, "unable to connect to the url",e);
        }
        Log.d(TAG,"Result: "+result);
        return result;
    }

    private String requestJSON(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }



}
