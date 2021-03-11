package com.example.wallpaperappbyducminh.getjson;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.wallpaperappbyducminh.adapter.ListForCategoryAdapter;
import com.example.wallpaperappbyducminh.model.listforcategories.ListForCategory;
import com.example.wallpaperappbyducminh.model.listforcategories.Photo;
import com.example.wallpaperappbyducminh.setting.EndlessRecyclerViewScrollListener;

import java.util.List;


// void = no value
public class GetListForCategories {
    List<Photo> photoList;
    RecyclerView recyclerView;
    ListForCategoryAdapter listForCategoryAdapter;
    Context context;
    SwipeRefreshLayout swipeRefreshLayout;
    StaggeredGridLayoutManager manager;
    private int per_pager = 10;
    private int pager = 1;

    public GetListForCategories(List<Photo> photoList, RecyclerView recyclerView, ListForCategoryAdapter listForCategoryAdapter, Context context, SwipeRefreshLayout swipeRefreshLayout, StaggeredGridLayoutManager manager) {
        this.photoList = photoList;
        this.recyclerView = recyclerView;
        this.listForCategoryAdapter = listForCategoryAdapter;
        this.context = context;
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.manager = manager;
    }


    public void Refresh(String gallery_id) {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        photoList.clear();
                        per_pager = 10;
                        pager = 1;
                        NetworkingGetJsonListForCategory(gallery_id,per_pager,pager);
                        Loadmore(gallery_id);
                    }
                }, 1000);
            }
        });

    }

    public void Loadmore (String gallery_id){
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore(int pagee, int totalItemsCount, RecyclerView view) {
                swipeRefreshLayout.setRefreshing(true);
                pager +=1;
                NetworkingGetJsonListForCategory(gallery_id,per_pager,pager);
            }
        });
    }


    public void NetworkingGetJsonListForCategory(String gallery_id,int per_page,int page) {
        AndroidNetworking.post("https://www.flickr.com/services/rest/")
                .addBodyParameter("method", "flickr.galleries.getPhotos")
                .addBodyParameter("api_key", "85e29beb36172de1178cbeb10a08e0e7")
                .addBodyParameter("gallery_id",gallery_id+"")
                .addBodyParameter("extras", "views,media,path_alias,url_sq,url_t,url_s,url_q,url_m,url_n,url_z,url_c,url_l,url_o")
                .addBodyParameter("per_page",String.valueOf(per_page))
                .addBodyParameter("page", String.valueOf(page))
                .addBodyParameter("format", "json")
                .addBodyParameter("nojsoncallback", "1")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObject(ListForCategory.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        //tat su kien loading
                        swipeRefreshLayout.setRefreshing(false);
                        ListForCategory listForCategory = (ListForCategory) response;
                        List<Photo> photos = listForCategory.getPhotos().getPhoto();
                        photoList.addAll(photos);
                        listForCategoryAdapter.notifyDataSetChanged();
                        Log.e("List Size",photoList.size()+"");
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
}

