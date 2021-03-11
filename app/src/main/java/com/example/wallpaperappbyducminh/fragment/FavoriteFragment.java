package com.example.wallpaperappbyducminh.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.wallpaperappbyducminh.R;
import com.example.wallpaperappbyducminh.adapter.FavoriteAdapter;
import com.example.wallpaperappbyducminh.getjson.GetimagesFavorite;
import com.example.wallpaperappbyducminh.model.favorite.Favorites;
import com.example.wallpaperappbyducminh.model.favorite.Photo;
import com.example.wallpaperappbyducminh.setting.CustomItemDecorator;
import com.example.wallpaperappbyducminh.setting.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.List;


public class FavoriteFragment extends Fragment {
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    GetimagesFavorite getimagesFavorite;
    FavoriteAdapter favoriteAdapter;
    ArrayList<Photo> photoList;
    StaggeredGridLayoutManager manager;
    private int per_pager = 10;
    private int pager = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        recyclerView = view.findViewById(R.id.rclRecyclerview);
        swipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        photoList = new ArrayList<>();
        setAdapter();
        setStaggeredGridLayout();
        getLoader();
       return view;
    }

    public void getLoader(){
            getimagesFavorite = new GetimagesFavorite(photoList, recyclerView, favoriteAdapter, getContext(), swipeRefreshLayout, manager);
            getimagesFavorite.NetworkingGetJsonFavorite(per_pager, pager);
            getimagesFavorite.Refresh();
            getimagesFavorite.Loadmore();
    }

    public void setAdapter(){
        favoriteAdapter = new FavoriteAdapter(getContext(),recyclerView,photoList);
        recyclerView.setAdapter(favoriteAdapter);
    }


    public void setStaggeredGridLayout(){
        manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new CustomItemDecorator(12));
        recyclerView.setLayoutManager(manager);
    }



}