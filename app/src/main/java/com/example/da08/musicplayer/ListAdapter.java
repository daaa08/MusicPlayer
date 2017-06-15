package com.example.da08.musicplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.da08.musicplayer.Domain.Music;
import com.example.da08.musicplayer.ListFragment.OnListFragmentInteractionListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private final OnListFragmentInteractionListener mListener;

    private Context context = null;
    // 데이터 저장소
    private final List<Music.Item> datas;  // 음악 data

    public ListAdapter(Set<Music.Item> items, OnListFragmentInteractionListener listener) {
        mListener = listener;

        // set에서 data꺼내 사용을 하는데 index를 필요로 하는경우 array에 담는다
        datas = new ArrayList<>(items);  // 공간 생성

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(context == null)
            context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // datas 저장소에 들어가있는 뮤직아이템 하나를 꺼낸다
        //Music.Item item = datas.get(position);

        holder.position = position;
        holder.mIdView.setText(datas.get(position).id);
        holder.mContentView.setText(datas.get(position).title);

        Glide.with(context).load(datas.get(position).albumArt).placeholder(R.mipmap.icon).bitmapTransform(new CropCircleTransformation(context))
                .into(holder.imgAlbum);  // 로드할 대상, 이미지를 출력할 대상

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    static final int STOP = 0;
    static final int PLAY = 1;
    static final int PAUSE = 2;
    MediaPlayer player = null;
    int playerstatus = STOP;


    public void play(int position){
        // 1 미디어 플레이어 사용하기
        Uri musicUri = datas.get(position).musicUri;
        if(player != null){
            player.release();
        }
        player = MediaPlayer.create(context, musicUri);
        // 2 설정
        player.setLooping(false);  // 반복여부
        // 3 시작
        player.start();

        playerstatus = PLAY;

    }

    public void pause(){
        player.pause();
        playerstatus = PAUSE;
    }

    public void replay(){
        player.start();
        playerstatus = PLAY;
    }

    public void goDetail(int position){

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public int position;
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final ImageView imgAlbum;
        public final ImageButton btnPas;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            imgAlbum = (ImageView) view.findViewById(R.id.imgAlbum);
            btnPas = (ImageButton) view.findViewById(R.id.btnPas);


            // 플레이 버튼 노출
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    play(position);
                    btnPas.setImageResource(android.R.drawable.ic_media_pause);
                    btnPas.setVisibility(View.VISIBLE);
                }
            });

            // 상세보기로 이동  > 뷰페이저로 이동
            mView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    goDetail(position);

                    return true; // 롱 클릭 후 온클릭이 실행되지 않도록 함
                }
            });

            btnPas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch(playerstatus){
                        case PLAY:
                            pause();
                            // pause 가 클릭되면 이미지 모양이 play 로 바뀐다.
                            btnPas.setImageResource(android.R.drawable.ic_media_play);
                            break;
                        case PAUSE:
                            replay();
                            btnPas.setImageResource(android.R.drawable.ic_media_pause);
                            break;
                    }
                }
            });
        }

//        @Override
//        public String toString() {
//            return super.toString() + " '" + mContentView.getText() + "'";
//        }
    }
}
