package com.calymayor.linea12.schemas;

/**
 * Created by etorres on 26/01/17.
 */

public interface IVistaSchema {
    String TABLA_VISTA = "Vista";
    String COLUMNA_ID = "id";
    String COLUMNA_NOMBRE = "nombre";
    String COLUMNA_LIGA = "liga";



    String TABLA_VISTA_CREATE = "CREATE TABLE IF NOT EXISTS "
            + TABLA_VISTA
            + " ("
            + COLUMNA_ID
            + " INTEGER PRIMARY KEY, "
            + COLUMNA_NOMBRE
            + " TEXT NOT NULL, "
            + COLUMNA_LIGA
            + " TEXT "
            + ")";


    String[] VISTA_COLUMNAS = new String[] { COLUMNA_ID, COLUMNA_NOMBRE, COLUMNA_LIGA};

}
