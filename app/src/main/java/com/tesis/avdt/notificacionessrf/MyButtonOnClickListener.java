package com.tesis.avdt.notificacionessrf;

import android.app.AlertDialog;
import android.view.View;

public class MyButtonOnClickListener implements View.OnClickListener, View.OnLongClickListener {

    int id;
    String word;

    public MyButtonOnClickListener(int id, String word) {
        this.id = id;
        this.word = word;
    }

    public void onClick(View v) {
        // Implemented adapterAlerta
    }

    @Override
    public boolean onLongClick(View v) {
        return true;
    }
}
