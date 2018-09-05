package com.tesis.avdt.notificacionessrf;

/*
Basado en los apuntes del curso Programación en Ambiente Android,
de la Escuela de Ingeniería de Telecomunicaciones
de la Universidad Católica Andrés Bello. Caracas
Autor: José Gregorio Castillo Pacheco
 */

public class alertaItem {

    private int id;
    private String fecha;
    private String hora;
    private String foto;

    public alertaItem(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
