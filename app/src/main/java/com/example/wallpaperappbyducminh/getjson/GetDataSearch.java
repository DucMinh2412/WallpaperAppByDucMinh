package com.example.wallpaperappbyducminh.getjson;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.wallpaperappbyducminh.adapter.SearchListAdapter;
import com.example.wallpaperappbyducminh.model.search.Photo;
import com.example.wallpaperappbyducminh.model.search.Search;
import com.example.wallpaperappbyducminh.setting.EndlessRecyclerViewScrollListener;

import java.util.List;
public class GetDataSearch {
    String text;
    List<Photo> photoList;
    RecyclerView recyclerView;
    SearchListAdapter searchListAdapter;
    Context context;
    SwipeRefreshLayout swipeRefreshLayout;
    StaggeredGridLayoutManager manager;
    private int per_pager = 10;
    private int pager = 1;

    public GetDataSearch(String text, List<Photo> photoList, RecyclerView recyclerView, SearchListAdapter searchListAdapter, Context context, SwipeRefreshLayout swipeRefreshLayout, StaggeredGridLayoutManager manager) {
        this.text = text;
        this.photoList = photoList;
        this.recyclerView = recyclerView;
        this.searchListAdapter = searchListAdapter;
        this.context = context;
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.manager = manager;
    }

    public void SwipeRefresh(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    public void Loadmore (){
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore(int pages, int totalItemsCount, RecyclerView view) {
                swipeRefreshLayout.setRefreshing(true);
                pager +=1;
                NetworkingGetDataSearch(text,per_pager,pager);
            }
        });
    }


    public void NetworkingGetDataSearch(String text,int per_page,int page) {
        AndroidNetworking.post("https://www.flickr.com/services/rest/")
                .addBodyParameter("method", "flickr.photos.search")
                .addBodyParameter("api_key", "85e29beb36172de1178cbeb10a08e0e7")
                .addBodyParameter("text","" + text )
                .addBodyParameter("content_type","4")
                .addBodyParameter("media","photos")
                .addBodyParameter("extras", "views,media,path_alias,url_sq,url_t,url_s,url_q,url_m,url_n,url_z,url_c,url_l,url_o")
                .addBodyParameter("per_page",String.valueOf(per_page))
                .addBodyParameter("page", String.valueOf(page))
                .addBodyParameter("format", "json")
                .addBodyParameter("nojsoncallback", "1")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObject(Search.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        swipeRefreshLayout.setRefreshing(false);
                        Search search =  (Search) response;
                        List<Photo> photos = search.getPhotos().getPhoto();
                        photoList.addAll(photos);
                        searchListAdapter.notifyDataSetChanged();
                        Log.e("List to size",photoList.size()+"");
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

}
