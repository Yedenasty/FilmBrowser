package com.example.rent.filmbrowserapp.search;

import com.example.rent.filmbrowserapp.listing.MovieListingItem;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by RENT on 2017-03-07.
 */

public class SearchResult {

    @SerializedName("Search")
    private List <MovieListingItem> items;
    String totalResults;

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
