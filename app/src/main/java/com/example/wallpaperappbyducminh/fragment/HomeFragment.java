package com.example.wallpaperappbyducminh.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.wallpaperappbyducminh.R;
import com.example.wallpaperappbyducminh.adapter.CategoriesAdapter;
import com.example.wallpaperappbyducminh.getjson.GetCategories;
import com.example.wallpaperappbyducminh.model.categories.Galerryy;
import com.example.wallpaperappbyducminh.model.categories.Gallery;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    GetCategories getCategories;
    CategoriesAdapter categoriesAdapter;
    List<Gallery> galleryList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.rclCategoriesRecyclerview);
        galleryList = new ArrayList<>();
       SetAdapter();
       Loader();
       return view;
    }

    public void Loader(){
            getCategories = new GetCategories(galleryList, recyclerView, categoriesAdapter, getContext());
            getCategories.NetworkingGetJson();
    }

    public void SetAdapter(){
        categoriesAdapter = new CategoriesAdapter(getContext(),recyclerView,galleryList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(categoriesAdapter);
    }

}