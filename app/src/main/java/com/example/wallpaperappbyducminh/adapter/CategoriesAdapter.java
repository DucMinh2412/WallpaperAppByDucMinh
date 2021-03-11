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
import com.example.wallpaperappbyducminh.R;
import com.example.wallpaperappbyducminh.activity.listimages.ListForCategoryActivity;
import com.example.wallpaperappbyducminh.model.categories.Gallery;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesHolder> {
    public Context context;
    public RecyclerView recyclerView;
    public List<Gallery> galleryList;

    public CategoriesAdapter(Context context, RecyclerView recyclerView, List<Gallery> galleryList) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.galleryList = galleryList;
    }

    @NonNull
    @Override
    public CategoriesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(context).
                        inflate(R.layout.listcategory, parent, false);

        CategoriesHolder categoriesHolder = new CategoriesHolder(view);

        return categoriesHolder;


    }

    @Override
    public void onBindViewHolder(@NonNull final CategoriesHolder categoriesHolder, final int position) {
        final Gallery gallery =  galleryList.get(position);
        categoriesHolder.textView.setText(gallery.getTitle().getContent()+"");
        Glide.with(context).load(gallery.getPrimaryPhotoExtras().getUrlC()).priority(Priority.IMMEDIATE).into(categoriesHolder.imageView);

        categoriesHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ListForCategoryActivity.class);
                intent.putExtra("gallery_id",gallery.getGalleryId());
                intent.putExtra("title",gallery.getTitle().getContent());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }


    public class CategoriesHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;

        public CategoriesHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ImgImageCategory);
            textView = itemView.findViewById(R.id.titleCategory);
        }
    }
}
