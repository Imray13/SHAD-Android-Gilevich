package com.example.imray.marchcreditapp;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lenovo on 17.03.2016.
 */
public class ImageKeeper {

    private List<String> urlList = new ArrayList<>();
    private HashMap<String, byte[]> cash = new HashMap<>();
    private ImageReadyListener updateViewListener;
    public final Bitmap NO_IMAGE = null;



    private final String IMAGE_ADDRESS = "http://api-fotki.yandex.ru/api/podhistory/poddate;2012-04-01T12:00:00Z/";

    public ImageKeeper(ImageReadyListener updateViewListener)
    {
        this.updateViewListener = updateViewListener;
        ImageUrlListTask task = new ImageUrlListTask();
        task.addListener(new UrlsReadyListener() {
            @Override
            public void onListDownloaded(List<String> list) {
                urlList = list;
                for (String url : list) {
                    downloadBitMapToCash(url);
                }
            }
        });
        task.execute(IMAGE_ADDRESS);
    }

    public Bitmap getBitmap(int position)
    {
        String urlString = urlList.get(position);
        if(!cash.containsKey(urlString)) {
            downloadBitMapToCash(urlString);
            return null;
        }
        return getBitmapFromCash(urlString);
    }

    public int getItemCount()
    {
        return urlList.size();
    }


    private void downloadBitMapToCash(final String url)
    {
        ImageDownloadTask task = new ImageDownloadTask();
        task.addListener(new ImageReadyListener() {
            @Override
            public void onImageDownloaded(byte[] image) {
                cash.put(url, image);
            }
        });
        task.addListener(updateViewListener);
        task.execute(url);
    }

    private Bitmap getBitmapFromCash(String url)
    {
        byte[] byteImage = cash.get(url);
        return BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
    }


}
