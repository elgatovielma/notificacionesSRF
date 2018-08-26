package com.tesis.avdt.notificacionessrf;


import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
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

public class MyLoopjTask extends  IntentService implements  Response.Listener<JSONObject>, Response.ErrorListener{

    private static final String TAG = "MOVIE_TRIVIA";

    private AsyncHttpClient asyncHttpClient;
    private RequestParams requestParams;

    private String BASE_URL = "http://192.168.1.4/pruebaBD/JSONConsulta.php?last_name=Vielma&first_name=Alberto";
    private JSONArray jsonResponse;

    public MyLoopjTask() {
        super("test-service");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        asyncHttpClient = new AsyncHttpClient();
        requestParams = new RequestParams();
        /*Bundle extra = intent.getExtras();
        Log.d("EXTRASNOT", "Enter in EXTRAS");
        if(extra != null) {
            ejecutarPedido(extra.getString("first_name"),
                    extra.getString("last_name"));
        } else {
            Log.d("EXTRAS", "Extras are NULL");
        }*/
        ejecutarPedido("Alberto",
                "Vielma");
    }

    public void ejecutarPedido(String usuario, String pass){
        Log.d("SERVICE", "Service is running");
        requestParams.put("first_name", usuario);
        requestParams.put("last_name",pass);
        Log.d("PARAMETRONAME", usuario);

        RequestQueue request = Volley.newRequestQueue(this);

        String url = "http://192.168.1.4/pruebaBD/JSONConsulta.php?"
                + "last_name="+pass
                +"&first_name="+usuario;


       /* String url = "http://192.168.1.11/tesis/JSONConsulta.php?"
                + "clave="+infoPassword
                +"&user="+infoUser;     */


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                null,this,this);

        // Add the request to the RequestQueue.
        request.add(jsonObjectRequest);


        /*
        asyncHttpClient.post(BASE_URL, requestParams, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("onSuccess", "Enter in onSuccess");
                jsonResponse =  response.optJSONArray("employees");

                JSONObject jsonObject;

                try {
                    jsonObject = jsonResponse.getJSONObject(0);

                    int idConsultado = jsonObject.optInt("id");


                    if (idConsultado == 0){
                        Log.d("CONSULTAHTTP", "No se encontro ");
                    }
                    else{
                        Log.d("CONSULTAHTTP", "El id es "+ idConsultado );
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e(TAG, "onFailure: " + errorResponse);
            }
        });*/

    }


    @Override
    public void onErrorResponse(VolleyError error) {

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
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
