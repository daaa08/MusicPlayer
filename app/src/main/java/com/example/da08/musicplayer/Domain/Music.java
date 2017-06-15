package com.example.da08.musicplayer.Domain;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Da08 on 2017. 6. 14..
 */

public class Music {

    private static Music intance = null;
    private Set<Item> items = null;
    public Music(){
        items = new HashSet<>();
    }

    private Music getInstance(){
        if(intance == null)
            intance = new Music();

        return intance;
    }

    public  Set<Item> getItems(){
        return items;
    }

    // 음악 데이터를 꺼낸다음 리스트 저장소에 담아 둠
    public void loader(Context context){
        items.clear();  // 데이터가 계속 쌓이는 것을 방지
        ContentResolver resolver = context.getContentResolver();

        // 1 테이블 명 정의
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        // 2 가져올 컬럼 명 정의

        String pro[] = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ALBUM_ID,  // 앨범 아트 조회
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST
        };
        // 3 쿼리
        Cursor cursor = resolver.query(uri,pro,null,null,null);
        if(cursor != null){
            while(cursor.moveToNext()){
                Item item = new Item();
                item.id = getValue(cursor,pro[0]);
                item.albumId = getValue(cursor,pro[1]);
                item.title = getValue(cursor,pro[2]);
                item.artist = getValue(cursor,pro[3]);

                item.musicUri = makeMusicUri(item.id);
                item.albumArt = makeAlbumUri(item.albumId);


                // 데이터 담기
                items.add(item);

            }
        }
    }

    private String getValue(Cursor cursor, String name){
        int index = cursor.getColumnIndex(name);
        return cursor.getString(index);
    }

    // set이 정상적으로 중복값을 허용하지 않도록 어떤 함수를 오버라이듷해서 구현해라
    public class Item{
        String id;
        String albumId;
        String title;
        String artist;

        Uri musicUri;
        Uri albumArt;

        @Override
        public boolean equals(Object obj) {
            
            return super.equals(obj);
        }
    }

    private Uri makeMusicUri(String musicid){
        Uri contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        return Uri.withAppendedPath(contentUri, musicid);
    }

    private Uri makeAlbumUri(String albumid){
        String albumUri = "content://media/external/audio/albumart/";
        return Uri.parse(albumUri + albumid);

    }
}
