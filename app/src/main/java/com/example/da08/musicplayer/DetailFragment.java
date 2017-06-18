package com.example.da08.musicplayer;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.da08.musicplayer.Domain.Music;

import java.util.List;


public class DetailFragment extends Fragment {
    public static final int CHANGE_SEEKBAR = 99;
    static final String ARG1 = "position";
    private int position = -1;

    ViewHolder viewHolder = null;

    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case CHANGE_SEEKBAR:
                    viewHolder.setSeekBarPosition(msg.arg1);
                    break;
            }
        }
    };

    public DetailFragment() {                // 필수로 있어야하는 부분
        // Required empty public constructor
    }

    public static DetailFragment newInstance(int position) {
        DetailFragment fragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG1,position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_pager, container, false);
        Bundle bundle = getArguments();
        position = bundle.getInt(ARG1);
        viewHolder = new ViewHolder(view, position);
        return view;
    }

    public List<Music.Item> getDatas(){
        Music music = Music.getInstance();
        music.loader(getContext());

        return music.getItems();
    }

    // viewPager의 View
    public class ViewHolder implements View.OnClickListener {
        ViewPager viewPager;
        RelativeLayout layoutControl;
        ImageButton btnPre, btnStop, btnNext;
        SeekBar seekBar6;
        TextView txtStart, txtEnd;

        public ViewHolder(View view, int position){
            viewPager = (ViewPager)view.findViewById(R.id.viewPager);
            layoutControl = (RelativeLayout) view.findViewById(R.id.layoutControl);
            btnPre = (ImageButton) view.findViewById(R.id.btnPre);
            btnStop = (ImageButton) view.findViewById(R.id.btnStop);
            btnNext = (ImageButton) view.findViewById(R.id.btnNext);
            seekBar6 = (SeekBar) view.findViewById(R.id.seekBar6);
            txtStart = (TextView) view.findViewById(R.id.txtStart);
            txtEnd = (TextView) view.findViewById(R.id.txtEnd);
            setOnClickListener();
            setViewPager(position);
        }
        private void setOnClickListener(){
            btnNext.setOnClickListener(this);
            btnStop.setOnClickListener(this);
            btnPre.setOnClickListener(this);
        }

        private void setViewPager(int position){
            DetailAdapter adapter = new DetailAdapter(getDatas());
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(position);
        }


        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btnPre:


                    break;
                case R.id.btnStop:
                    Uri musicUri = getDatas().get(position).musicUri;
                    Player.play(musicUri , v.getContext());
                    // seekBar 의 최대길이를 지정
                    Log.d("DetailFragment","duration="+Player.getDuration());
                    seekBar6.setMax(Player.getDuration());

                    // seekBar를 변경해주는 thread
                    new SeekBarThread(handler).start();
                    break;
                case R.id.btnNext:
                    break;
            }
        }

        public void setSeekBarPosition(int current){
            seekBar6.setProgress(current);
        }
    }
}

class SeekBarThread extends Thread {
    Handler handler;
    boolean runFlag = true;

    public SeekBarThread(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {

        while (runFlag) {
            // 매초마다 음원의 실행영역을 가져와서
            int current = Player.getCurrent();
            // seekbar 의 위치를 변경해준다.
            Message msg = new Message();
            msg.what = DetailFragment.CHANGE_SEEKBAR;
            msg.arg1 = current;
            handler.sendMessage(msg);

            // 플레이 시간이 끝나면 thread 를 종료한다.
            if (current >= Player.getDuration()) {
                runFlag = false;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}