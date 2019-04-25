package com.calymayor.linea12.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.calymayor.linea12.daos.interfaces.IVistaDao;
import com.calymayor.linea12.dataaccess.DbContentProvider;
import com.calymayor.linea12.modelos.Vista;
import com.calymayor.linea12.schemas.IVistaSchema;


/**
 * Created by etorres on 27/01/17.
 */

public class VistaDao extends DbContentProvider implements IVistaDao, IVistaSchema {

    Cursor cursor;
    ContentValues initialValues;

    public VistaDao(SQLiteDatabase db) {
        super(db);
    }

    @Override
    public Vista fetchVistaById(int personalId) {

        final String selectionArgs[] = {String.valueOf(personalId)};
        final String selection = COLUMNA_ID + " = ?";
        Vista vista = new Vista();
        cursor = super.query(TABLA_VISTA, VISTA_COLUMNAS, selection,
                selectionArgs, COLUMNA_ID);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                vista = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return vista;
    }

    @Override
    public List<Vista> fetchAllVistas() {

        List<Vista> vistaList = new ArrayList<Vista>();
        cursor = super.query(TABLA_VISTA, VISTA_COLUMNAS, null,
                null, COLUMNA_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Vista vista = cursorToEntity(cursor);
                vistaList.add(vista);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return vistaList;

    }

    @Override
    public boolean addVista(Vista vista) {

        // set values
        setContentValue(vista);
        try {
            return super.insert(TABLA_VISTA, getContentValue()) > 0;
        } catch (SQLiteConstraintException ex){
            Log.w("Database", ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean addVistas(List<Vista> vistas) {

        if(vistas!=null&&vistas.size()>0) {

            for (int i = 0; i < vistas.size(); i++) {
                addVista(vistas.get(i));
            }
        }
        return false;
    }

    @Override
    public boolean deleteAllVistas() {
        System.out.println("Borrando las vistas....");
        super.delete(TABLA_VISTA,null,null);
        return false;
    }

    @Override
    protected Vista cursorToEntity(Cursor cursor) {

        int idIndex;
        int nombreIndex;
        int ligaIndex;


        Vista vista = new Vista();

        if (cursor != null) {
            if (cursor.getColumnIndex(COLUMNA_ID) != -1) {
                idIndex = cursor.getColumnIndexOrThrow(COLUMNA_ID);
                vista.setId(cursor.getInt(idIndex));
            }
            if (cursor.getColumnIndex(COLUMNA_NOMBRE) != -1) {
                nombreIndex = cursor.getColumnIndexOrThrow(
                        COLUMNA_NOMBRE);
                vista.setNombre(cursor.getString(nombreIndex));
            }
            if (cursor.getColumnIndex(COLUMNA_LIGA) != -1) {
                ligaIndex = cursor.getColumnIndexOrThrow(
                        COLUMNA_LIGA);
                vista.setLiga(cursor.getString(ligaIndex));
            }

        }
        return vista;
    }

    private ContentValues getContentValue() {
        return initialValues;
    }

    private void setContentValue(Vista vista) {
        initialValues = new ContentValues();
        initialValues.put(COLUMNA_NOMBRE, vista.getNombre());
        initialValues.put(COLUMNA_LIGA, vista.getLiga());

    }
}
