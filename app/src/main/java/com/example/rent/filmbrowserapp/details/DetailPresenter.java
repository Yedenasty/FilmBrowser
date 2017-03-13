package com.example.rent.filmbrowserapp.details;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.rent.filmbrowserapp.RetrofitProvider;


import io.reactivex.Observable;
import nucleus.presenter.Presenter;
import retrofit2.Retrofit;


public class DetailPresenter extends Presenter<DetailActivity> {

    private Retrofit retrofit;


    public void setRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedState) {

    }
    public Observable<MovieItem> loadDetail (String imdbId) {
        DetailService detailService = retrofit.create(DetailService.class);
        return detailService.getDetailInfo(imdbId);

    }
}
