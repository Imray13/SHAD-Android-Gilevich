package com.example.imray.marchcreditapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.IOUtils;

/**
 * Created by lenovo on 17.03.2016.
 */
public class ImageDownloadTask extends AsyncTask<String, Void, byte[]> {

    private List<ImageReadyListener> listeners = new ArrayList<ImageReadyListener>();

    public void addListener(ImageReadyListener toAdd) {
        listeners.add(toAdd);
    }

    public ImageDownloadTask() {

    }
    @Override
    protected byte[] doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream stream = connection.getInputStream();
                return  IOUtils.toByteArray(stream);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(byte[] result)
    {
        if(result == null)
        {
            Log.e("Image Download", "NULL image on post execute");
            return;
        }
        for (ImageReadyListener listener : listeners)
            listener.onImageDownloaded(result);
    }
}
