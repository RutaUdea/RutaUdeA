package com.example.myapplication.objetos;

public class RutasBD {
    private String nombre;
    private String correo;
    private String foto;
    private String numero;
    private String ruta;

    public RutasBD() {
    }

    public RutasBD(String nombre, String correo, String foto, String numero, String ruta) {
        this.nombre = nombre;
        this.correo = correo;
        this.foto = foto;
        this.numero = numero;
        this.ruta = ruta;
    }

    public RutasBD(String nombre, String correo, String foto, String numero) {
        this.nombre = nombre;
        this.correo = correo;
        this.foto = foto;
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
}

