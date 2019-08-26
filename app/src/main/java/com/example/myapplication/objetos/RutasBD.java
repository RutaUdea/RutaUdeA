package com.example.myapplication.objetos;

import java.io.Serializable;

public class RutasBD  implements Serializable {
    private String nombre;
    private String correo;
    private String foto;
    private String numero;
    private String ruta;
    private String zona;
    private String id;
    private String horarios;

    public RutasBD() {
    }

    public RutasBD(String nombre, String correo, String foto, String numero, String ruta, String zona, String id,String horarios) {
        this.nombre = nombre;
        this.correo = correo;
        this.foto = foto;
        this.numero = numero;
        this.ruta = ruta;
        this.zona= zona;
        this.id=id;
        this.horarios=horarios;
    }

    public String getHorarios() {
        return horarios;
    }

    public void setHorarios(String horarios) {
        this.horarios = horarios;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
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

