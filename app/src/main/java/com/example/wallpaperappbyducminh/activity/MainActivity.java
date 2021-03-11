package com.example.wallpaperappbyducminh.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.example.wallpaperappbyducminh.R;
import com.example.wallpaperappbyducminh.fragment.FavoriteFragment;
import com.example.wallpaperappbyducminh.fragment.HomeFragment;
import com.example.wallpaperappbyducminh.fragment.SearchFragment;
import com.example.wallpaperappbyducminh.setting.BottomNavigationBehavior;
import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MainActivity extends AppCompatActivity {
    BubbleNavigationLinearView bubbleNavigationLinearView;
    // Class cho phép thêm, xóa, sửa các fragment
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bubbleNavigationLinearView = findViewById(R.id.bottom_navigation_bar);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,new HomeFragment());
        fragmentTransaction.commit();
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bubbleNavigationLinearView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

      bubbleNavigationLinearView.setNavigationChangeListener(new BubbleNavigationChangeListener() {
          @Override
          public void onNavigationChanged(View view, int position) {
              switch (position){
                  case 0 :
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                // thay thế 1 fragment cũ bằng 1 fragment mới vào View
                fragmentTransaction.replace(R.id.fragment_container,new HomeFragment());
                fragmentTransaction.commit();
                break;

                  case 1 :
                      fragmentTransaction = getSupportFragmentManager().beginTransaction();
                      // thay thế 1 fragment cũ bằng 1 fragment mới vào View
                      fragmentTransaction.replace(R.id.fragment_container,new FavoriteFragment());
                      fragmentTransaction.commit();
                      break;

                  case 2 :
                      fragmentTransaction = getSupportFragmentManager().beginTransaction();
                      // thay thế 1 fragment cũ bằng 1 fragment mới vào View
                      fragmentTransaction.replace(R.id.fragment_container,new SearchFragment());
                      fragmentTransaction.commit();
                      break;
              }
          }
      });

     // printKeyHash();
    }

//  private void printKeyHash(){
//        try{
//            PackageInfo packageInfo = getPackageManager().getPackageInfo("com.example.wallpaperappbyducminh",
//                    PackageManager.GET_SIGNATURES);
//
//            for(Signature signature : packageInfo.signatures){
//                MessageDigest messageDigest = MessageDigest.getInstance("SHA");
//                messageDigest.update(signature.toByteArray());
//                Log.e("KeyHash", Base64.encodeToString(messageDigest.digest(),Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }




}