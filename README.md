# Music Player

## glide
- 로드 라이브러리
- App Gradle에 추가
```java
compile 'com.github.bumptech.glide:glide:3.+'
```

- Gradle transform - circle
```java
compile 'jp.wasabeef:glide-transformations:2.0.2'
```
## permission _  저장소 권한

- Manifest에 추가
```java
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
```
[Premission Control](https://github.com/daaa08/MusicPlayer/blob/master/app/src/main/java/com/example/da08/musicplayer/PermissionControl.java)

## 
