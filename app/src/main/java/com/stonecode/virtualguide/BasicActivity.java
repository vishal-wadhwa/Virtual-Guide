package com.stonecode.virtualguide;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.io.IOException;

public class BasicActivity extends AppCompatActivity {

    private static final String TAG = "BasicActivity";
    int state=0;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        String url = "http://10.0.0.30/Virtual-Guide/virtual-guide/data/isa_khan_niazi.mp3";

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(final MediaPlayer mediaPlayer) {
                fab.setImageResource(android.R.drawable.ic_media_play);
                Log.d(TAG, "onPrepared: ");
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                                .setAction("Action", null).show();
                        if(state==0) {
                            fab.setImageResource(android.R.drawable.ic_media_pause);
                            mediaPlayer.start();
                            state=1;
                        }
                        else{
                            fab.setImageResource(android.R.drawable.ic_media_play);
                            state=0;
                            mediaPlayer.pause();
                        }
                    }
                });
            }
        });
        mediaPlayer.prepareAsync();



    }

}
