package com.calymayor.linea12.presenters;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import com.calymayor.linea12.interactors.AppBaseInteractor;
import com.calymayor.linea12.modelos.Vista;
import com.calymayor.linea12.views.AppBaseActivity;


/**
 * Created by etorres on 28/12/16.
 */

public class AppBasePresenter {

    private AppBaseActivity appView;
    private AppBaseInteractor appBaseInteractor;

    public AppBasePresenter(AppBaseActivity appView) {
        this.appView = appView;
        this.appBaseInteractor = new AppBaseInteractor();
    }

    public List<Vista> createMenu (Context context){
        List<Vista> vistas = new ArrayList<>();
        System.out.println("Desde el presenter de AppBasePresenter");
        vistas = appBaseInteractor.createMenu(context);
        return vistas;
    }


    public boolean validateInternet(Context context) {
        boolean isOnline = false;
        isOnline = appBaseInteractor.isOnline(context);
        //isOnline = loginInteractor.isOnline(context,this);
        return isOnline;
    }


    public void onDestroy() {
        appView = null;

    }


    public void onUnavailableNetwork() {
        System.out.println("UnavailableNetwork");
        if(appView!=null)
        {
            appView.setInternetError();
        }
    }

    /*@Override
    public void onSuccess() {
        if(loginView !=null){
            loginView.navigateToHome();
        }

    }*/
}
