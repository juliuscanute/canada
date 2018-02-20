package com.jsonfeed.newsfeed.model.translate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jsonfeed.newsfeed.model.data.FeedData;
import java.util.List;

/**
 * Created by juliuscanute on 19/02/18.
 */

public class JSONConverter implements Converter {

    private Gson mGson;

    public JSONConverter(Gson gson){
        mGson = gson;
    }

    @Override
    public FeedData convertJsonToFeed(String json) {
        TypeToken<FeedData> token = new TypeToken<FeedData>(){};
        return mGson.fromJson(json, token.getType());
    }

}
