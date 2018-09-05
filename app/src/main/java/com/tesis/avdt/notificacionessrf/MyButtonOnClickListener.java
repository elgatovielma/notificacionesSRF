package com.tesis.avdt.notificacionessrf;


import android.view.View;

/*
Basado en los apuntes del curso Programación en Ambiente Android,
de la Escuela de Ingeniería de Telecomunicaciones
de la Universidad Católica Andrés Bello. Caracas
Autor: José Gregorio Castillo Pacheco
 */

public class MyButtonOnClickListener implements View.OnClickListener{

    int id;
    String word;

    public MyButtonOnClickListener(int id, String word) {
        this.id = id;
        this.word = word;
    }

    public void onClick(View v) {
        //Implementado en adapterAlerts
    }

}
