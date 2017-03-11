package com.example.rent.filmbrowserapp.listing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.rent.filmbrowserapp.R;
import com.example.rent.filmbrowserapp.RetrofitProvider;
import com.example.rent.filmbrowserapp.search.SearchResult;

import static io.reactivex.schedulers.Schedulers.io;

import butterknife.BindView;
import butterknife.ButterKnife;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusAppCompatActivity;

import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;

@RequiresPresenter(ListingPresenter.class)
public class ListingActivity extends NucleusAppCompatActivity<ListingPresenter> implements CurrentItemListener, ShowOrHideCounter {
    private static final String SEARCH_TITLE = "search_title";
    private static final String SEARCH_YEAR = "search_year";
    private static final String SEARCH_TYPE = "search_type";
    public static final int NO_YEAR_SELECTED = -1;
    private MoviesListAdaprter adapter;

    @BindView(R.id.view_flipper)
    ViewFlipper viewFlipper;

    @BindView(R.id.no_internet_view)
    FrameLayout noInternetView;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.no_results)
    FrameLayout noResults;

    @BindView(R.id.counter)
    TextView counter;

    private EndlessScrollListener endlessScrollListener;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_list);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            RetrofitProvider retrofitProvider = (RetrofitProvider) getApplication();
            getPresenter().setRetrofit(retrofitProvider.provideRetrofit());
        }

        String title = getIntent().getStringExtra(SEARCH_TITLE);
        int year = getIntent().getIntExtra(SEARCH_YEAR, NO_YEAR_SELECTED);
        String type = getIntent().getStringExtra(SEARCH_TYPE);

        adapter = new MoviesListAdaprter();
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        endlessScrollListener = new EndlessScrollListener(layoutManager, getPresenter());
        recyclerView.addOnScrollListener(endlessScrollListener);
        endlessScrollListener.setCurrentItemListener(this);
        endlessScrollListener.setShowOrHideCounter(this);

        getPresenter().getDataAsync(title, year, type)   //getPresenter zwraca prezentaera którego wczesniej definiowalismy
                .subscribeOn(io())       //to co jest  powyżej jest wykonane w innym wątku
                .observeOn(mainThread())  //to co bedzie wykonywane w głównym wątku
                .subscribe(this::success, this::error);


      //  endlessScrollListener.onScrollStateChanged(recyclerView);
    }

    private void error(Throwable throwable) {
        viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(noInternetView));
    }

    public void appendItems(SearchResult searchResult) {
        adapter.addItems(searchResult.getItems());
        endlessScrollListener.setTotalItemsNumber(Integer.parseInt(searchResult.getTotalResults()));
    }



    private void success(SearchResult searchResult) {
        if ("false".equalsIgnoreCase(searchResult.getResponse())) {
            viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(noResults));
        } else {
            viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(recyclerView));
            adapter.setItems(searchResult.getItems());
            endlessScrollListener.setTotalItemsNumber(Integer.parseInt(searchResult.getTotalResults()));
        }
    }
    public static Intent createIntent(Context context, String title, int year, String type) {
        Intent intent = new Intent(context, ListingActivity.class);
        intent.putExtra(SEARCH_TITLE, title);
        intent.putExtra(SEARCH_YEAR, year);
        intent.putExtra(SEARCH_TYPE, type);
        return intent;
    }

    @Override
    public void onNewCurrentItem(int currentItem, int totalItemsCount) {
        counter.setText(currentItem + "/" + totalItemsCount);
    }

    @Override
    public void showCounter() {
        counter.setVisibility(View.VISIBLE);
       counter.animate().alpha(1).start();
    }

    @Override
    public void hideCounter() {
        counter.animate().alpha(0).start();
    }
}