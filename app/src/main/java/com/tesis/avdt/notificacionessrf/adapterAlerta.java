package com.tesis.avdt.notificacionessrf;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

/*
Basado en los apuntes del curso Programación en Ambiente Android,
de la Escuela de Ingeniería de Telecomunicaciones
de la Universidad Católica Andrés Bello. Caracas
Autor: José Gregorio Castillo Pacheco
 */

public class adapterAlerta extends RecyclerView.Adapter<adapterAlerta.ViewHolderAlertas> {

    private List<alertaItem> cargaEventos;
    private final LayoutInflater mInflater;
    private Context mContext;
    private RequestQueue request;
    private static final String   RUTA_IMAGENES = "http://192.168.1.4/tesis/";
    private static final String   RUTA_NULL = "http://192.168.1.4/tesis/null";


    public adapterAlerta(Context context, List<alertaItem>  cargaEventos ) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        this.cargaEventos = cargaEventos;
        //Permite la carga de las imagenes desde la bd remota
        request = Volley.newRequestQueue(mContext);
    }

    @NonNull
    @Override
    public ViewHolderAlertas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.historial_modelo, parent, false);
        return new ViewHolderAlertas(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderAlertas holder, int position) {

        if (cargaEventos.get(position).getFoto()!=null){
            Log.d("Pruebaimagen", "Entra carga imagen  ");
            cargarImagenesWebService(cargaEventos.get(position).getFoto(), holder);
        }else{
            Log.d("Pruebaimagen", "Entra carga imagen predefinida ");
            holder.iconoHistorialItemView.setImageResource(R.drawable.loadingimage);
        }

        holder.fechaItemView.setText(cargaEventos.get(position).getFecha());
        holder.iconoItemView.setOnClickListener(
                new MyButtonOnClickListener(cargaEventos
                        .get(position).getId(), null) {
                    @Override
                    public void onClick(final View v) {
                        super.onClick(v);
                        alertBox(v);
                    }
                });
    }

    private void cargarImagenesWebService(String rutaFoto,final ViewHolderAlertas holder ) {
        String urlImagen = RUTA_IMAGENES + rutaFoto;

        if (urlImagen.contains(RUTA_NULL)){
            holder.iconoHistorialItemView.setImageResource(R.drawable.loadingimage);
        }
        ImageRequest imageRequest = new ImageRequest(urlImagen,
                new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                holder.iconoHistorialItemView.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER, null,
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        request.add(imageRequest);
    }

    @Override
    public int getItemCount() {
        return cargaEventos.size();
    }

    public void alertBox(final View v) {

        AlertDialog.Builder myAlertBuilder =
                new AlertDialog.Builder(v.getContext());
        myAlertBuilder.setTitle("Llamada");
        myAlertBuilder.setMessage("¿Seguro que desea llamar a seguridad?");

        // Add the buttons.
        myAlertBuilder.setPositiveButton("Si",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Oprimir boton "si".
                        String phone = "+582124511483";
                        Intent intent = new Intent(Intent.ACTION_CALL,
                                Uri.fromParts("tel", phone, null));

                        if (ActivityCompat.checkSelfPermission(v.getContext(),
                                android.Manifest.permission.CALL_PHONE)
                                != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        mContext.startActivity(intent); } });
        myAlertBuilder.setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Dialogo de cancelar
                        } });
        myAlertBuilder.show();
    }





    public class ViewHolderAlertas extends RecyclerView.ViewHolder {

        public final TextView fechaItemView, alertaItemView;
        public final ImageView iconoItemView, iconoHistorialItemView;

        public ViewHolderAlertas(View itemView) {
            super(itemView);
            alertaItemView = itemView.findViewById(R.id.alertatexto);
            fechaItemView = itemView.findViewById(R.id.fechafinalizacion);
            iconoItemView = itemView.findViewById(R.id.idImagen);
            iconoHistorialItemView = itemView.findViewById(R.id.idimageneshistorial);
        }
    }

}
