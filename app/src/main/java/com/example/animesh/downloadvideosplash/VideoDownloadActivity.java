package com.example.animesh.downloadvideosplash;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class VideoDownloadActivity extends Activity {
    public static final int progress_bar_type = 0;
    static String url1,url2;
    private ProgressDialog prgDialog;
    static String  videoname;static int number=0;
    private Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_download);
        url1="http://clivertech.com//Videos/WTC1.m4v";
        url2="http://www.androidbegin.com/tutorial/AndroidCommercial.3gp";
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case progress_bar_type:
                prgDialog = new ProgressDialog(this);
                prgDialog.setMessage("Downloading Video file. Please wait...");
                prgDialog.setIndeterminate(false);
                prgDialog.setMax(100);
                prgDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                prgDialog.setCancelable(false);
                prgDialog.show();
                return prgDialog;
            default:
                return null;
        }
    }
    public void clivertech(View view) {

        Downloadasync downloadasync=new Downloadasync();
        videoname=Uri.parse(url1).getLastPathSegment();
        number=1;
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/Download/" + videoname);
        if (file.exists())
        {
            Toast.makeText(getApplicationContext(), "File already exist under SD card", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, VideoPlayerActivity.class);
            intent.putExtra("name", videoname);
            intent.putExtra("number",number);
            startActivity(intent);


        } else
        {
            Toast.makeText(getApplicationContext(), "File doesn't exist under SD Card, downloading  from Internet", Toast.LENGTH_LONG).show();
            // Trigger Async Task (onPreExecute method)
            downloadasync.execute(url1);
        }




    }
    public void commercial(View view) {

        Downloadasync downloadasync=new Downloadasync();
        number=1;
        videoname=Uri.parse(url2).getLastPathSegment();
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/Download/" + videoname);
        if (file.exists())
        {
            Toast.makeText(getApplicationContext(), "File already exist under SD card", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, VideoPlayerActivity.class);
            intent.putExtra("name", videoname);
            intent.putExtra("number",number);
            startActivity(intent);
            // Play Music

            // If the Music File doesn't exist in SD card (Not yet downloaded)
        } else
        {
            Toast.makeText(getApplicationContext(), "File doesn't exist under SD Card, downloading  from Internet", Toast.LENGTH_LONG).show();
            // Trigger Async Task (onPreExecute method)
            downloadasync.execute(url2);
        }


    }
    public void commercialwd(View view)
    {
        number=2;
        Intent intent=new Intent(this,VideoPlayerActivity.class);
        intent.putExtra("number",number);
        intent.putExtra("url",url2);
        startActivity(intent);

    }
    public void clivertechwd(View view)
    {
        number=2;
        Intent intent=new Intent(this,VideoPlayerActivity.class);
        intent.putExtra("number",number);
        intent.putExtra("url",url1);
        startActivity(intent);
    }




    class Downloadasync extends AsyncTask<String, Integer, Boolean> {
        private int length = -1;
        private int counter = 0;
        private int calprogress = 0;

        @Override
        protected void onPreExecute() {
            showDialog(progress_bar_type);


        }

        @Override
        protected Boolean doInBackground(String... params) {
            boolean success = false;
            URL url = null;
            HttpURLConnection urlconnection = null;
            InputStream inputStream = null;
            File file = null;
            FileOutputStream fileOutputStream = null;
            try {
                url = new URL(params[0]);


                urlconnection = (HttpURLConnection) url.openConnection();
                length = urlconnection.getContentLength();

                inputStream = urlconnection.getInputStream();

                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/" + Uri.parse(params[0]).getLastPathSegment());

                fileOutputStream = new FileOutputStream(file);

                int read = -1;
                byte[] buffer = new byte[1024];
                while ((read = inputStream.read(buffer)) != -1) {
                    //Log.d("ani","input");
                    fileOutputStream.write(buffer, 0, read);
                    counter = counter + read;
                    publishProgress(counter);

                }
                success = true;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();

            } finally {

                if (urlconnection != null) {
                    urlconnection.disconnect();
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return success;


        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            calprogress = (int) (((double) values[0] / length) * 100);
            prgDialog.setProgress(calprogress);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            dismissDialog(progress_bar_type);
          /*  Intent intent=new Intent(MainActivity.this,VideoplayerActivity.class);
            startActivity(intent);*/
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

            // set title
            alertDialogBuilder.setTitle("Your Title");

            // set dialog message
            alertDialogBuilder
                    .setMessage("video downloaded..!want to watch??")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // if this button is clicked, close
                            // current activity

                            Intent intent = new Intent(VideoDownloadActivity.this, VideoPlayerActivity.class);
                            intent.putExtra("name", videoname);
                            intent.putExtra("number",number);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();


        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
