package com.example.bookquiz;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Button buttonDash, buttonWho;
    private ImageButton pause, stop;
    private MediaPlayer audioBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        audioBackground = new MediaPlayer();

        pause = (ImageButton) findViewById(R.id.pause);
        stop = (ImageButton) findViewById(R.id.stop);

        audioBackground = MediaPlayer.create(this, R.raw.soundy);
        try {
            audioBackground.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        audioBackground.start();


        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pause();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop();
            }
        });

        buttonDash = (Button) findViewById(R.id.buttonDash);
        buttonDash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dash = new Intent(MainActivity.this, DashboardActivity.class);
                startActivity(dash);
            }
        });

        buttonWho = (Button)findViewById(R.id.buttonWho);
        buttonWho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent who = new Intent(MainActivity.this, WhowroteitActivity.class);
                startActivity(who);
            }
        });
    }

    public void pause(){
        if(audioBackground.isPlaying()){
            if(audioBackground!=null){
                audioBackground.pause();
                pause.setImageResource(R.drawable.ic_play);
            }
        } else {
            if(audioBackground!=null){
                audioBackground.start();
                pause.setImageResource(R.drawable.ic_pause);
            }
        }
    }

    public void stop(){
        if(audioBackground.isPlaying()){
                audioBackground.stop();
                stop.setImageResource(R.drawable.ic_play);
        } else {
                audioBackground = MediaPlayer.create(this, R.raw.soundy);
                try {
                    audioBackground.prepare();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                audioBackground.start();
                stop.setImageResource(R.drawable.ic_stop);
        }
    }

    @Override
    public void onBackPressed() {
        audioBackground.stop();
        MainActivity.this.finish();
    }
}
