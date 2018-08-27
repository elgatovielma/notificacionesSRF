package com.tesis.avdt.notificacionessrf;


import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;

public class MyLoopjTask extends  IntentService
        implements  Response.Listener<JSONObject>, Response.ErrorListener{

    private static final String BASE_URL = "http://192.168.1.4/pruebaBD/JSONConsulta.php?";
    private SharedPreferences logeo;

    public MyLoopjTask() {
        super("test-service");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Bundle extra = intent.getExtras();
        Log.d("EXTRASNOT", "Enter in EXTRAS");
        if(extra != null) {
            ejecutarPedido(extra.getString("first_name"),
                    extra.getString("last_name"));
        } else {
            Log.d("EXTRAS", "Extras are NULL");
        }
        //ejecutarPedido("Alberto","Vielma");
    }

    public void ejecutarPedido(String usuario, String pass){
        Log.d("SERVICE", "Service is running");
        Log.d("PARAMETRONAME", usuario);
        RequestQueue request = Volley.newRequestQueue(this);
        String url = BASE_URL
                + "last_name="+pass
                +"&first_name="+usuario;


       /* String url = "http://192.168.1.11/tesis/JSONConsulta.php?"
                + "clave="+infoPassword
                +"&user="+infoUser;     */

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                null,this,this);

        // Add the request to the RequestQueue.
        request.add(jsonObjectRequest);
    }


    @Override
    public void onResponse(JSONObject response) {
        Log.d("onResponse", "Enter in onSuccess");
        //JSONArray json = response.optJSONArray("empleados");
        JSONArray json = response.optJSONArray("employees");
        JSONObject jsonObject;

        try {
            jsonObject = json.getJSONObject(0);

            int idConsultado = jsonObject.optInt("id");

            if (idConsultado == 0){
                Log.d("CONSULTAHTTP", "No se encontro ");
            }
            else{
                Log.d("CONSULTAHTTP", "El id es "+ idConsultado );
                showNotification();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    private void showNotification() {
        Intent i = new Intent(this,notificacionesHistorial.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);

        String titulo = "Alerta de seguridad!";
        String mensaje = "Una persona no autorizada utilizo el sistema";
        //i.putExtra("Mew", informacion);
        //i.putExtra("Mewtwo",titulo);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle(titulo)
                .setContentText(mensaje)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(mensaje))
                .setSmallIcon(R.drawable.if_securitycamera_531907)
                .setPriority(Notification.PRIORITY_HIGH)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        manager.notify(0,builder.build());
    }

}
