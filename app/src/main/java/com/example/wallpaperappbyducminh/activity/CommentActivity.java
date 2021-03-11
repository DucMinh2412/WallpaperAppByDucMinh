package com.example.wallpaperappbyducminh.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.wallpaperappbyducminh.R;
import com.example.wallpaperappbyducminh.adapter.CommentAdapter;
import com.example.wallpaperappbyducminh.adapter.FavoriteAdapter;
import com.example.wallpaperappbyducminh.getjson.GetComment;
import com.example.wallpaperappbyducminh.model.comment.Comment;
import com.example.wallpaperappbyducminh.setting.CustomItemDecorator;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity {
    List<Comment> getCommentList;
    RecyclerView recyclerView;
    CommentAdapter commentAdapter;
    ImageView exitcomment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        getCommentList = new ArrayList<>();
        recyclerView = findViewById(R.id.rclComment);
        exitcomment = findViewById(R.id.ImgExitComment);
        setAdapter();
        Intent intent = getIntent();
        String id = intent.getStringExtra("idImage");
        GetComment getComment = new GetComment(getCommentList,recyclerView,commentAdapter,this);
        getComment.NetworkingGetComment(id);

        exitcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void setAdapter(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentAdapter = new CommentAdapter(this,recyclerView,getCommentList);
        recyclerView.setAdapter(commentAdapter);
    }


}