package com.example.wallpaperappbyducminh.social;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.example.wallpaperappbyducminh.model.favorite.Photo;
import com.example.wallpaperappbyducminh.setting.DownloadImage;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;

public class SocialFavorite {
    ShareDialog shareDialog;
    Context context;
    DownloadImage downloadImage;
    Photo photo;

    public SocialFavorite(ShareDialog shareDialog, Context context, DownloadImage downloadImage, Photo photo) {
        this.shareDialog = shareDialog;
        this.context = context;
        this.downloadImage = downloadImage;
        this.photo = photo;
    }

    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            SharePhoto sharePhoto = new SharePhoto.Builder()
                    .setBitmap(bitmap)
                    .build();

            if(ShareDialog.canShow(SharePhotoContent.class)){
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(sharePhoto)
                        .build();

                shareDialog.show(content);
            }
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    public void ShareInstagram(){
                downloadImage.startDownload(photo.getId(),photo.getUrlO(),photo.getTitle());
                String filename = photo.getId() + ".jpg";
                String path = "/storage/emulated/0/Download/" + filename;
                File f = new File(path);
                Uri imageUri = Uri.fromFile(f);

                Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("image/*");
                shareIntent.putExtra(Intent.EXTRA_STREAM,imageUri); // set uri
                shareIntent.setPackage("com.instagram.android");
                context.startActivity(Intent.createChooser(shareIntent, "Share to"));
    }

    public void ShareFacebook(){
        Picasso.with(context).load(photo.getUrlZ()).into(target);
    }

}
