package com.tesis.avdt.notificacionessrf;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;

public class notificacionesHistorial extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private adapterAlerta mAdapter;
    private historialOpenHelper mDB;
    SharedPreferences log;

    public notificacionesHistorial(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaciones_historial);
        log = getSharedPreferences("login",MODE_PRIVATE);


        mDB = new historialOpenHelper(this);

        // Create recycler view.
        mRecyclerView = findViewById(R.id.recyclerview);
        // Create an mAdapter and supply the data to be displayed.
        mAdapter = new adapterAlerta(this,mDB);
        // Connect the mAdapter with the recycler view.
        mRecyclerView.setAdapter(mAdapter);
        // Give the recycler view a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        String[] informacion;

        Intent in = getIntent();
        Bundle extra = in.getExtras();

        if (extra != null){
            Log.d("TEMP", "SALUT ");

            informacion = extra.getStringArray("Mew");
            mDB.insert(informacion);
            mAdapter.notifyDataSetChanged();
        }


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle extras = intent.getExtras();
        String[] info;
        Log.d("TEMP", "BONJOUR ");
        if(extras != null) {
            info = extras.getStringArray("Mew");

            mDB.insert(info);
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


            return  true;

        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("StaticFieldLeak")
    private void logout() {
        new AsyncTask<Void,Void,Void>()
        {
            @Override
            protected Void doInBackground(Void... params)
            {
                {
                    try
                    {
                        FirebaseInstanceId.getInstance().deleteInstanceId();
                        //FirebaseMessaging.getInstance().setAutoInitEnabled(false);
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void result)
            {
                Toast.makeText(getBaseContext(),
                        "Hasta pronto", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                startActivity(i);
            }
        }.execute();
    }

    @Override
    public void onBackPressed() {

    }
}
