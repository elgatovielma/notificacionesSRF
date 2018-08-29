package com.tesis.avdt.notificacionessrf;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class notificacionesHistorial extends AppCompatActivity
        implements  Response.Listener<JSONObject>, Response.ErrorListener{

    private RecyclerView mRecyclerView;
    private adapterAlerta mAdapter;
    private SharedPreferences log;
    private ArrayList<alertaItem> listaAlerta;
    private RequestQueue request;

    public notificacionesHistorial(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaciones_historial);
        log = getSharedPreferences("login",MODE_PRIVATE);
        listaAlerta = new ArrayList<>();
        // Create recycler view.
        mRecyclerView = findViewById(R.id.recyclerview);
        cargarLista();

        // Give the recycler view a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        String[] informacion;
        String infoTitulo;

        Intent in = getIntent();
        Bundle extra = in.getExtras();

        if (extra != null){
            Log.d("TEMP", "SALUT ");

            informacion = extra.getStringArray("Mew");
            infoTitulo = extra.getString("Mewtwo");
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResponse(JSONObject response) {
        alertaItem  evento;

        JSONArray json=response.optJSONArray("employees");
        try {
            for (int i=0;i<json.length();i++){
                evento=new alertaItem();
                JSONObject jsonObject;
                jsonObject=json.getJSONObject(i);

                evento.setId(jsonObject.optInt("id"));
                evento.setFecha(jsonObject.optString("first_name"));
                evento.setHora(jsonObject.optString("last_name"));
                evento.setFoto(jsonObject.optString("imagen"));
                listaAlerta.add(evento);
            }
            // Create an mAdapter and supply the data to be displayed.
            mAdapter = new adapterAlerta(this,listaAlerta);
            // Connect the mAdapter with the recycler view.
            mRecyclerView.setAdapter(mAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    private void cargarLista() {
        listaAlerta.clear();
        request = Volley.newRequestQueue(this);

        String url = "http://192.168.1.4/pruebaBD/JSONConsultaimagen.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                null,this,this);

        // Add the request to the RequestQueue.
        request.add(jsonObjectRequest);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle extras = intent.getExtras();
        cargarLista();
        String[] info;
        String infoTitulo;
        Log.d("TEMP", "BONJOUR ");
        if(extras != null) {
            info = extras.getStringArray("Mew");
            infoTitulo = extras.getString("Mewtwo");

            mAdapter.notifyDataSetChanged();

            Log.d("TEMP", "Tab Number: " + info[0]);

        } else {
            Log.d("TEMP", "Extras are NULL");

        }
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
