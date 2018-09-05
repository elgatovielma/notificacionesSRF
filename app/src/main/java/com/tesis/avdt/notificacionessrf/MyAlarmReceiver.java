package com.tesis.avdt.notificacionessrf;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/*
Basado en los apuntes del curso Programación en Ambiente Android,
de la Escuela de Ingeniería de Telecomunicaciones
de la Universidad Católica Andrés Bello. Caracas
Autor: José Gregorio Castillo Pacheco
 */

public class MyAlarmReceiver extends BroadcastReceiver {

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
