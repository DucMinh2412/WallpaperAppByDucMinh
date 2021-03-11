package com.example.wallpaperappbyducminh.activity.showimages;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.wallpaperappbyducminh.R;
import com.example.wallpaperappbyducminh.activity.CommentActivity;
import com.example.wallpaperappbyducminh.adapter.viewpager.ViewPagerSearchAdapter;
import com.example.wallpaperappbyducminh.model.search.Photo;
import com.example.wallpaperappbyducminh.setting.DownloadImage;
import com.example.wallpaperappbyducminh.setting.Zoomoutpagetransformer;
import com.example.wallpaperappbyducminh.setwallpaper.SetwallpaperSearch;
import com.example.wallpaperappbyducminh.social.SocialFavorite;
import com.example.wallpaperappbyducminh.social.SocialSearch;
import com.facebook.share.widget.ShareDialog;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SearchShowImagesActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1000;
    ViewPager viewPager;
    int position;
    ViewPagerSearchAdapter viewPagerSearchAdapter;
    FloatingActionButton floatingActionButton1,floatingActionButton2,floatingActionButton3;
    ImageView ImgSetWallpaper,ImgShare,ImgExitSearch,ImgComment;
    ArrayList<Photo> list;
    DownloadImage downloadImage;
    SocialSearch socialSearch;
    ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_show_images);
        setId();
        setViewPager();
        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Photo photo = list.get(viewPager.getCurrentItem());
                downloadImage.startDownload(photo.getId(),photo.getUrlC(),photo.getTitle());
            }
        });

        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Photo photo = list.get(viewPager.getCurrentItem());
                downloadImage.startDownload(photo.getId(),photo.getUrlL(),photo.getTitle());
            }
        });

        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Photo photo = list.get(viewPager.getCurrentItem());
                downloadImage.startDownload(photo.getId(),photo.getUrlO(),photo.getTitle());
            }
        });

        ImgExitSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ImgSetWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Photo photo = list.get(viewPager.getCurrentItem());
                SetwallpaperSearch setwallpaperSearch = new SetwallpaperSearch(SearchShowImagesActivity.this,photo);
                setwallpaperSearch.setWallpaperForPhone();
            }
        });

        ImgComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Photo photo = list.get(viewPager.getCurrentItem());
                Intent intent = new Intent(SearchShowImagesActivity.this, CommentActivity.class);
                intent.putExtra("idImage",photo.getId());
                startActivity(intent);
            }
        });

        ImgShare.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
                MenuBuilder menuBuilder =new MenuBuilder(SearchShowImagesActivity.this);
                MenuInflater inflater = new MenuInflater(SearchShowImagesActivity.this);
                inflater.inflate(R.menu.popupmenu, menuBuilder);
                MenuPopupHelper optionsMenu = new MenuPopupHelper(SearchShowImagesActivity.this, menuBuilder, view);
                optionsMenu.setForceShowIcon(true);

                // Set Item Click Listener
                menuBuilder.setCallback(new MenuBuilder.Callback() {
                    @Override
                    public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.opt1:
                                Photo photo = list.get(viewPager.getCurrentItem());
                                socialSearch = new SocialSearch(shareDialog,SearchShowImagesActivity.this,downloadImage,photo);
                                socialSearch.ShareFacebook();
                                return true;

                            case R.id.opt2:
                                new SweetAlertDialog(SearchShowImagesActivity.this, SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText("Are you sure?")
                                        .setContentText("You must download the photo to share it !")
                                        .setConfirmText("Yes")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                Photo photos = list.get(viewPager.getCurrentItem());
                                                socialSearch = new SocialSearch(shareDialog,SearchShowImagesActivity.this,downloadImage,photos);
                                                socialSearch.ShareInstagram();
                                                sDialog.dismissWithAnimation();
                                            }
                                        })
                                        .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                sDialog.dismissWithAnimation();
                                            }
                                        })
                                        .show();

                                return true;

                            default:
                                return false;
                        }
                    }

                    @Override
                    public void onMenuModeChange(MenuBuilder menu) {}
                });


                // Display the menu
                optionsMenu.show();
            }
        });

    }

    public void setViewPager(){
        Intent intent = getIntent();
        list = (ArrayList<Photo>) intent.getSerializableExtra("List");
        position = intent.getIntExtra("Position", 0);
        viewPagerSearchAdapter = new ViewPagerSearchAdapter(list, SearchShowImagesActivity.this);
        viewPager.setAdapter(viewPagerSearchAdapter);
        viewPager.setCurrentItem(position, true);
        viewPager.setPageTransformer(true, new Zoomoutpagetransformer());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();

                else
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            break;
        }
    }

    public void setId(){
        viewPager = findViewById(R.id.ViewPager);
        floatingActionButton1 = findViewById(R.id.fab_action1);
        floatingActionButton2 = findViewById(R.id.fab_action2);
        floatingActionButton3 = findViewById(R.id.fab_action3);
        ImgSetWallpaper = findViewById(R.id.ImgSetWallpaper);
        ImgShare = findViewById(R.id.ImgShare);
        ImgExitSearch = findViewById(R.id.ImgExitSearch);
        downloadImage = new DownloadImage(SearchShowImagesActivity.this,this);
        shareDialog = new ShareDialog(this);
        ImgComment  = findViewById(R.id.ImgComment);
    }
}