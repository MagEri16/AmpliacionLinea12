package com.calymayor.linea12.presenters;

import android.content.Context;

import com.calymayor.linea12.interactors.LoginInteractor;
import com.calymayor.linea12.interactors.LoginInteractorImpl;
import com.calymayor.linea12.modelos.Usuario;
import com.calymayor.linea12.views.LoginActivity;


/**
 * Created by etorres on 28/12/16.
 */

public class LoginPresenter implements LoginInteractor.OnLoginFinishedListener {

    private LoginActivity loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenter(LoginActivity loginView) {
        this.loginView = loginView;
        this.loginInteractor = new LoginInteractorImpl();
    }

    public void initApp(Context context){
        System.out.println("Init app: LoginPresenter");
        loginInteractor.initApp(context);
    }

    public Usuario getUsuario(String username){
        Usuario usuario = new Usuario();
        usuario = loginInteractor.getUsuario(username);
        return  usuario;
    }

    public void validateCredentials(String username, String password, Context context) {
        System.out.println("Login: "+username);
        System.out.println("Password: "+password);

        if (loginView != null) {
            loginView.showProgress();
        }

        loginInteractor.login(username, password, this, context);
    }


    public boolean validateInternet(Context context) {
        boolean isOnline = false;
        isOnline = loginInteractor.isOnline(context,this);
        return isOnline;
    }


    public void onDestroy() {
        loginView = null;

    }

    @Override
    public void onUsernameError() {
        if (loginView != null) {
            loginView.setUsernameError();
            loginView.hideProgress();
        }

    }

    @Override
    public void onPasswordError() {
        if (loginView != null) {
            loginView.setPasswordError();
            loginView.hideProgress();
        }

    }

    public void onInvalidCredentials(){
        System.out.println("OnInvalidCredentials");
        System.out.println("Sus credenciales son inválidas!!!");
        if(loginView!=null){
            loginView.setCredentialsError();
            loginView.hideProgress();
        }
    }

    @Override
    public void onUnavailableNetwork() {
        System.out.println("UnavailableNetwork");
        if(loginView!=null)
        {
            loginView.setInternetError();
        }
    }

    @Override
    public void onSuccess() {
        if(loginView !=null){
            loginView.navigateToHome();
        }

    }
}
