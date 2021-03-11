package com.example.wallpaperappbyducminh.activity.listimages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wallpaperappbyducminh.R;
import com.example.wallpaperappbyducminh.adapter.ListForCategoryAdapter;
import com.example.wallpaperappbyducminh.getjson.GetListForCategories;
import com.example.wallpaperappbyducminh.model.listforcategories.Photo;
import com.example.wallpaperappbyducminh.setting.CustomItemDecorator;

import java.util.ArrayList;
import java.util.List;

public class ListForCategoryActivity extends AppCompatActivity {
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    GetListForCategories getListForCategories;
    ListForCategoryAdapter listForCategoryAdapter;
    ArrayList<Photo> photoList;
    StaggeredGridLayoutManager manager;
    TextView TitleList;
    private int per_pager = 10;
    private int pager = 1;
    ImageView ImgExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_for_category);
        recyclerView = findViewById(R.id.rclRecyclerviewListForCategory);
        swipeRefreshLayout = findViewById(R.id.swiperefreshListForCategory);
        TitleList = findViewById(R.id.TitleList);
        ImgExit = findViewById(R.id.ImgExit);
        photoList = new ArrayList<>();
        setAdapter();
        setStaggeredGridLayout();
        getLoader();

        ImgExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void getLoader(){
        Intent intent = getIntent();
        String gallery_id = intent.getStringExtra("gallery_id");
        String title = intent.getStringExtra("title");
        TitleList.setText(title);
        getListForCategories = new GetListForCategories(photoList,recyclerView,listForCategoryAdapter,this,swipeRefreshLayout,manager);
        getListForCategories.NetworkingGetJsonListForCategory(gallery_id,per_pager,pager);
        getListForCategories.Refresh(gallery_id);
        getListForCategories.Loadmore(gallery_id);
    }

    public void setAdapter(){
        listForCategoryAdapter = new ListForCategoryAdapter(this,recyclerView,photoList);
        recyclerView.setAdapter(listForCategoryAdapter);
    }


    public void setStaggeredGridLayout(){
        manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new CustomItemDecorator(12));
        recyclerView.setLayoutManager(manager);
    }
}