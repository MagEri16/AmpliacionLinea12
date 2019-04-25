package com.calymayor.linea12.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.calymayor.linea12.daos.interfaces.IUsuarioDao;
import com.calymayor.linea12.dataaccess.DbContentProvider;
import com.calymayor.linea12.modelos.Usuario;
import com.calymayor.linea12.schemas.IUsuarioSchema;


/**
 * Created by etorres on 27/01/17.
 */

public class UsuarioDao extends DbContentProvider implements IUsuarioDao, IUsuarioSchema {

    Cursor cursor;
    ContentValues initialValues;

    public UsuarioDao(SQLiteDatabase db) {
        super(db);
    }

    @Override
    public Usuario fetchUsuarioById(int personalId) {

        final String selectionArgs[] = {String.valueOf(personalId)};
        final String selection = COLUMNA_ID + " = ?";
        Usuario usuario = new Usuario();
        cursor = super.query(TABLA_USUARIO, USUARIO_COLUMNAS, selection,
                selectionArgs, COLUMNA_ID);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                usuario = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return usuario;
    }

    @Override
    public List<Usuario> fetchAllUsuarios() {

        List<Usuario> usuarioList = new ArrayList<Usuario>();
        cursor = super.query(TABLA_USUARIO, USUARIO_COLUMNAS, null,
                null, COLUMNA_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Usuario usuario = cursorToEntity(cursor);
                usuarioList.add(usuario);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return usuarioList;

    }

    @Override
    public boolean addUsuario(Usuario usuario) {

        // set values
        setContentValue(usuario);
        try {
            return super.insert(TABLA_USUARIO, getContentValue()) > 0;
        } catch (SQLiteConstraintException ex){
            Log.w("Database", ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean addUsuarios(List<Usuario> usuarios) {

        if(usuarios!=null&&usuarios.size()>0) {

            for (int i = 0; i < usuarios.size(); i++) {
                addUsuario(usuarios.get(i));
            }
        }
        return false;
    }

    @Override
    public boolean deleteAllUsuarios() {
        System.out.println("Borrando los usuarios....");
        super.delete(TABLA_USUARIO,null,null);
        return false;
    }

    @Override
    protected Usuario cursorToEntity(Cursor cursor) {

        int idIndex;
        int nombreIndex;
        int emailIndex;
        int idTipoPersonalIndex;
        int usuarioIndex;
        int passwordIndex;
        int grupoIndex;
        int firmaIndex;

        Usuario personal = new Usuario();

        if (cursor != null) {
            if (cursor.getColumnIndex(COLUMNA_ID) != -1) {
                idIndex = cursor.getColumnIndexOrThrow(COLUMNA_ID);
                personal.setId(cursor.getInt(idIndex));
            }
            if (cursor.getColumnIndex(COLUMNA_NOMBRE) != -1) {
                nombreIndex = cursor.getColumnIndexOrThrow(
                        COLUMNA_NOMBRE);
                personal.setNombre(cursor.getString(nombreIndex));
            }
            if (cursor.getColumnIndex(COLUMNA_EMAIL) != -1) {
                emailIndex = cursor.getColumnIndexOrThrow(
                        COLUMNA_EMAIL);
                personal.setEmail(cursor.getString(emailIndex));
            }
            if (cursor.getColumnIndex(COLUMNA_TIPO_USUARIO_ID) != -1) {
                idTipoPersonalIndex = cursor.getColumnIndexOrThrow(COLUMNA_TIPO_USUARIO_ID);
                personal.setIdTipoPersonal(cursor.getInt(idTipoPersonalIndex));
            }
            if (cursor.getColumnIndex(COLUMNA_USUARIO) != -1) {
                usuarioIndex = cursor.getColumnIndexOrThrow(COLUMNA_USUARIO);
                personal.setUsuario(cursor.getString(usuarioIndex));

            }
            if (cursor.getColumnIndex(COLUMNA_PASSWORD) != -1) {
                passwordIndex = cursor.getColumnIndexOrThrow(COLUMNA_PASSWORD);
                personal.setPassword(cursor.getString(passwordIndex));

            }
            if (cursor.getColumnIndex(COLUMNA_GRUPO_MOVIL) != -1) {
                grupoIndex = cursor.getColumnIndexOrThrow(COLUMNA_GRUPO_MOVIL);
                personal.setGrupoMovil(cursor.getString(grupoIndex));
            }

            if (cursor.getColumnIndex(COLUMNA_FIRMA) != -1) {
                firmaIndex = cursor.getColumnIndexOrThrow(COLUMNA_FIRMA);
                personal.setFirma(cursor.getString(firmaIndex));

            }
        }
        return personal;
    }

    private ContentValues getContentValue() {
        return initialValues;
    }

    private void setContentValue(Usuario usuario) {
        initialValues = new ContentValues();
        initialValues.put(COLUMNA_NOMBRE, usuario.getNombre());
        initialValues.put(COLUMNA_EMAIL, usuario.getEmail());
        initialValues.put(COLUMNA_TIPO_USUARIO_ID, usuario.getIdTipoPersonal());
        initialValues.put(COLUMNA_USUARIO, usuario.getUsuario());
        initialValues.put(COLUMNA_PASSWORD, usuario.getPassword());
        initialValues.put(COLUMNA_GRUPO_MOVIL, usuario.getGrupoMovil());
        initialValues.put(COLUMNA_FIRMA, usuario.getFirma());

    }
}
