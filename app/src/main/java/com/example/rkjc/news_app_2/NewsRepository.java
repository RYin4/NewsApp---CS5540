package com.example.rkjc.news_app_2;

import android.os.AsyncTask;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import java.util.List;

public class NewsRepository {
    private NewsItemDao mNewsDao;
    private LiveData<List<NewsItem>> mAllNews;

    NewsRepository(Application application) {
        NewsDatabase db = NewsDatabase.getDatabase(application);
        mNewsDao = db.newsDao();
        mAllNews = mNewsDao.loadAllNewsItems();
    }

    public LiveData<List<NewsItem>> getAllNews() {
        new allNewsAsyncTask(mNewsDao).execute();
        return mAllNews;
    }

    public void syncNews(URL url) {
        new syncNewsAsyncTask(mNewsDao).execute(url);
    }

    private class allNewsAsyncTask extends AsyncTask<Void, Void, Void> {

        private NewsItemDao mAsyncTaskDao;
        allNewsAsyncTask(NewsItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAllNews = mAsyncTaskDao.loadAllNewsItems();
            return null;
        }
    }

    private class syncNewsAsyncTask extends AsyncTask<URL, Void, Void> {

        private NewsItemDao mAsyncTaskDao;
        syncNewsAsyncTask(NewsItemDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(URL... urls) {
            String searchResults = "";
            ArrayList<NewsItem> news = new ArrayList<>();

            try {
                searchResults = NetworkUtils.getResponseFromHttpUrl(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

            mAsyncTaskDao.clearAll();
            news = JsonUtils.parseNews(searchResults);
            mAsyncTaskDao.insert(news);
            return null;
        }
    }
}