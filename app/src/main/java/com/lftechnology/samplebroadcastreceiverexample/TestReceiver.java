package com.lftechnology.samplebroadcastreceiverexample;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.util.HashMap;

public class TestReceiver extends BroadcastReceiver {
    private String TEST_BROADCAST = "testBroadcast";

    @Override
    public void onReceive(Context context, Intent intent) {
//        Toast.makeText(context, "Image downloading", Toast.LENGTH_SHORT).show();
        if (intent.getAction().equals(TEST_BROADCAST)) {
            HashMap<String, Integer> hashMap = (HashMap) intent.getSerializableExtra("size");
            Float downloadedSize = Float.parseFloat(String.valueOf(hashMap.get("downloadedSize")));
            Float totalSize = Float.parseFloat(String.valueOf(hashMap.get("totalSize")));


            NotificationCompat.Builder builder;
            if (((downloadedSize / totalSize) * 100) == 100) {
                Intent resultIntent = new Intent();
                resultIntent.setAction(Intent.ACTION_VIEW);
                File file = new File(Environment.getExternalStorageDirectory(), "/test.png");
                resultIntent.setDataAndType(Uri.parse(file.toURI().toString()), "image/*");

                PendingIntent resultPendingIntent = PendingIntent.getActivity(
                        context,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
                builder = new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.whale)
                        .setContentTitle("File Downloaded")
                        .setContentText(Math.round((downloadedSize / totalSize) * 100) + "% completed.")
                        .setContentIntent(resultPendingIntent);
            } else {
                Intent resultIntent = new Intent(context, MainActivity.class);
                PendingIntent resultPendingIntent = PendingIntent.getActivity(
                        context,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

                builder = new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.whale)
                        .setContentTitle("File Downloading")
                        .setContentText(Math.round((downloadedSize / totalSize) * 100) + "% completed.")
                        .setContentIntent(resultPendingIntent);
            }


            NotificationManager managerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            managerCompat.notify(1, builder.build());
        }
    }
}
