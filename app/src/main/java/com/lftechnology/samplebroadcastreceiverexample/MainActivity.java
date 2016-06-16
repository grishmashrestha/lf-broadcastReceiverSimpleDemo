package com.lftechnology.samplebroadcastreceiverexample;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    TestReceiver testReceiver;
    private String TEST_BROADCAST = "testBroadcast";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testReceiver = new TestReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        register here if it has not been registered at the manifest file
//        registerReceiver(testReceiver, new IntentFilter(Intent.ACTION_POWER_CONNECTED));
    }

    @Override
    protected void onPause() {
        super.onPause();
//        unregister here if registered dynamically
//        unregisterReceiver(testReceiver);
    }

    public void downloadImage(View view) {
        Toast.makeText(MainActivity.this, "Image download initiated", Toast.LENGTH_SHORT).show();
        new MyAsyncTask().execute();
    }

    public class MyAsyncTask extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
//                URL url = new URL("http://www.clipartbest.com/cliparts/9i4/o8L/9i4o8LL6T.png");
                URL url = new URL("http://image.shutterstock.com/z/stock-vector-smiling-baby-whale-cartoon-illustration-76588003.jpg");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();

                File SDCardRoot = getFilesDir();

                File file = new File(SDCardRoot, "test.png");
                FileOutputStream fileOutput = new FileOutputStream(file);
                InputStream inputStream = urlConnection.getInputStream();
                int totalSize = urlConnection.getContentLength();
                int downloadedSize = 0;
                byte[] buffer = new byte[1024];
                int bufferLength = 0;
                while((bufferLength = inputStream.read(buffer)) > 0) {
                    fileOutput.write(buffer, 0, bufferLength);
                    downloadedSize += bufferLength;
                    updateProgress(downloadedSize, totalSize);
                }
                fileOutput.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    public void updateProgress(int downloadedSize, int totalSize) {
        HashMap<String , Integer> hashMap = new HashMap<>();
        hashMap.put("downloadedSize", downloadedSize);
        hashMap.put("totalSize", totalSize);

        Intent intent = new Intent();
        intent.putExtra("size", hashMap);
        intent.setAction(TEST_BROADCAST);
        sendBroadcast(intent);
    }
}
