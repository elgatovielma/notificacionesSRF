package com.tesis.avdt.notificacionessrf;


import android.util.Log;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;


public class FirebaseInstanceIDService extends FirebaseInstanceIdService
        implements Response.Listener<String>, Response.ErrorListener {


    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        Log.d("TEMP6", "Si entra");
        if (FirebaseMessaging.getInstance().isAutoInitEnabled()){
            String token = FirebaseInstanceId.getInstance().getToken();
            registerToken(token);
            Log.d("TEMP5", "prendido3");
        }
    }

    public void registerToken(String token) {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.168.1.4/pruebaBD/FCMregister.php?Token="+token;

        // Request a string response from the provided URL.
        StringRequest stringRequest =
                new StringRequest(Request.Method.GET, url, this, this);

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


    @Override
    public void onResponse(String response) {
        if (FirebaseMessaging.getInstance().isAutoInitEnabled()){

            Log.d("TEMP4", "prendido2");
        }

        Log.d("TEMP5", "apagado2");
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getBaseContext(),
                "No Funciona!", Toast.LENGTH_SHORT).show();
    }

}
