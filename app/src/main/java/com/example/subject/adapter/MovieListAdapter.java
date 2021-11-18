package com.example.subject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bean.ListItem;
import com.example.cuishanyi.R;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {
    private List<ListItem> topMovieList;
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(View view){
            super(view);
            imageView = view.findViewById(R.id.list_image);
        }

    }

    public MovieListAdapter(List<ListItem> topMovieList) {
        this.topMovieList = topMovieList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItem listItem = topMovieList.get(position);
        holder.imageView.setImageResource(listItem.getImageID());
    }

    @Override
    public int getItemCount() {
        return topMovieList.size();
    }
}
