package com.stonecode.virtualguide;

import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class BasicActivity extends AppCompatActivity {

    private static final String TAG = "BasicActivity";
    int state=0;
    MediaPlayer mediaPlayer;
    TextView tvtv;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        img = (ImageView) findViewById(R.id.basic_title_image);
        tvtv= (TextView) findViewById(R.id.basic_tv);

        Intent i = getIntent();
        String name = i.getStringExtra("name");
        setTitle(name);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        String url = "http://10.0.0.30/Virtual-Guide/virtual-guide/data/isa_khan_niazi.mp3";


        if(name.equals("Isa Khan's Complex"))
        {
            url = "http://10.0.0.30/Virtual-Guide/virtual-guide/data/isa_khan_niazi.mp3";
            if(MainActivity.placeIntro[0]!=null) {
                tvtv.setText(MainActivity.placeIntro[0]);
            }
            img.setImageResource(R.drawable.isa_khan_mosque);

        }
        else
        {
            url = "http://10.0.0.30/Virtual-Guide/virtual-guide/data/topping_things_up.mp3";
            if(MainActivity.placeIntro[2]!=null) {
                tvtv.setText(MainActivity.placeIntro[2]);
            }
            img.setImageResource(R.drawable.topping_thing_up);
        }

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
