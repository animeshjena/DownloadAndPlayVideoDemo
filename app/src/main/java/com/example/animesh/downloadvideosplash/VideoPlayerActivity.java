package com.example.animesh.downloadvideosplash;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;


public class VideoPlayerActivity extends Activity {
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video_player);
        Intent intent=getIntent();
        String videoname=intent.getStringExtra("name");
        int number=intent.getIntExtra("number",0);
        String urladd=intent.getStringExtra("url");

        VideoView videoView = (VideoView) findViewById(R.id.videoView);

        //Creating MediaController
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        //videoView.setVideoPath("/sdcard/AndroidCommercial.3gp");

        //specify the location of media file

        if(number==1)
        {

            uri = Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/Download/" + videoname);
        }
        else if(number==2)
        {
            uri = Uri.parse(urladd);
        }
        //Setting MediaController and URI, then starting the videoView
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();

    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}