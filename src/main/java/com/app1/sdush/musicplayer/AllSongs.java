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
    ArrayList<String> arrayList;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Akad Bakad","onCreateAllSongs");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_songs);

        arrayList = new ArrayList<>();
        arrayList = MainActivity.arrayList;
        Collections.sort(arrayList);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, arrayList);
        listView = (ListView)findViewById(R.id.allsongs_listview);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Player.class);
                startActivity(intent);
            }
        });
    }
}
