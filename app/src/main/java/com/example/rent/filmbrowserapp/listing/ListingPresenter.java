package com.example.rent.filmbrowserapp.listing;

import com.example.rent.filmbrowserapp.search.SearchResult;
import com.example.rent.filmbrowserapp.search.SearchService;

import io.reactivex.Observable;
import nucleus.presenter.Presenter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListingPresenter extends Presenter<ListingActivity> {

    private Retrofit retrofit;
    public ListingPresenter() {

    }

    public Observable<SearchResult> getDataAsync(String title, int year, String type){

        String stringYear = year == ListingActivity.NO_YEAR_SELECTED ? null : String.valueOf(year);
        return retrofit.create(SearchService.class).search(title,
                stringYear, type);      //search to nasza metoda z interface SearchService
    }

    public void setRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;
    }
}
