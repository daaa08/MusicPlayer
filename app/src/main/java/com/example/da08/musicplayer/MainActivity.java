package com.example.da08.musicplayer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.example.da08.musicplayer.dummy.DummyContent;

public class MainActivity extends AppCompatActivity implements ListFragment.OnListFragmentInteractionListener, PermissionControl.CallBack{


    FrameLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionControl.checkVersion(this);
    }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            PermissionControl.onResult(this, requestCode, grantResults);
        }

    public void init(){
        setView();
        setFragment(ListFragment.newInstance(1));  // 목록 프래그먼트
    }

    private void setView(){
        layout = (FrameLayout)findViewById(R.id.layout);
    }

    private void setFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction  transaction= manager.beginTransaction();
        transaction.add(R.id.layout,fragment);
        transaction.commit();
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}
