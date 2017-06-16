package com.example.da08.musicplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

/**
 * Created by Da08 on 2017. 6. 16..
 */

public class Player {

    public static final int STOP = 0;
    public static final int PLAY = 1;
    public static final int PAUSE = 2;
    public static MediaPlayer player = null;
    public static int playerstatus = STOP;


    public static void play(Uri musicUri, Context context) {
        // 1 미디어 플레이어 사용하기
        if (player != null) {
            player.release();
        }
        player = MediaPlayer.create(context, musicUri);
        // 2 설정
        player.setLooping(false);  // 반복여부
        // 3 시작
        player.start();

        playerstatus = PLAY;

    }

    public static void pause() {
        player.pause();
        playerstatus = PAUSE;
    }

    public static void replay() {
        player.start();
        playerstatus = PLAY;
    }

}
