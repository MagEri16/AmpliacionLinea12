package com.calymayor.linea12.modelos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by etorres on 03/01/18.
 */

public class Vista {

    @SerializedName("id")
    @Expose
    int id;

    @SerializedName("nombre")
    @Expose
    String nombre;


    @SerializedName("liga")
    @Expose
    String liga;

    public Vista() {
    }

    public Vista(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLiga() {
        return liga;
    }

    public void setLiga(String liga) {
        this.liga = liga;
    }
}
