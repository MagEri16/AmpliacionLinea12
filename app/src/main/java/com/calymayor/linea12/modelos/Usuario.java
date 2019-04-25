package com.calymayor.linea12.modelos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by etorres on 02/08/17.
 */

public class Usuario implements Parcelable {

    @SerializedName("idPersonal")
    @Expose
    int id;

    @SerializedName("nombre")
    @Expose
    String nombre;

    private String email;
    private int idTipoPersonal;

    @SerializedName("usuario")
    @Expose
    String usuario;

    @SerializedName("password")
    @Expose
    String password;

    @SerializedName("grupomovil")
    @Expose
    private String grupoMovil;

    private String firma;


    public Usuario() {
    }

    public Usuario(int id) {
        this.id = id;
    }

    protected Usuario(Parcel in) {
        id = in.readInt();
        nombre = in.readString();
        email = in.readString();
        idTipoPersonal = in.readInt();
        usuario = in.readString();
        password = in.readString();
        grupoMovil = in.readString();
        firma = in.readString();
    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdTipoPersonal() {
        return idTipoPersonal;
    }

    public void setIdTipoPersonal(int idTipoPersonal) {
        this.idTipoPersonal = idTipoPersonal;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGrupoMovil() {
        return grupoMovil;
    }

    public void setGrupoMovil(String grupoMovil) {
        this.grupoMovil = grupoMovil;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nombre);
        dest.writeString(email);
        dest.writeInt(idTipoPersonal);
        dest.writeString(usuario);
        dest.writeString(password);
        dest.writeString(grupoMovil);
        dest.writeString(firma);
    }
}
