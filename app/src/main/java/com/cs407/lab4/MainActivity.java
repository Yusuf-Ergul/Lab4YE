package com.cs407.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button startButton;
    private volatile boolean stopThread = false;
    private TextView textView;
    private int downloadProgress;
    String textToString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startButton);
        textView = findViewById(R.id.resourceTextView);
    }

    public void mockFileDownloader() {

        String text;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startButton.setText("Downloading...");
            }
        });
        
        for(downloadProgress = 0; downloadProgress <= 100; downloadProgress=downloadProgress+10) {
            text = "Download Progress: " + downloadProgress + "%";
            if (stopThread) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startButton.setText("Start");
                    }
                });
                return;
            }

            Log.d(TAG, "Download Progress: " + downloadProgress + "%");
            Log.i("info", "Hi" + text);
            String text2 = text;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView.setText("Download Progress: " + downloadProgress + "%");
                }
            });


            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startButton.setText("Start");
            }
        });
    }

    public void startDownload(View view){
        stopThread = false;
        ExampleRunnable runnable = new ExampleRunnable();
        new Thread(runnable).start();
    }

    public void stopDownload(View view) {
        stopThread = true;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText("Download Canceled");
            }
        });
    }

    class ExampleRunnable implements Runnable {
        @Override
        public void run() {
            mockFileDownloader();
        }
    }
}