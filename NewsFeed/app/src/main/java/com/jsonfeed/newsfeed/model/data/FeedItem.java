package com.jsonfeed.newsfeed.model.data;
/**
 * Created by juliuscanute on 20/02/18.
 */

public class FeedItem {
    private String title;
    private String description;
    private String imageHref;

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public String getImageHref(){
        return imageHref;
    }

    public String getFileName(){
        String filename = null;
        if(imageHref != null) {
            String parts[] = imageHref.split("/");
            if(parts.length > 1)
                filename = parts[parts.length - 1];
        }
        return filename;
    }
}
