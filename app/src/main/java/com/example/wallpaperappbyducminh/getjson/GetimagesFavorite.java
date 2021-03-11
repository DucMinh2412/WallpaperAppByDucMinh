package com.example.wallpaperappbyducminh.getjson;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.wallpaperappbyducminh.activity.MainActivity;
import com.example.wallpaperappbyducminh.activity.WelcomeActivity;
import com.example.wallpaperappbyducminh.adapter.FavoriteAdapter;
import com.example.wallpaperappbyducminh.model.favorite.Favorites;
import com.example.wallpaperappbyducminh.model.favorite.Photo;
import com.example.wallpaperappbyducminh.setting.CustomItemDecorator;
import com.example.wallpaperappbyducminh.setting.EndlessRecyclerViewScrollListener;

import java.util.List;


// void = no value
public class GetimagesFavorite {
    List<Photo> photoList;
    RecyclerView recyclerView;
    FavoriteAdapter favoriteAdapter;
    Context context;
    SwipeRefreshLayout swipeRefreshLayout;
    StaggeredGridLayoutManager manager;
    private int per_pager = 10;
    private int pager = 1;

    public GetimagesFavorite(List<Photo> photoList, RecyclerView recyclerView, FavoriteAdapter favoriteAdapter, Context context, SwipeRefreshLayout swipeRefreshLayout, StaggeredGridLayoutManager manager) {
        this.photoList = photoList;
        this.recyclerView = recyclerView;
        this.favoriteAdapter = favoriteAdapter;
        this.context = context;
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.manager = manager;
    }


    public void Refresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        photoList.clear();
                        per_pager = 10;
                        pager = 1;
                        NetworkingGetJsonFavorite(per_pager, pager);
                        Loadmore();
                    }
                }, 1000);
            }
        });
    }

    public void Loadmore (){
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore(int pages, int totalItemsCount, RecyclerView view) {
                swipeRefreshLayout.setRefreshing(true);
                pager +=1;
                NetworkingGetJsonFavorite(per_pager,pager);
            }
        });
    }


    public void NetworkingGetJsonFavorite(int per_page,int page) {
        AndroidNetworking.post("https://www.flickr.com/services/rest/")
                .addBodyParameter("method", "flickr.favorites.getList")
                .addBodyParameter("api_key", "85e29beb36172de1178cbeb10a08e0e7")
                .addBodyParameter("user_id", "187036618@N06")
                .addBodyParameter("extras", "views,media,path_alias,url_sq,url_t,url_s,url_q,url_m,url_n,url_z,url_c,url_l,url_o")
                .addBodyParameter("per_page",String.valueOf(per_page))
                .addBodyParameter("page", String.valueOf(page))
                .addBodyParameter("format", "json")
                .addBodyParameter("nojsoncallback", "1")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObject(Favorites.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        //tat su kien loading
                        swipeRefreshLayout.setRefreshing(false);
                        Favorites favorites = (Favorites) response;
                        List<Photo> photos = favorites.getPhotos().getPhoto();
                        photoList.addAll(photos);
                        favoriteAdapter.notifyDataSetChanged();
                        Log.e("size List",photoList.size()+"");
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
}

