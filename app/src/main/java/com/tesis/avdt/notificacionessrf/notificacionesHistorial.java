package com.tesis.avdt.notificacionessrf;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

import java.util.ArrayList;

/*
Basado en los apuntes del curso Programación en Ambiente Android,
de la Escuela de Ingeniería de Telecomunicaciones
de la Universidad Católica Andrés Bello. Caracas
Autor: José Gregorio Castillo Pacheco
 */

public class notificacionesHistorial extends AppCompatActivity
        implements  Response.Listener<JSONObject>, Response.ErrorListener{

    private RecyclerView mRecyclerView;
    private adapterAlerta mAdapter;
    private SharedPreferences log;
    private ArrayList<alertaItem> listaAlerta;
    private RequestQueue request;
    private ProgressDialog progreso;
    private static final String urlConsultaImagenes =
            "http://192.168.1.4/tesis/JSONConsultaimagen.php";

    public notificacionesHistorial(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaciones_historial);
        log = getSharedPreferences("login",MODE_PRIVATE);
        listaAlerta = new ArrayList<>();
        // Crear recycler View
        mRecyclerView = findViewById(R.id.recyclerview);
        cargarLista();

        // Dar al recycler view un manejador de layout por defecto
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onResponse(JSONObject response) {
        progreso.hide();
        alertaItem  evento;

        JSONArray json=response.optJSONArray("empleado");
        try {
            for (int i=0;i<json.length();i++){
                evento=new alertaItem();
                JSONObject jsonObject;
                jsonObject=json.getJSONObject(i);

                evento.setId(jsonObject.optInt("id"));
                evento.setFecha(jsonObject.optString("fecha_hora"));
                evento.setFoto(jsonObject.optString("nombreimagen"));
                listaAlerta.add(evento);
            }
            // Crear un adaptador para mostrar la data en el recycler view.
            mAdapter = new adapterAlerta(this,listaAlerta);
            // Conectar el adaptador con el recycler view.
            mRecyclerView.setAdapter(mAdapter);

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

    private void cargarLista() {
        progreso = new ProgressDialog(this);
        progreso.setMessage("Cargando...");
        progreso.show();
        listaAlerta.clear();
        request = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                urlConsultaImagenes, null,this,this);

        // Agregar solicitud a la cola de solicitudes
        request.add(jsonObjectRequest);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        cargarLista();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.cerrar_sesion){
            log.edit().putBoolean("logged",false).apply();
            logout();
        }
        else if (id == R.id.actualizar){
            cargarLista();
            mAdapter.notifyDataSetChanged();
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        Toast.makeText(getBaseContext(),
                "Hasta pronto", Toast.LENGTH_SHORT).show();

        NotificationManager notificationManager = (NotificationManager)
                getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(0);

        Intent i = new Intent(getBaseContext(), MainActivity.class);
        Intent intentAlarm = new Intent("com.example.android.standup.ACTION_NOTIFY");
        PendingIntent sender = PendingIntent.getBroadcast(this,
                123456789,intentAlarm, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(sender);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {

    }

}
