package com.example.animesh.downloadvideosplash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class SplashScreenActivity extends Activity {
    Thread thread1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        thread1=new Thread()
        {
            @Override
            public void run()
            {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(new Intent(SplashScreenActivity.this,VideoDownloadActivity.class));
                }
            }
        };
        thread1.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
