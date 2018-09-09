package com.tesis.avdt.notificacionessrf;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/*
Basado en los apuntes del curso Programación en Ambiente Android,
de la Escuela de Ingeniería de Telecomunicaciones
de la Universidad Católica Andrés Bello. Caracas
Autor: José Gregorio Castillo Pacheco
 */

public class MainActivity extends AppCompatActivity
        implements  Response.Listener<JSONObject>, Response.ErrorListener{

    private EditText user, password;
    private ProgressDialog progreso;
    private RequestQueue request;
    private SharedPreferences logeo;
    private static final String ACTION_NOTIFY = "com.example.android.standup.ACTION_NOTIFY";
    private static final String BASE_URL = "http://192.168.1.4/tesis/JSONConsulta.php";
    private String infoUser;
    private String infoPassword;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logeo = getSharedPreferences("login",MODE_PRIVATE);
        if(logeo.getBoolean("logged",false)){
            Intent inte = new Intent(this, notificacionesHistorial.class);
            startActivity(inte);
        }
        user = findViewById(R.id.usernameinicio);
        password = findViewById(R.id.passwordinicio);
    }


    public void login(View view) {
        progreso = new ProgressDialog(this);
        progreso.setMessage("Cargando...");
        progreso.show();
        Log.d("Pruebaboton", "Entra ");
        infoUser = user.getText().toString().trim();
        infoPassword = password.getText().toString().trim();

        request = Volley.newRequestQueue(this);

        Map<String, String> params = new HashMap();
        params.put("password", infoPassword);
        params.put("usuario", infoUser);

        JSONObject parameters = new JSONObject(params);
        Log.d("JSON", parameters.toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, BASE_URL,
                parameters,this,this);

        // Se agrega ppedido a la cola de pedidos.
        request.add(jsonObjectRequest);

    }
             @Override
             public void onResponse(JSONObject response) {
                 progreso.hide();

                 JSONArray json = response.optJSONArray("empleado");
                 JSONObject jsonObject;

                 try {
                     jsonObject = json.getJSONObject(0);
                     int idConsultado = jsonObject.optInt("id");
                     if (idConsultado == 0){
                         Toast.makeText(getBaseContext(),
                                 "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                     }
                     else{
                         Toast.makeText(getBaseContext(),
                                 "Bienvenido", Toast.LENGTH_SHORT).show();

                         logeo.edit().putBoolean("logged",true).apply();
                         MyAlarmManagerStart();
                         Intent i = new Intent(this, notificacionesHistorial.class);
                         startActivity(i);
                     }
                 } catch (JSONException e) {
                     e.printStackTrace();
                 }
             }

            @Override
             public void onErrorResponse(VolleyError volleyError) {
                 progreso.hide();

                 String message = null;
                 if (volleyError instanceof NetworkError) {
                     message = "No se puede conectar al Internet..." +
                             "Por favor verifique la coneccion";
                 } else if (volleyError instanceof ServerError) {
                     message = "El servidor no pudo ser enconrado. " +
                             "Por favor intente mas tarde!!";
                 } else if (volleyError instanceof AuthFailureError) {
                     message = "No se puede conectar al Internet..." +
                             "Por favor verifique la coneccion!";
                 } else if (volleyError instanceof ParseError) {
                     message = "Error de de infromacion, por favor intente luego!";
                 } else if (volleyError instanceof NoConnectionError) {
                     message = "No se puede conectar al Internet..." +
                             "Por favor verifique la coneccion!";
                 } else if (volleyError instanceof TimeoutError) {
                     message = "Tiempo fuera de la coneccion! " +
                             "Por favor verifique su coneccion a Internet.";
                 }
                 Toast.makeText(getBaseContext(),
                         message, Toast.LENGTH_SHORT).show();
             }



    private void MyAlarmManagerStart() {
        // Se construye un intent para el alarmManger
        Intent iService = new Intent(ACTION_NOTIFY);
        iService.putExtra("usuario",infoUser);
        iService.putExtra("password",infoPassword);

        PendingIntent sender = PendingIntent.getBroadcast(this,
                123456789,iService, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime(), 5000, sender);
    }

    @Override
    public void onBackPressed() {

    }

}
