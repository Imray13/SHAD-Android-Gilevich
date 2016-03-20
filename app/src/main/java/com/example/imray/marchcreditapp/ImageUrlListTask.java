package com.example.imray.marchcreditapp;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 17.03.2016.
 */
public class ImageUrlListTask extends AsyncTask<String, Void, List<String>> {

    private List<UrlsReadyListener> listeners = new ArrayList<UrlsReadyListener>();

    public void addListener(UrlsReadyListener toAdd) {
        listeners.add(toAdd);
    }

    public ImageUrlListTask()
    {

    }

    @Override
    protected void onPostExecute(List<String> result) {
        if(result == null)
        {
            Log.e("ImageUrlList", "Null list on post execute");
            return;
        }
        for (UrlsReadyListener listener : listeners)
            listener.onListDownloaded(result);
    }

    @Override
    protected List<String> doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream stream = connection.getInputStream();
                XmlPullParser parser = Xml.newPullParser();
                parser.setInput(stream, null);
                String imgUrl = null;
                List<String> imgUrls = new ArrayList<>();
                while ((imgUrl = processEntry(parser)) != null) {
                    imgUrls.add(imgUrl);
                }
                return imgUrls;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String processEntry(XmlPullParser parser) throws IOException, XmlPullParserException {
        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            String prefix = parser.getPrefix();
            // Starts by looking for the entry tag
            if ("f".equals(prefix) && "img".equals(name)) {
                for (int i = 0; i<parser.getAttributeCount(); i++) {
                    if ("height".equals(parser.getAttributeName(i))) {
                        if ("75".equals(parser.getAttributeValue(i))) {
                            return parser.getAttributeValue(i+1);
                        }
                    }
                }
            }
        }
        return null;
    }
}
