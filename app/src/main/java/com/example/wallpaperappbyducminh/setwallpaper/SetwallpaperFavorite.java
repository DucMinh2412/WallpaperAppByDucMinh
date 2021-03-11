package com.example.wallpaperappbyducminh.setwallpaper;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.example.wallpaperappbyducminh.activity.showimages.FavoriteShowImagesActivity;
import com.example.wallpaperappbyducminh.model.favorite.Photo;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SetwallpaperFavorite {
    Context context;
    Photo photo;

    public SetwallpaperFavorite(Context context, Photo photo) {
        this.context = context;
        this.photo = photo;
    }

    Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
                new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Success !")
                        .setContentText("Set wallpaper success !")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                DisplayMetrics metrics = new DisplayMetrics();
                                ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
                                int height = metrics.heightPixels;
                                int width = metrics.widthPixels;
                                Bitmap bitmaps = Bitmap.createScaledBitmap(bitmap, width, height, true);
                                try {
                                    wallpaperManager.setBitmap(bitmaps);
                                    sweetAlertDialog.dismissWithAnimation();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .show();
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
        }

    };

    public void setWallpaperForPhone(){
        Picasso.with(context)
                .load(photo.getUrlZ())
                .into(target);
    }
}