package com.example.imray.marchcreditapp;

import java.util.EventListener;
import java.util.List;

/**
 * Created by lenovo on 18.03.2016.
 */
public interface ImageReadyListener extends EventListener {
    void onImageDownloaded(byte[] image);
}
