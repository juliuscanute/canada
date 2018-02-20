package com.jsonfeed.newsfeed.model.data;

import java.util.List;
/**
 * Created by juliuscanute on 20/02/18.
 */

public class FeedData {
    private String title;
    private List<FeedItem> rows;

    public String getTitle(){
        return title;
    }

    public List<FeedItem> getRows(){
        return rows;
    }
}
