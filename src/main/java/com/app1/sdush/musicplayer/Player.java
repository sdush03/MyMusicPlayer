package com.app1.sdush.musicplayer;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class Player extends AppCompatActivity{

    TextView textView;
    SeekBar seekBar;
    Handler handler;
    Runnable runnable;
    MediaPlayer mediaPlayer = new MediaPlayer();
    Button playPause;
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player);

        textView = (TextView)findViewById(R.id.faltu);
        seekBar = (SeekBar)findViewById(R.id.player_seekBar);
        playPause = (Button)findViewById(R.id.play_pause);

        handler = new Handler();

        String[] atr = getIntent().getExtras().get("song").toString().split(";;");
        String songTitle = atr[0];
        String songArtist = atr[1];
        String songAlbum = atr[2];
        String songLocation = atr[3];
        textView.setText(songLocation);

        Uri myUri = Uri.parse(songLocation); // initialize Uri here
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(getApplicationContext(), myUri);
            mediaPlayer.prepare();
            mediaPlayer.start();
            seekBar.setMax(mediaPlayer.getDuration());
            playCycle();
            textView.setText("Started");
        } catch(Exception e){ textView.setText("Exception: " + e);}

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser)
                    mediaPlayer.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                i++;
                if(i%2==0){
                    mediaPlayer.start();
                }
                else{
                    mediaPlayer.pause();
                }
            }
        });
    }

    public void playCycle(){
        seekBar.setProgress(mediaPlayer.getCurrentPosition());

        if(mediaPlayer.isPlaying()){
            runnable = new Runnable() {
                @Override
                public void run() {
                    playCycle();
                }
            };
        }
        else{
            textView.setText("Stopped");
        }
        handler.postDelayed(runnable, 1000);
    }

}
