package com.example.waymon.coolweather.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2016/6/11 0011.
 */
public class HttpUtil {
    public static void sendHttpRequest(final String address,
                                       final HttpCallbackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;

                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    StringBuilder response = new StringBuilder();
                    String line = null;
                    while((line = reader.readLine()) != null){
                        response.append(line);
                    }

                    if(listener !=null){
                        listener.onFinished(response.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if(listener !=null){
                        listener.onError(e);
                    }
                } finally {
                    if(connection !=null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
}
