package com.example.imray.marchcreditapp;

import java.util.EventListener;
import java.util.List;

/**
 * Created by lenovo on 18.03.2016.
 */
public interface UrlsReadyListener extends EventListener {
     void onListDownloaded(List<String> list);
}
