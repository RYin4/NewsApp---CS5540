package com.example.rkjc.news_app_2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.view.Menu;
import android.view.MenuItem;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import android.widget.ProgressBar;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.content.Context;
import android.os.AsyncTask;
import java.io.IOException;
import android.widget.AdapterView;
import android.widget.EditText;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private NewsRecyclerViewAdapter mAdapter;
    private ArrayList<NewsItem> news = new ArrayList<>();
    private NewsItemViewModel mNewsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.news_recyclerview);
        mNewsViewModel = ViewModelProviders.of(this).get(NewsItemViewModel.class);
        mAdapter = new NewsRecyclerViewAdapter(this, news);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager( new LinearLayoutManager(this));

        mNewsViewModel.getAllNews().observe(this, new Observer<List<NewsItem>>() {
            @Override
            public void onChanged(@Nullable List<NewsItem> newsItems) {
                mAdapter.setNews(newsItems);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) {
            URL url = NetworkUtils.buildUrl("b3282c577785438a8c23efe931c987bb");
            mNewsViewModel.syncNews(url);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}