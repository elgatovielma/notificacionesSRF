package com.tesis.avdt.notificacionessrf;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MyAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, MyLoopjTask.class);
        // Add extras to the bundle

        Bundle extra = intent.getExtras();
        Log.d("EXTRASNOT", "Enter in EXTRAS");
        if(extra != null) {
            i.putExtra("usuario",extra.getString("usuario"));
            i.putExtra("password",extra.getString("password"));
        } else {
            Log.d("EXTRAS", "Extras are NULL");
        }
        // Start the service
        context.startService(i);
        Log.d("BROADCASTRECEIVE", "BROADCAST ENTRA");
    }
}
