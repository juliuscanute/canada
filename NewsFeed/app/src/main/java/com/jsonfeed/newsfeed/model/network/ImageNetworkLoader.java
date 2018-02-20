package com.jsonfeed.newsfeed.model.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import com.jsonfeed.newsfeed.model.async.Threading;
import org.apache.commons.io.IOUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.NoRouteToHostException;

import io.reactivex.functions.Consumer;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by juliuscanute on 20/02/18.
 */

public class ImageNetworkLoader implements ImageLoader {
    private static final String TAG = "ImageNetworkLoader";

    @Override
    public void loadImage(File directory,String url,Consumer<String> callback,Consumer<Throwable> error){
        Threading.async(()-> createImageRequest(directory,url),callback,error);
    }

    private String createImageRequest(File directory,String url) throws IOException {
        String result = "";
        result = requestImage(directory,url);
        return result;
    }


    private String requestImage(File directory, String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        String[] parts = url.split("/");
        String fileName = parts[parts.length-1];
        Response response = client.newCall(request).execute();
        File imgDir = new File(directory.getAbsoluteFile()+"/images/");
        if(!imgDir.exists()){
            imgDir.mkdir();
        }
        File file = new File(directory.getAbsoluteFile()+"/images/"+fileName);
        Log.d(TAG,"Creating file:"+file.getAbsolutePath());
        file.createNewFile();
        FileOutputStream outputStream=new FileOutputStream(file,false);
        InputStream inputStream = response.body().byteStream();
        IOUtils.copy(inputStream, outputStream);
        outputStream.close();

        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        if(bitmap == null){
            file.delete();
            throw new NoRouteToHostException();
        }
        bitmap.recycle();

        return file.getAbsolutePath();
    }
}
