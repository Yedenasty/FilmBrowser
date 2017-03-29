package com.example.rent.filmbrowserapp.listing;

import com.example.rent.filmbrowserapp.search.SearchResult;
import com.example.rent.filmbrowserapp.search.SearchService;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import nucleus.presenter.Presenter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListingPresenter extends Presenter<ListingActivity> implements OnLoadNextPageListener {

    private ResultAggregator resultAggregator = new ResultAggregator();

    private Retrofit retrofit;
    private String title;
    private String stringYear;
    private String type;


    public void startLoadingItems(String title, int year, String type) {
        this.title = title;
        this.type = type;
        stringYear = year == ListingActivity.NO_YEAR_SELECTED ? null : String.valueOf(year);

        if (resultAggregator.getMovieItems().size() > 0) {
            getView().setNewAggregatorResult(resultAggregator);
        } else {
            loadNextPage(1);
        }
    }

    public void setRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    @Override
    public void loadNextPage(int page) {
        retrofit.create(SearchService.class).search(page, title, stringYear, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(searchResult -> {
                    resultAggregator.addNewItems(searchResult.getItems());
                    resultAggregator.setTotalItemsResult(Integer.parseInt(searchResult.getTotalResults()));
                    resultAggregator.setResponse(searchResult.getResponse());
                    getView().setNewAggregatorResult(resultAggregator);
                }, throwable -> {
                });
    }
}
