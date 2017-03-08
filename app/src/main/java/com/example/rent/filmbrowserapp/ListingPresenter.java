package com.example.rent.filmbrowserapp;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import nucleus.presenter.Presenter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListingPresenter extends Presenter<ListingActivity> {

    private Retrofit retrofit;
    public ListingPresenter() {
        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://www.omdbapi.com/")
                .build();
    }

    public void getDataAsync(String title){
        new Thread() {

            @Override
            public void run() {
                try {
                    String result = getData(title);
                    SearchResult searchResult = new Gson().fromJson(result, SearchResult.class);
                    getView().setDataOnUiThread(searchResult, false);
                } catch (IOException e) {
                    getView().setDataOnUiThread(null, true);
                }
            }
        }.start();
    }

   private String getData(String title) throws IOException {
        String stringUrl = "https://www.omdbapi.com/?s=" + title;
        URL url = new URL(stringUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setConnectTimeout(3000);
        InputStream inputStream = urlConnection.getInputStream();
        return convertStreamToString(inputStream);
    }

    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

}
