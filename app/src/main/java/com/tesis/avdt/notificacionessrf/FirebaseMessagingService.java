package com.tesis.avdt.notificacionessrf;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import com.google.firebase.messaging.RemoteMessage;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;




public class FirebaseMessagingService extends
        com.google.firebase.messaging.FirebaseMessagingService {

    private String mensajeAlerta = "Una persona no identificada utilizo el sistema";


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String creacionFecha = creacionFecha();
        String creacionTiempo = creacionTiempo();

        String[] informacion = {creacionFecha, creacionTiempo};

        showNotification(remoteMessage.getData().get("message"), informacion);

    }



    private void showNotification(String message, String[] informacion) {

        Intent i = new Intent(this,notificacionesHistorial.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);

        String titulo;

        if(message.contains(mensajeAlerta)) {

            titulo = "Alerta de seguridad!";

            i.putExtra("Mew", informacion);
            i.putExtra("Mewtwo",titulo);


            PendingIntent pendingIntent = PendingIntent.getActivity(this,
                    0, i, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setAutoCancel(true)
                    .setContentTitle("Alerta de seguridad!")
                    .setContentText(message)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                    .setSmallIcon(R.drawable.if_securitycamera_531907)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentIntent(pendingIntent);

            NotificationManager manager = (NotificationManager)
                    getSystemService(NOTIFICATION_SERVICE);

            manager.notify(0,builder.build());
        }
        else{

            titulo = "Ingreso de usuario identificado";

            i.putExtra("Mew", informacion);
            i.putExtra("Mewtwo",titulo);


            PendingIntent pendingIntent = PendingIntent.getActivity(this,
                    0, i, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setAutoCancel(true)
                    .setContentTitle("Usuario verificado")
                    .setContentText(message)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                    .setSmallIcon(R.drawable.if_securitycamera_531907)
                    .setPriority(Notification.PRIORITY_MIN)
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentIntent(pendingIntent);

            NotificationManager manager = (NotificationManager)
                    getSystemService(NOTIFICATION_SERVICE);

            manager.notify(0,builder.build());

        }


    }

    private String creacionTiempo() {

        Date dt = new Date();
        int hours = dt.getHours();
        int minutes = dt.getMinutes();
        String tiempoActual= hours + ":" + minutes;
        return  tiempoActual;
    }

    private String creacionFecha() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy");
        String fechaActual =  mdformat.format(calendar.getTime());

        return  fechaActual;
    }


}
