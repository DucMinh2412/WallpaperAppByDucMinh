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
import com.example.wallpaperappbyducminh.model.comment.Comment;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {
    public Context context;
    public RecyclerView recyclerView;
    public List<Comment> commentList;

    public CommentAdapter(Context context, RecyclerView recyclerView, List<Comment> commentList) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(context).
                        inflate(R.layout.listcomment, parent, false);

        CommentHolder commentHolder = new CommentHolder(view);

        return commentHolder;


    }

    @Override
    public void onBindViewHolder(@NonNull final CommentHolder commentHolder, final int position) {
        final Comment comment = commentList.get(position);
        commentHolder.name.setText(comment.getAuthorname());
        commentHolder.content.setText(comment.getContent());
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }


    public class CommentHolder extends RecyclerView.ViewHolder {
        public TextView name,content;

        public CommentHolder(@NonNull View itemView) {
            super(itemView);
          name = itemView.findViewById(R.id.tvauthorname);
          content = itemView.findViewById(R.id.tvcontent);
        }
    }
}
