package com.example.wallpaperappbyducminh.getjson;

import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.wallpaperappbyducminh.adapter.CommentAdapter;
import com.example.wallpaperappbyducminh.model.comment.Comment;
import com.example.wallpaperappbyducminh.model.comment.ExampleComment;

import java.util.List;


public class GetComment {
    List<Comment> getCommentList;
    RecyclerView recyclerView;
    CommentAdapter commentAdapter;
    Context context;

    public GetComment(List<Comment> getCommentList, RecyclerView recyclerView, CommentAdapter commentAdapter, Context context) {
        this.getCommentList = getCommentList;
        this.recyclerView = recyclerView;
        this.commentAdapter = commentAdapter;
        this.context = context;
    }

    public void NetworkingGetComment (String id){
        AndroidNetworking.get("https://www.flickr.com/services/rest")
                .addQueryParameter("method", "flickr.photos.comments.getList")
                .addQueryParameter("api_key", "85e29beb36172de1178cbeb10a08e0e7")
                .addQueryParameter("photo_id", "" + id)
                .addQueryParameter("format", "json")
                .addQueryParameter("nojsoncallback", "1")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                // lấy Json trả về theo id
                .getAsObject(ExampleComment.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                       ExampleComment exampleComment = (ExampleComment) response;
                        List<Comment> getComments = exampleComment.getComments().getComment();
                     try{
                         getCommentList.addAll(getComments);
                         commentAdapter.notifyDataSetChanged();
                     }
                     catch (Exception e){

                     }

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }
}
