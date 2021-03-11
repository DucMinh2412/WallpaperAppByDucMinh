package com.example.wallpaperappbyducminh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.wallpaperappbyducminh.R;
import com.example.wallpaperappbyducminh.fragment.SearchFragment;
import com.example.wallpaperappbyducminh.model.suggestlist.TitleSuggest;

import java.util.List;

public class SuggestListAdapter extends RecyclerView.Adapter<SuggestListAdapter.SuggestListHolder> {
    public Context context;
    public RecyclerView recyclerView;
    public List<TitleSuggest> titleSuggestList;
    public SearchView searchView;

    public SuggestListAdapter(Context context, RecyclerView recyclerView, List<TitleSuggest> titleSuggestList, SearchView searchView) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.titleSuggestList = titleSuggestList;
        this.searchView = searchView;
    }

    @NonNull
    @Override
    public SuggestListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(context).
                        inflate(R.layout.suggestlist, parent, false);

        SuggestListHolder suggestListHolder = new SuggestListHolder(view);

        return suggestListHolder;


    }

    @Override
    public void onBindViewHolder(@NonNull final SuggestListHolder suggestListHolder, final int position) {
        final TitleSuggest titleSuggest = titleSuggestList.get(position);
        suggestListHolder.textView.setText(titleSuggest.getTitle()+"");

        suggestListHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               searchView.setQuery(titleSuggest.getTitle(),false);
            }
        });

    }

    @Override
    public int getItemCount() {
        return titleSuggestList.size();
    }


    public class SuggestListHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public SuggestListHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.titleSuggestList);
        }
    }
}