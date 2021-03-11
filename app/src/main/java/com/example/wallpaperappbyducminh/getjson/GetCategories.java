package com.example.wallpaperappbyducminh.getjson;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.wallpaperappbyducminh.adapter.CategoriesAdapter;
import com.example.wallpaperappbyducminh.model.categories.Galerryy;
import com.example.wallpaperappbyducminh.model.categories.Gallery;

import java.util.List;

public class GetCategories {
    List<Gallery> galleryList;
    RecyclerView recyclerView;
    CategoriesAdapter categoriesAdapter;
    Context context;

    public GetCategories(List<Gallery> galleryList, RecyclerView recyclerView, CategoriesAdapter categoriesAdapter, Context context) {
        this.galleryList = galleryList;
        this.recyclerView = recyclerView;
        this.categoriesAdapter = categoriesAdapter;
        this.context = context;
    }


    public void NetworkingGetJson (){
        AndroidNetworking.post("https://www.flickr.com/services/rest/")
                .addBodyParameter("method", "flickr.galleries.getList")
                .addBodyParameter("api_key", "85e29beb36172de1178cbeb10a08e0e7")
                .addBodyParameter("user_id", "187036618@N06")
                .addBodyParameter("per_page", "4")
                .addBodyParameter("page", "1")
                .addBodyParameter("primary_photo_extras", "views,media,path_alias,url_sq,url_t,url_s,url_q,url_m,url_n,url_z,url_c,url_l,url_o")
                .addBodyParameter("continuation", "0")
                .addBodyParameter("short_limit", "1")
                .addBodyParameter("format", "json")
                .addBodyParameter("nojsoncallback", "1")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                // lấy Json trả về theo id
                .getAsObject(Galerryy.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        Galerryy galerryy = (Galerryy) response;
                        List<Gallery> galleries = galerryy.getGalleries().getGallery();
                       galleryList.addAll(galleries);
                       categoriesAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }

}
