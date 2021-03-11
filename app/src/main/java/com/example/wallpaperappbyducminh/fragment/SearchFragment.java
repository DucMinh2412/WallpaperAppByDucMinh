package com.example.wallpaperappbyducminh.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.wallpaperappbyducminh.R;
import com.example.wallpaperappbyducminh.activity.listimages.ListSearchActivity;
import com.example.wallpaperappbyducminh.adapter.SuggestListAdapter;
import com.example.wallpaperappbyducminh.model.suggestlist.TitleSuggest;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
  private RecyclerView rclSuggestList;
  private List<TitleSuggest> titleSuggestList;
  private SuggestListAdapter suggestListAdapter;
  public SearchView searchView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        rclSuggestList = view.findViewById(R.id.rclSuggestList);
        searchView = view.findViewById(R.id.svSearch);
        addItemSuggestList();
        Search();
        return view;
    }

    public void Search(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                searchView.clearFocus();
                Intent intent = new Intent(getContext(), ListSearchActivity.class);
                intent.putExtra("textSearch",text);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                return false;
            }
        });
    }

    public void addItemSuggestList(){
        titleSuggestList = new ArrayList<>();
        if(titleSuggestList.size()==0) {
            titleSuggestList.add(new TitleSuggest("Cars"));
            titleSuggestList.add(new TitleSuggest("Scenery"));
            titleSuggestList.add(new TitleSuggest("Gái xinh việt nam"));
            titleSuggestList.add(new TitleSuggest("Sport"));
        }
        rclSuggestList.setHasFixedSize(true);
        rclSuggestList.setLayoutManager(new LinearLayoutManager(getContext()));
        suggestListAdapter = new SuggestListAdapter(getContext(),rclSuggestList,titleSuggestList,searchView);
        rclSuggestList.setAdapter(suggestListAdapter);
    }

}