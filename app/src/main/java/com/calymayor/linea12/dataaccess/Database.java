package com.calymayor.linea12.dataaccess;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.calymayor.linea12.daos.UsuarioDao;
import com.calymayor.linea12.daos.VistaDao;

/**
 * Created by etorres on 27/01/17.
 */

public class Database {

    /*private static final String TAG = "MyDatabase";
    private static final String DATABASE_NAME = "my_database.db";*/
    // Increment DB Version on any schema change
    //private static final int DATABASE_VERSION = 1;

    private DatabaseHelper mDbHelper;
    private final Context mContext;


    public static UsuarioDao mUsuarioDao;
    public static VistaDao mVistaDao;




    public Database open() throws SQLException {
        mDbHelper = new DatabaseHelper(mContext);
        SQLiteDatabase mDb = mDbHelper.getWritableDatabase();

        mUsuarioDao = new UsuarioDao(mDb);
        mVistaDao = new VistaDao(mDb);
        System.out.println("He creado el DAO en el Database.open");
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public Database(Context context) {
        this.mContext = context;
    }

}
