package com.app1.sdush.musicplayer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Player extends AppCompatActivity{

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player);

        textView = (TextView)findViewById(R.id.faltu);
        textView.setText(getIntent().getExtras().getString("song"));
    }
}
