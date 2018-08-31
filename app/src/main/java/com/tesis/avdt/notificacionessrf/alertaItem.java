package com.tesis.avdt.notificacionessrf;

public class alertaItem {

    private int id;
    private String mensaje;
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
