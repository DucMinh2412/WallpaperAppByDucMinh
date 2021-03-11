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
import com.example.wallpaperappbyducminh.activity.showimages.SearchShowImagesActivity;
import com.example.wallpaperappbyducminh.model.search.Photo;
import java.util.ArrayList;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.SearchListHolder> {
    public Context context;
    public RecyclerView recyclerView;
    public ArrayList<Photo> photoList;

    public SearchListAdapter(Context context, RecyclerView recyclerView, ArrayList<Photo> photoList) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.photoList = photoList;
    }

    @NonNull
    @Override
    public SearchListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(context).
                        inflate(R.layout.listsearch, parent, false);

        SearchListHolder searchListHolder = new SearchListHolder(view);

        return searchListHolder;


    }

    @Override
    public void onBindViewHolder(@NonNull final SearchListHolder searchListHolder, final int position) {
        final Photo photo = photoList.get(position);
        searchListHolder.textView.setText(photo.getViews()+ " views ");
        Glide.with(context).load(photo.getUrlZ()).override(600, 800).priority(Priority.IMMEDIATE).into(searchListHolder.imageView);

      searchListHolder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, SearchShowImagesActivity.class);
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


    public class SearchListHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;

        public SearchListHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ImgImage);
            textView = itemView.findViewById(R.id.views);
        }
    }
}
