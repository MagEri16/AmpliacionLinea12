package com.calymayor.linea12.schemas;

/**
 * Created by etorres on 26/01/17.
 */

public interface IUsuarioSchema {
    String TABLA_USUARIO = "Usuario";
    String COLUMNA_ID = "idUsuario";
    String COLUMNA_NOMBRE = "nombre";
    String COLUMNA_EMAIL = "email";
    String COLUMNA_TIPO_USUARIO_ID = "idTipoUsuario";
    String COLUMNA_USUARIO = "usuario";
    String COLUMNA_PASSWORD = "password";
    String COLUMNA_GRUPO_MOVIL = "grupoMovil";
    String COLUMNA_FIRMA = "firma";



    String TABLA_USUARIO_CREATE = "CREATE TABLE IF NOT EXISTS "
            + TABLA_USUARIO
            + " ("
            + COLUMNA_ID
            + " INTEGER PRIMARY KEY, "
            + COLUMNA_NOMBRE
            + " TEXT NOT NULL, "
            + COLUMNA_EMAIL
            + " TEXT, "
            + COLUMNA_TIPO_USUARIO_ID
            + " INTEGER , "
            + COLUMNA_USUARIO
            + " TEXT NOT NULL, "
            + COLUMNA_PASSWORD
            + " TEXT NOT NULL, "
            + COLUMNA_GRUPO_MOVIL
            + " TEXT, "
            + COLUMNA_FIRMA
            + " TEXT "
            + ")";


    String[] USUARIO_COLUMNAS = new String[] { COLUMNA_ID,
            COLUMNA_NOMBRE, COLUMNA_EMAIL, COLUMNA_TIPO_USUARIO_ID, COLUMNA_USUARIO,
            COLUMNA_PASSWORD, COLUMNA_GRUPO_MOVIL, COLUMNA_FIRMA};

}
