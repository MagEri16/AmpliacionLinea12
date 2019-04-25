package com.calymayor.linea12.interactors;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import com.calymayor.linea12.daos.VistaDao;
import com.calymayor.linea12.dataaccess.Database;
import com.calymayor.linea12.modelos.Vista;
import com.calymayor.linea12.utilities.InternetConnection;

/**
 * Created by etorres on 11/06/17.
 */

public class AppBaseInteractor {

    private static List<Vista> vistas;

    public static Database mDb;
    public static ServiceManagerInteractor mServiceManager;
    private VistaDao vistaDao;

    public List<Vista> createMenu(Context context) {
        System.out.println("Desde el interactor AppBaseInteractor");
        mDb = new Database(context);
        List<Vista> vistas = new ArrayList<>();
        //Esto se agregó....y es necesario validar internet
        /*System.out.println("Is online value:---" +isOnline(context));
        if(isOnline(context)) {*/
            System.out.println("Si tengo internet desde el createMenu");
            mServiceManager = new ServiceManagerInteractor();
            vistaDao = mDb.mVistaDao;
            vistas = vistaDao.fetchAllVistas();
            System.out.println("Vistas:---->"+vistas.size());
            //mServiceManager.getUsuariosFromWebService(usuarioDao);
        /*}*/
        return  vistas;
    }



    public boolean isOnline(Context context) {
        boolean isOnline = false;

        if (InternetConnection.isConnectedToInternet(context)) {
            isOnline = true;
            System.out.println("Si hay internet en AppBase");
        } else {
            isOnline = false;
            System.out.println("No hay internet en AppBase!!");
        }

        return isOnline;
    }
}


