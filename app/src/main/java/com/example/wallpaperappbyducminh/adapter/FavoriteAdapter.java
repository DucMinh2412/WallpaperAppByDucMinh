package com.example.wallpaperappbyducminh.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.example.wallpaperappbyducminh.activity.showimages.FavoriteShowImagesActivity;
import com.example.wallpaperappbyducminh.model.favorite.Photo;
import com.example.wallpaperappbyducminh.R;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder> {
    public Context context;
    public RecyclerView recyclerView;
    public ArrayList<Photo> photoList;

    public FavoriteAdapter(Context context, RecyclerView recyclerView, ArrayList<Photo> photoList) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.photoList = photoList;
    }

    @NonNull
    @Override
    public FavoriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(context).
                        inflate(R.layout.listimagesfavorite, parent, false);

        FavoriteHolder favoriteHolder = new FavoriteHolder(view);

        return favoriteHolder;


    }

    @Override
    public void onBindViewHolder(@NonNull final FavoriteHolder favoriteHolder, final int position) {
        final Photo photo = photoList.get(position);
        favoriteHolder.textView.setText(photo.getViews()+ " views ");
        Glide.with(context).load(photo.getUrlZ()).override(600,800).priority(Priority.IMMEDIATE).into(favoriteHolder.imageView);

        favoriteHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(context, FavoriteShowImagesActivity.class);
                intent.putExtra("List",photoList);
                intent.putExtra("Position",position);
              context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }


    public class FavoriteHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;

        public FavoriteHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ImgImage);
            textView = itemView.findViewById(R.id.views);
        }
    }
}
