package com.tesis.avdt.notificacionessrf;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
        implements  Response.Listener<JSONObject>, Response.ErrorListener {

    private EditText user, password;
    private Button login;
    private ProgressDialog progreso;
    private RequestQueue request;
    SharedPreferences logeo;
    FirebaseInstanceIDService token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseMessaging.getInstance().setAutoInitEnabled(false);

        token = new FirebaseInstanceIDService();

        Log.d("TEMP3", "apagado ");

        user = findViewById(R.id.usernameinicio);
        password = findViewById(R.id.passwordinicio);
        login = findViewById(R.id.buttonlogin);

        logeo = getSharedPreferences("login",MODE_PRIVATE);

        if(logeo.getBoolean("logged",false)){
            Intent inte = new Intent(this, notificacionesHistorial.class);
            startActivity(inte);
        }

    }


    public void Login(View view) {


        progreso = new ProgressDialog(this);
        progreso.setMessage("Cargando...");
        activarService();


        String infoUser = user.getText().toString().trim();
        String infoPassword = password.getText().toString().trim();

        request = Volley.newRequestQueue(this);

        String url = "http://192.168.1.4/pruebaBD/JSONConsulta.php?"
                + "last_name="+infoPassword
                +"&first_name="+infoUser;

        /*
        String url = "http://192.168.1.11/tesis/JSONConsulta.php?"
                + "clave="+infoPassword
                +"&user="+infoUser;*/


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                null,this,this);

        // Add the request to the RequestQueue.
        request.add(jsonObjectRequest);
    }




    @Override
    public void onResponse(JSONObject response) {
        SystemClock.sleep(1000);

        progreso.hide();

        //JSONArray json = response.optJSONArray("empleados");
        JSONArray json = response.optJSONArray("employees");
        JSONObject jsonObject;

        try {
            jsonObject = json.getJSONObject(0);

            int idConsultado = jsonObject.optInt("id");
            //String userConsultado = jsonObject.optString("first_name");
            //String passwordConsultado = jsonObject.optString("last_name");

            if (idConsultado == 0){
                Toast.makeText(getBaseContext(),
                        "Usuario no encontrado", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getBaseContext(),
                        "Bienvenio", Toast.LENGTH_SHORT).show();
                logeo.edit().putBoolean("logged",true).apply();

                FirebaseMessaging.getInstance().setAutoInitEnabled(true);

                FirebaseMessaging.getInstance().subscribeToTopic("test");
                String tokenObtenido = FirebaseInstanceId.getInstance().getToken();
                //token.registerToken(tokenObtenido);


                Log.d("TEMP2", "prendido ");

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
            message = "Cannot connect to Internet..." +
                    "Please check your connection!";
        } else if (volleyError instanceof ServerError) {
            message = "The server could not be found. " +
                    "Please try again after some time!!";
        } else if (volleyError instanceof AuthFailureError) {
            message = "Cannot connect to Internet..." +
                    "Please check your connection!";
        } else if (volleyError instanceof ParseError) {
            message = "Parsing error! Please try again after some time!!";
        } else if (volleyError instanceof NoConnectionError) {
            message = "Cannot connect to Internet..." +
                    "Please check your connection!";
        } else if (volleyError instanceof TimeoutError) {
            message = "Connection TimeOut! " +
                    "Please check your internet connection.";
        }

        Toast.makeText(getBaseContext(),
                message, Toast.LENGTH_SHORT).show();

    }

    @SuppressLint("StaticFieldLeak")
    private void activarService() {
        new AsyncTask<Void,Void,Void>()
        {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progreso.show();
            }

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
            }
        }.execute();
    }

    @Override
    public void onBackPressed() {

    }

}
