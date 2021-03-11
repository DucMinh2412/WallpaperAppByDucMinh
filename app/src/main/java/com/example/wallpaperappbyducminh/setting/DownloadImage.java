package com.example.wallpaperappbyducminh.setting;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.androidnetworking.interfaces.DownloadProgressListener;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.wallpaperappbyducminh.R;
import com.example.wallpaperappbyducminh.activity.MainActivity;
import com.example.wallpaperappbyducminh.activity.WelcomeActivity;

import java.io.File;
import java.sql.Time;
import java.util.Timer;

public class DownloadImage  {
    Activity activity;
    private static final int PERMISSION_REQUEST_CODE = 1000;
    Context context;
    ProgressDialog progressDialog;
    long downloadId;
    DownloadManager manager;

    public DownloadImage(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
    }

    public void startDownload(String id, String url, String title) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED) {
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                activity.requestPermissions(permissions, PERMISSION_REQUEST_CODE);
            }

            else {
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                //Allow type of network to download files
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                        DownloadManager.Request.NETWORK_MOBILE);
                request.setTitle("Download WallpaperGirl_" + title);
                request.setDescription("Wallpaper Girl");
                request.allowScanningByMediaScanner();
                request.setMimeType("image/jpg");
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                //get current time for image file
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, id + ".jpg");

                //get download service and enqueue file
                manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                downloadId = manager.enqueue(request);
            }

        } else {
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            //Allow type of network to download files
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                    DownloadManager.Request.NETWORK_MOBILE);
            request.setTitle("Download WallpaperGirl_" + title);
            request.setDescription("Wallpaper Girl");
            request.allowScanningByMediaScanner();
            request.setMimeType("image/jpg");
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            //get current time for image file
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, id + ".jpg");

            //get download service and enqueue file
            manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            downloadId = manager.enqueue(request);
        }

        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Đang xử lý, vui lòng đợi...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgress(0);
        progressDialog.setMax(100);
        progressDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
              boolean downloading = true;
                try {
                    while (downloading) {
                        // tìm kiếm tệp đang tải
                        DownloadManager.Query q = new DownloadManager.Query();
                        q.setFilterById(downloadId);
                        Cursor cursor = manager.query(q);
                        cursor.moveToFirst();

                        int bytes_downloaded = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                        int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));

                        // 100l = tạo bộ nhớ 8 bit
                        // % hoàn thành = (số công việc đã hoàn thành) * 100 / tổng số công việc
                        final int dl_progress = (int) ((bytes_downloaded * 100) / bytes_total);

                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.setProgress(dl_progress);
                            }
                        });

                        Thread.sleep(1000);
                        if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                            downloading = false;
                            progressDialog.dismiss();
                        }

                        cursor.close();
                    }
                } catch (Exception e) {

                }
            }
        }).start();

    }

}
