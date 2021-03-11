package com.example.wallpaperappbyducminh.setting;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class CustomItemDecorator extends RecyclerView.ItemDecoration {

    int halfSpace;

    public CustomItemDecorator(int spanPadding){
        halfSpace = spanPadding;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
       if(parent.getPaddingLeft()!=halfSpace){
           parent.setPadding(halfSpace,halfSpace,halfSpace,halfSpace);
           parent.setClipToPadding(false);
       }
        outRect.top = halfSpace;
        outRect.right = halfSpace;
        outRect.left = halfSpace;
        outRect.bottom = halfSpace;

    }
}
