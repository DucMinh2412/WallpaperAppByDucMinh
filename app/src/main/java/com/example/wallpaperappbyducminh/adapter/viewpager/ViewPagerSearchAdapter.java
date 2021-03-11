package com.example.wallpaperappbyducminh.adapter.viewpager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.example.wallpaperappbyducminh.R;
import com.example.wallpaperappbyducminh.model.search.Photo;

import java.util.List;

import uk.co.senab.photoview.PhotoView;

public class ViewPagerSearchAdapter extends PagerAdapter {
    List<Photo> photoList;
    Context context;

    public ViewPagerSearchAdapter(List<Photo> photoList, Context context) {
        this.photoList = photoList;
        this.context = context;
    }

    // add view
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slideshow_images,null);
        PhotoView image = view.findViewById(R.id.imgFullSize);
        Glide.with(context).load(photoList.get(position).getUrlZ()).priority(Priority.NORMAL).override(600,800).into(image);
        ViewPager viewPager = (ViewPager)container;
        viewPager.addView(view,0);
        return view;
    }

    // chỉ giữ tối đa 3 view trong bộ nhớ memory, 1 view hiện tại đc hiển thị visible, 1 view bên trái và 1 view bên phải.
    // Trong quá trình scroll để di chuyển các page, các page bị đi ra ngoài sẽ bị destroy trong phương thức
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager viewPager = (ViewPager)container;
        View view = (View)object;
        viewPager.removeView(view);
    }

    @Override
    public int getCount() {
        return photoList.size();
    }


    //phương thức kiểm tra xem các đối tượng đc trả về bởi instantiateItem()
    // đc liên kết với View được cung cấp.
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
