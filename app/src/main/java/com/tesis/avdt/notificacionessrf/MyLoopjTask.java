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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;

/*
Basado en la documentacion encontrada en la pagagina code.tutsplus
Link: https://code.tutsplus.com/tutorials/android-fundamentals-intentservice-basics--mobile-6183
Autor: Desconocido
 */

public class MyLoopjTask extends  IntentService
        implements  Response.Listener<JSONObject>, Response.ErrorListener{

    private static final String BASE_URL = "http://192.168.1.4/tesis/JSONConsulta.php?";
    private static final String BASE_URL_LIMPIEZA = "http://192.168.1.4/tesis/limpiarAlerta.php?";

    public MyLoopjTask() {
        super("test-service");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Bundle extra = intent.getExtras();
        if(extra != null) {
            ejecutarPedido(extra.getString("usuario"),
                    extra.getString("password"));
        } else {
        }
    }

    public void ejecutarPedido(String usuario, String pass){
        RequestQueue request = Volley.newRequestQueue(this);
        String url = BASE_URL
                + "password="+pass
                +"&usuario="+usuario;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                null,this,this);

        // Add the request to the RequestQueue.
        request.add(jsonObjectRequest);
    }

    @Override
    public void onResponse(JSONObject response) {
        JSONArray json = response.optJSONArray("empleado");
        JSONObject jsonObject;

        try {
            jsonObject = json.getJSONObject(0);

            int consultaAlerta = jsonObject.optInt("acceso");
            int idConsultado = jsonObject.optInt("id");

            if (consultaAlerta == 0){
                Log.d("CONSULTAHTTP", "No hay alerta ");
            }
            else{
                Log.d("CONSULTAHTTP", "Hat alerta ");
                limpiarBnaderaAlerta(idConsultado);
                showNotification();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    private void limpiarBnaderaAlerta(int id) {
        RequestQueue request = Volley.newRequestQueue(this);
        String url = BASE_URL_LIMPIEZA
                +"id="+id;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        request.add(stringRequest);
    }

    private void showNotification() {
        Intent i = new Intent(this,notificacionesHistorial.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);

        String titulo = "Alerta de seguridad!";
        String mensaje = "Una persona no autorizada utilizo el sistema";

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
