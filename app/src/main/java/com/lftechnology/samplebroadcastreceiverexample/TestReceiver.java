package com.lftechnology.samplebroadcastreceiverexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class TestReceiver extends BroadcastReceiver {
//    private String TEST_BROADCAST = "testBroadcast";

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Image downloading", Toast.LENGTH_SHORT).show();

    }
}
