package com.example.rent.filmbrowserapp;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.annimon.stream.Stream;

import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusAppCompatActivity;

@RequiresPresenter(ListingPresenter.class)
public class ListingActivity extends NucleusAppCompatActivity<ListingPresenter> {
    private static final String SEARCH_TITLE = "search_title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_list);
        String title = getIntent().getStringExtra(SEARCH_TITLE);
        getPresenter().getDataAsync(title);
    }

    public void setDataOnUiThread(SearchResult results) {
        runOnUiThread(() -> {
            Stream.of(results.getItems()).forEach(movieListingItem -> {
                Log.d("result", "id " + movieListingItem.getImdbID());
            });
        });
    }

    public static Intent createIntent(Context context, String title) {
        Intent intent = new Intent(context, ListingActivity.class);
        intent.putExtra(SEARCH_TITLE, title);
        return intent;
    }
}