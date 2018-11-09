package com.example.rkjc.news_app_2;

import android.net.Uri;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import android.net.Uri;
import android.net.Uri.Builder;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.InputStream;
import java.util.Scanner;

public class NetworkUtils {

    //Define the appropriate base_url and query_parameter constants
    final static String PARAM_SOURCE = "source";
    final static String source = "the-next-web";
    final static String PARAM_APIKEY = "apiKey";
    final static String NEWSAPI_BASE_URL = "https://newsapi.org/v1/articles";
    final static String apiKey = "b3282c577785438a8c23efe931c987bb";
    final static String PARAM_SORT = "sort";
    final static String sortBy = "stars";

    public static URL buildUrl() {

        Uri builtUri = Uri.parse(NEWSAPI_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_SOURCE, source)
                .appendQueryParameter(PARAM_SORT, sortBy)
                .appendQueryParameter(PARAM_APIKEY, apiKey)
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    //copied method from powerpoint
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}














