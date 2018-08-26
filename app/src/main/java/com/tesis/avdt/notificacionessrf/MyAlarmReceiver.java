package com.tesis.avdt.notificacionessrf;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MyAlarmReceiver extends BroadcastReceiver {

    public static final int REQUEST_CODE = 12345;
    public static final String ACTION = "com.codepath.example.servicesdemo.alarm";

    @Override
    public void onReceive(Context context, Intent intent) {
       /* Intent i = new Intent(context, MyLoopjTask.class);
        // Add extras to the bundle

        Bundle extra = intent.getExtras();
        Log.d("EXTRASNOT", "Enter in EXTRAS");
        if(extra != null) {
            i.putExtra("first_name",extra.getString("first_name"));
            i.putExtra("last_name",extra.getString("last_name"));
        } else {
            Log.d("EXTRAS", "Extras are NULL");
        }

        // Start the service
        context.startService(i);*/
        Log.d("BROADCASTRECEIVE", "BROADCAST ENTRA");
        Intent i = new Intent(context, MyLoopjTask.class);
        i.putExtra("foo", "bar");
        context.startService(i);
    }
}
