package com.calymayor.linea12.dataaccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import com.calymayor.linea12.daos.VistaDao;
import com.calymayor.linea12.modelos.Vista;
import com.calymayor.linea12.schemas.IUsuarioSchema;
import com.calymayor.linea12.daos.UsuarioDao;
import com.calymayor.linea12.schemas.IVistaSchema;


/**
 * Created by etorres on 27/01/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DBLinea12";
    private static final String DATABASE_NAME = "Linea12.db";
    // Increment DB Version on any schema change
    private static final int DATABASE_VERSION = 1;


    private UsuarioDao usuarioDao;
    private VistaDao vistaDao;

    //Para la sincronización con el web service
    //public ServiceManagerInteractor mServiceManager = new ServiceManagerInteractor();

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se crean las tablas de catálogos

        db.execSQL(IUsuarioSchema.TABLA_USUARIO_CREATE);
        db.execSQL(IVistaSchema.TABLA_VISTA_CREATE);

        //Inicialización de los objetos DAO

        usuarioDao = new UsuarioDao(db);
        vistaDao = new VistaDao(db);

        //Llenado de catálogos

        //initUsuarioTabla(usuarioDao);
        //initVistaTabla(vistaDao);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        Log.w(TAG, "Upgrading database from version "
                + oldVersion + " to "
                + newVersion + " which destroys all old data");


        db.execSQL("DROP TABLE IF EXISTS "
                + IUsuarioSchema.TABLA_USUARIO);

        onCreate(db);

    }



    public void initUsuarioTabla(UsuarioDao usuarioDao){

        //mServiceManager.getUsuariosFromWebService(personalDao);
    }

    public  void initVistaTabla (VistaDao vistaDao){

        ArrayList<Vista> vistas = new ArrayList<>();

        Vista vista1 = new Vista();
        vista1.setNombre("Avenida Pirana");
        vista1.setLiga("http://www.calymayor.com.mx/sigcdmx/m_avpirana.php");


        Vista vista2 = new Vista();
        vista2.setNombre("Demoliciones");
        vista2.setLiga("http://www.calymayor.com.mx/sigcdmx/m_demolicion.php");


        Vista vista3 = new Vista();
        vista3.setNombre("Carretera Xochimilco");
        vista3.setLiga("http://www.calymayor.com.mx/sigcdmx/m_carrxochimilco.php");

        Vista vista4 = new Vista();
        vista4.setNombre("Avenida Pirania");
        vista4.setLiga("http://www.calymayor.com.mx/sigcdmx/m_carrxochimilco.php");

        vistas.add(vista1);
        vistas.add(vista2);
        vistas.add(vista3);
        vistas.add(vista4);

        vistaDao.addVistas(vistas);

    }


}
