package com.example.rent.filmbrowserapp.search;

import com.example.rent.filmbrowserapp.listing.MovieListingItem;
import com.google.gson.annotations.SerializedName;

import java.util.List;



public class SearchResult {

    @SerializedName("Search")
    private List <MovieListingItem> items;
    String totalResults;

    public void setItems(List<MovieListingItem> items) {
        this.items = items;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public List<MovieListingItem> getItems() {
        return items;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public String getResponse() {
        return response;
    }

    @SerializedName("Response")
    String response;


}
