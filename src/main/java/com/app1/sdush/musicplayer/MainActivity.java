package com.app1.sdush.musicplayer;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSION_REQUEST = 1;
    public static ArrayList<String> arrayList;
    ListView listView;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("Akad Bakad","onCreate");

        if(ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            }
        } else {
            doStuff();
        }

        CircleMenu circleMenu = (CircleMenu)findViewById(R.id.circle_menu);

        circleMenu.setMainMenu(Color.parseColor("#CDCDCD"),R.drawable.bnda,R.drawable.bnda)
                .addSubMenu(Color.parseColor("#258cff"),R.drawable.bnda)
                .addSubMenu(Color.parseColor("#258cff"),R.drawable.bnda)
                .addSubMenu(Color.parseColor("#258cff"),R.drawable.bnda)
                .addSubMenu(Color.parseColor("#938492"),R.drawable.bnda)
        .setOnMenuSelectedListener(new OnMenuSelectedListener() {
            @Override
            public void onMenuSelected(int i) {
                if(i==0){
                    //all_songs
                    Intent intent = new Intent(getApplicationContext(), AllSongs.class);
                    intent.putExtra("allsongs", arrayList);
                    startActivity(intent);
                }
                else if(i==1){
                    //Browse
                    Intent intent = new Intent(getApplicationContext(), Browse.class);
                    startActivity(intent);
                }
                else if(i==2){
                    //Browse
                    Intent intent = new Intent(getApplicationContext(), Player.class);
                    intent.putExtra("song",arrayList.get(0));
                    startActivity(intent);
                }
                else if(i==3){
                    //Browse
                    Intent intent = new Intent(getApplicationContext(), Playlist.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void doStuff(){
        //listView = (ListView) findViewById(R.id.listview);
        Log.i("Akad Bakad","doStuff");
        arrayList = new ArrayList<>();
        getMusic();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, arrayList);
        //listView.setAdapter(adapter);

        System.out.println("Hello");
    }

    public void getMusic(){
        Log.i("Akad Bakad","getMusic");
        ContentResolver contentResolver = getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(songUri, null, null, null ,null);

        if(songCursor != null && songCursor.moveToFirst()){
            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int songAlbum = songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            int songLocation = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);

            do{
                String currentTitle = songCursor.getString(songTitle);
                String currentArtist = songCursor.getString(songArtist);
                String currentAlbum = songCursor.getString(songAlbum);
                String currentLocation = songCursor.getString(songLocation);
                arrayList.add(currentTitle + ";;" + currentArtist + ";;" + currentAlbum + ";;" + currentLocation);
            } while(songCursor.moveToNext());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.i("Akad Bakad","onReqPer");
        switch (requestCode){
            case  MY_PERMISSION_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();

                        doStuff();
                    }
                } else {
                    Toast.makeText(this, "No permission granted!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }
        }
    }
}
