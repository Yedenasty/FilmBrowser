package com.example.rent.filmbrowserapp.listing;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rent.filmbrowserapp.R;

import java.util.Collections;
import java.util.List;


public class MoviesListAdaprter extends RecyclerView.Adapter<MoviesListAdaprter.MyViewHolder> {

    private List<MovieListingItem> items = Collections.emptyList();
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MovieListingItem movieListingItem = items.get(position);
        Glide.with(holder.poster.getContext()).load(movieListingItem.getPoster()).into(holder.poster);
        holder.titleAndYear.setText(movieListingItem.getTitle()  + "\n(" + movieListingItem.getYear() + " rok)");
        holder.type.setText("typ: " + movieListingItem.getType());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<MovieListingItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void addItems(List<MovieListingItem> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView poster;
        TextView titleAndYear;
        TextView type;

        public MyViewHolder(View itemView) {
            super(itemView);
            poster = (ImageView) itemView.findViewById(R.id.poster);
            titleAndYear = (TextView) itemView.findViewById(R.id.title_and_year);
            type = (TextView) itemView.findViewById(R.id.type);

        }

    }
}
