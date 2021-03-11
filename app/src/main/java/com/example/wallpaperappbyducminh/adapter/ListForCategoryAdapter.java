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
import com.example.wallpaperappbyducminh.R;
import com.example.wallpaperappbyducminh.activity.showimages.CategoryShowImageActivity;
import com.example.wallpaperappbyducminh.model.listforcategories.Photo;

import java.util.ArrayList;

public class ListForCategoryAdapter extends RecyclerView.Adapter<ListForCategoryAdapter.ListForCategoryHolder> {
    public Context context;
    public RecyclerView recyclerView;
    public ArrayList<Photo> photoList;

    public ListForCategoryAdapter(Context context, RecyclerView recyclerView, ArrayList<Photo> photoList) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.photoList = photoList;
    }

    @NonNull
    @Override
    public ListForCategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(context).
                        inflate(R.layout.listimagesforcategory, parent, false);

        ListForCategoryHolder listForCategoryHolder = new ListForCategoryHolder(view);

        return listForCategoryHolder;


    }

    @Override
    public void onBindViewHolder(@NonNull final ListForCategoryHolder listForCategoryHolder, final int position) {
        final Photo photo = photoList.get(position);
        listForCategoryHolder.textView.setText(photo.getViews()+ " views ");
        Glide.with(context).load(photo.getUrlZ()).override(600,800).into(listForCategoryHolder.imageView);

        listForCategoryHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CategoryShowImageActivity.class);
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


    public class ListForCategoryHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;

        public ListForCategoryHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ImgImage);
            textView = itemView.findViewById(R.id.views);
        }
    }
}
