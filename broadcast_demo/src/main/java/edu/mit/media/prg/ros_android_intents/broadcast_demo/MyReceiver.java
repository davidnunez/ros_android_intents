package edu.mit.media.prg.ros_android_intents.broadcast_demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "MyReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String bundleString = bundleToString(intent.getExtras());
        Log.d(TAG, String.format("Intent:%s", bundleString));
        Toast.makeText(context, String.format("Intent: %s", bundleToString(intent.getExtras())), Toast.LENGTH_LONG).show();
    }

    public static String bundleToString(Bundle bundle) {
        String string = "{";
        for (String key : bundle.keySet()) {
            string += " " + key + " : " + bundle.get(key) + ";";
        }
        string += "}";
        return string;
    }

}
