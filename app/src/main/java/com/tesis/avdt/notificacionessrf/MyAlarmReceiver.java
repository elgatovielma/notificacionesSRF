package com.tesis.avdt.notificacionessrf;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MyAlarmReceiver extends BroadcastReceiver {

    /*
    Explicacion:
    * */

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, MyLoopjTask.class);

        // Se agregan los extras al bundle
        Bundle extra = intent.getExtras();
        if(extra != null) {
            i.putExtra("usuario",extra.getString("usuario"));
            i.putExtra("password",extra.getString("password"));
        } else {
        }
        // Comienza el servicio
        context.startService(i);
    }
}
