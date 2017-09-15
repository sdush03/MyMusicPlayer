package com.app1.sdush.musicplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Collections;

public class AllSongs extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList, songsList;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Akad Bakad","onCreateAllSongs");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_songs);

        arrayList = new ArrayList<>();
        songsList = new ArrayList<>();
        arrayList = MainActivity.arrayList;
        Collections.sort(arrayList);

        for(int i=0 ; i<arrayList.size() ; i++){
            String[] str = arrayList.get(i).split(";;");
            songsList.add(str[0]);
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, songsList);
        listView = (ListView)findViewById(R.id.allsongs_listview);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("Akad1","Item clicked: " + arrayList.get(position));
                Intent intent = new Intent(getApplicationContext(), Player.class);
                intent.putExtra("song", arrayList.get(position));
                startActivity(intent);
            }
        });
    }
}
