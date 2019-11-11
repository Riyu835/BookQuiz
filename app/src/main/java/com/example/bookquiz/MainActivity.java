package com.example.bookquiz;

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
    private ImageButton play, pause, stop;
    private MediaPlayer audioBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        audioBackground = new MediaPlayer();

        play = (ImageButton) findViewById(R.id.play);
        pause = (ImageButton) findViewById(R.id.pause);
        stop = (ImageButton) findViewById(R.id.stop);

        stateAwal();

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
                play.setEnabled(false);
                pause.setEnabled(true);
                stop.setEnabled(true);
            }
        });

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

    public void stateAwal(){
        play.setEnabled(true);
        pause.setEnabled(false);
        stop.setEnabled(false);
    }

    private void play() {
        audioBackground = MediaPlayer.create(this, R.raw.soundy);

        try {
            audioBackground.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        audioBackground.start();

        audioBackground.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stateAwal();
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
        audioBackground.stop();

        try{
            audioBackground.prepare();
            audioBackground.seekTo(0);
        }catch (Throwable t) {
            t.printStackTrace();
        }

        stateAwal();
    }
}
