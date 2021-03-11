package com.example.wallpaperappbyducminh.activity.listimages;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.wallpaperappbyducminh.R;
import com.example.wallpaperappbyducminh.adapter.SearchListAdapter;
import com.example.wallpaperappbyducminh.getjson.GetDataSearch;
import com.example.wallpaperappbyducminh.model.search.Photo;
import com.example.wallpaperappbyducminh.setting.CustomItemDecorator;

import java.util.ArrayList;

public class ListSearchActivity extends AppCompatActivity {
    TextView tvSearch;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    SearchListAdapter searchListAdapter;
    GetDataSearch getDataSearch;
    ArrayList<Photo> photoList;
    StaggeredGridLayoutManager manager;
    ImageView imgExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_search);
        tvSearch = findViewById(R.id.tvTextSearch);
        swipeRefreshLayout = findViewById(R.id.swiperefreshSearch);
        recyclerView = findViewById(R.id.rclListSearch);
        imgExit = findViewById(R.id.ImgExit);
        photoList = new ArrayList<>();
        setAdapter();
        setStaggeredGridLayout();
        getLoader();
        imgExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void getLoader(){
        Intent intent = getIntent();
        String textSearch = intent.getStringExtra("textSearch");
        tvSearch.setText(textSearch);
        getDataSearch = new GetDataSearch(textSearch,photoList,recyclerView,searchListAdapter,this,swipeRefreshLayout,manager);
        getDataSearch.NetworkingGetDataSearch(textSearch,10,1);
        getDataSearch.SwipeRefresh();
        getDataSearch.Loadmore();
    }

    public void setAdapter(){
        searchListAdapter = new SearchListAdapter(this,recyclerView,photoList);
        recyclerView.setAdapter(searchListAdapter);
    }


    public void setStaggeredGridLayout(){
        manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new CustomItemDecorator(12));
        recyclerView.setLayoutManager(manager);
    }

}