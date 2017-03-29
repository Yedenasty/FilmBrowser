package com.example.rent.filmbrowserapp.listing;


import com.example.rent.filmbrowserapp.details.MovieItem;

import java.util.ArrayList;
import java.util.List;

public class ResultAggregator {
    public int getTotalItemsResult() {
        return totalItemsResult;
    }

    private String response;

    public List<MovieListingItem> getMovieItems() {
        return movieItems;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    private List<MovieListingItem> movieItems = new ArrayList<>();
    private int totalItemsResult;

    public void setTotalItemsResult(int totalItemsResult) {
        this.totalItemsResult = totalItemsResult;
    }

    public void addNewItems(List<MovieListingItem> newItems) {
        movieItems.addAll(newItems);
    }

}
