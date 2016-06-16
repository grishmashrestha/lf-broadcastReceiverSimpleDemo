package com.lftechnology.samplebroadcastreceiverexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ChargerStatusReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)) {
            Toast.makeText(context, "Airplane mode status changed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Power Connected", Toast.LENGTH_SHORT).show();
        }
    }
}
