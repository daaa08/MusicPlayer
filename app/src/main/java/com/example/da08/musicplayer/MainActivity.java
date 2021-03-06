package com.example.da08.musicplayer;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.example.da08.musicplayer.Util.PermissionControl;

public class MainActivity extends AppCompatActivity implements ListFragment.OnListFragmentInteractionListener
        , PermissionControl.CallBack,DetailFragment.PlayerInterface{


    FrameLayout layout;
    ListFragment list;
    DetailFragment detail;

    Intent service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        service = new Intent(this, PlayerService.class);

        // 볼륨 조절 버튼으로 미디어 음량만 조절하기 위한 설정
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        list = ListFragment.newInstance(1);
        detail = DetailFragment.newInstance();

        PermissionControl.checkVersion(this);
    }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            PermissionControl.onResult(this, requestCode, grantResults);
        }

    @Override
    public void init(){
        setViews();
        setFragment(list); // 목록 프래그먼트
    }

    private void setViews(){
        layout = (FrameLayout) findViewById(R.id.layout);
    }

    private void setFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.layout, fragment);
        transaction.commit();
    }

    private void addFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    // Fragment 를 통해 Adapter 까지 interface 를 전달하고
    // Adapter 에서 interface 를 직접호출해서 사용한다.
    @Override
    public void goDetailInteraction(int position) {
        detail.setPosition(position);
        addFragment(detail);
    }

    @Override
    protected void onDestroy() {
        detail.setDestroy();
        super.onDestroy();
    }

    @Override
    public void initPlayer(){

    }
    @Override
    public void playPlayer(){
        // 1. 서비스를 생성하고
        // 서비스에 명령어를 담아서 넘긴다
        service.setAction(Const.Action.PLAY);
        startService(service);
    }
    @Override
    public void stopPlayer(){
        service.setAction(Const.Action.STOP);
        startService(service);
    }
    @Override
    public void pausePlayer(){
        service.setAction(Const.Action.PAUSE);
        startService(service);
    }
}