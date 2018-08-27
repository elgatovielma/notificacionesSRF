package com.tesis.avdt.notificacionessrf;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class adapterAlerta extends RecyclerView.Adapter<adapterAlerta.ViewHolderAlertas> {

    private historialOpenHelper mDB;
    private List<alertaItem> cargaEventos;
    private final LayoutInflater mInflater;
    private Context mContext;


    public adapterAlerta(Context context, List<alertaItem>  cargaEventos ) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        //mDB = db;
        this.cargaEventos = cargaEventos;
    }

    @NonNull
    @Override
    public ViewHolderAlertas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.historial_modelo, parent, false);
        return new ViewHolderAlertas(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderAlertas holder, int position) {

        //final alertaItem current = mDB.query(position);
        holder.fechaItemView.setText(cargaEventos.get(position).getFecha());
        holder.horaItemView.setText(cargaEventos.get(position).getHora());

        holder.alertaItemView.setText(cargaEventos.get(position).getHora());

        holder.iconoItemView.setOnClickListener(
                new MyButtonOnClickListener(cargaEventos
                        .get(position).getId(), null) {
                    @Override
                    public void onClick(final View v) {
                        super.onClick(v);
                        alertBox(v);
                    }
                });
        /*
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int id = current.getId();
                int deleted = mDB.delete(id);
                if (deleted >= 0)
                    notifyItemRemoved(holder.getAdapterPosition());
                Toast.makeText(v.getContext(),
                        "Alerta elimidada del historial", Toast.LENGTH_SHORT).show();
                return true;
            }
        });*/

    }


    @Override
    public int getItemCount() {
        return cargaEventos.size();
    }

    public void alertBox(final View v) {

        AlertDialog.Builder myAlertBuilder =
                new AlertDialog.Builder(v.getContext());
        // Set the dialog title.
        myAlertBuilder.setTitle("Llamada");
        // Set the dialog message.
        myAlertBuilder.setMessage("¿Seguro que desea llamar?");

        // Add the buttons.
        myAlertBuilder.setPositiveButton("Si",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // User clicked OK button.
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
                        // User cancelled the dialog.
                        } });

        myAlertBuilder.show();
    }





    public class ViewHolderAlertas extends RecyclerView.ViewHolder {

        public final TextView fechaItemView, horaItemView, alertaItemView;
        public final ImageView iconoItemView;

        public ViewHolderAlertas(View itemView) {
            super(itemView);

            alertaItemView = itemView.findViewById(R.id.alertatexto);
            fechaItemView = itemView.findViewById(R.id.fechafinalizacion);
            horaItemView = itemView.findViewById(R.id.horafinalizacion);
            iconoItemView = itemView.findViewById(R.id.idImagen);
        }
    }

}
