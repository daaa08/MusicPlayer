package com.example.da08.musicplayer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.da08.musicplayer.Domain.Music;

import java.util.Set;


public class DetailFragment extends Fragment {
    static final String ARG1 = "position";
    ViewHolder viewHolder = null;
    private int position = -1;

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

    public Set<Music.Item> getDatas(){
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
                    getDatas();
                    break;
                case R.id.btnStop:
                    break;
                case R.id.btnNext:
                    break;
            }
        }
    }
}
