package com.example.rent.filmbrowserapp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RENT on 2017-03-07.
 */

public class MovieListingItem {
    @SerializedName("Title")
    private String title;
    @SerializedName("Year")
    private String year;
    private String imdbID;

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getImdbID() {
        return imdbID;
    }

    public String getType() {
        return type;
    }

    public String getPoster() {
        return poster;
    }

    @SerializedName("Type")
    private String type;
    @SerializedName("Poster")
    private String poster;


}
