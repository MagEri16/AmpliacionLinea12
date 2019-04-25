package com.calymayor.linea12.interactors;

import android.content.Context;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import com.calymayor.linea12.daos.VistaDao;
import com.calymayor.linea12.dataaccess.Database;
import com.calymayor.linea12.modelos.Usuario;
import com.calymayor.linea12.modelos.Vista;
import com.calymayor.linea12.services.Linea12Service;
import com.calymayor.linea12.utilities.InternetConnection;
import com.calymayor.linea12.utilities.PasswordParser;
import com.calymayor.linea12.daos.UsuarioDao;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by etorres on 28/12/16.
 */

public class LoginInteractorImpl implements LoginInteractor {

    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "metro:linea12", "gbarrera:gbarrera", "prueba:prueba", "etungui:DGOP&2017", "consulta:DGCon2017"
    };
    public static Database mDb;
    public static ServiceManagerInteractor mServiceManager;
    private static List<Usuario> usuarios;
    private List<String> REAL_CREDENTIALS = new ArrayList<String>();
    private Linea12Service linea12Service = new Linea12Service();
    private UsuarioDao usuarioDao;
    private VistaDao vistaDao;


    public void initApp(Context context) {
        mDb = new Database(context);
        mDb.open();
        //Esto se agregó....y es necesario validar internet
        System.out.println("Is online value:---" + isOnline(context));
        if (isOnline(context)) {
            System.out.println("Si tengo internet desde el initApp");
            mServiceManager = new ServiceManagerInteractor();
            usuarioDao = mDb.mUsuarioDao;
            usuarioDao.deleteAllUsuarios();
            mServiceManager.getUsuariosFromWebService(usuarioDao);
        }
    }

    @Override
    public void login(final String username, final String password, final OnLoginFinishedListener listener, Context context) {

        boolean error = false;

        String passwordMd5 = PasswordParser.convertPassMd5(password);
        System.out.println("password changed: " + passwordMd5);

        fillUpUsersArray();


        if (!isOnline(context, listener)) {
            error = true;
            listener.onUnavailableNetwork();
            return;
        }

        if (TextUtils.isEmpty(username)) {
            listener.onUsernameError();
            error = true;
            return;
        }
        if (TextUtils.isEmpty(password)) {
            listener.onPasswordError();
            error = true;
            return;
        }

        if (!validCredentials(username, passwordMd5)) {
            error = true;
            listener.onInvalidCredentials();
            return;
        }
        if (!error) {

            vistaDao = mDb.mVistaDao;
            vistaDao.deleteAllVistas();
            Usuario usuario = getUsuario(username);

            System.out.println("dentro del getPostsFromWebService de Vistas");
            linea12Service.getApi().getVistaPorGrupo(usuario.getGrupoMovil()).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<Vista>>() {
                        @Override
                        public void onCompleted() {
                            System.out.println("onCompleted Vistas");

                        }

                        @Override
                        public void onError(Throwable e) {
                            System.out.println("onError");
                            System.out.println(e.getLocalizedMessage());
                            System.out.println("No se porque me mandó aquí");


                        }

                        @Override
                        public void onNext(List<Vista> vistas) {
                            System.out.println("Tengo estas vistas " + vistas.size());
                            vistaDao = mDb.mVistaDao;
                            vistaDao.deleteAllVistas();
                            insertVistasToLocalDB(vistas, vistaDao);
                            listener.onSuccess();
                        }
                    });


        }

    }

    @Override
    public boolean isOnline(Context context, OnLoginFinishedListener listener) {
        boolean isOnline = false;

        if (InternetConnection.isConnectedToInternet(context)) {
            isOnline = true;
            System.out.println("Si hay internet!!");
        } else {
            isOnline = false;
            System.out.println("No hay internet!!");
        }

        /*if (isOnline == false)
            listener.onUnavailableNetwork();*/
        return isOnline;
    }

    public boolean isOnline(Context context) {
        boolean isOnline = false;

        if (InternetConnection.isConnectedToInternet(context)) {
            isOnline = true;
            System.out.println("Si hay internet!!");
        } else {
            isOnline = false;
            System.out.println("No hay internet!!");
        }

        return isOnline;
    }

    public void fillUpUsersArray() {

        usuarios = mDb.mUsuarioDao.fetchAllUsuarios();
        System.out.println("USUARIOS!!! " + usuarios.size());

        for (int i = 0; i < usuarios.size(); i++) {
            String dupla = usuarios.get(i).getUsuario() + ":" + usuarios.get(i).getPassword();
            REAL_CREDENTIALS.add(dupla);
            System.out.println("Dupla: " + dupla);
        }

    }

    public boolean validCredentials(String username, String password) {
        boolean valid = false;

        for (int i = 0; i < REAL_CREDENTIALS.size(); i++) {
            String credential = REAL_CREDENTIALS.get(i);
            String[] pieces = credential.split(":");
            if (pieces[0].equals(username)) {
                // Account exists, return true if the password matches.
                System.out.println("El username es " + username);
                valid = pieces[1].equals(password);
            }
        }


        System.out.println("Credenciales válidas?" + valid);
        return valid;
    }

    public boolean validCredentialsDummy(String username, String password) {
        boolean valid = false;

        for (String credential : DUMMY_CREDENTIALS) {
            String[] pieces = credential.split(":");
            if (pieces[0].equals(username)) {
                // Account exists, return true if the password matches.
                System.out.println("El username es " + username);
                valid = pieces[1].equals(password);
            }
        }
        System.out.println("Credenciales válidas?" + valid);
        return valid;
    }

    public Usuario getUsuario(String nombre) {
        Usuario personal = new Usuario();
        if (usuarios != null) {
            for (int i = 0; i < usuarios.size(); i++) {
                if (usuarios.get(i).getUsuario().equals(nombre))
                    personal = usuarios.get(i);
            }
        }

        return personal;
    }

    public void insertVistasToLocalDB(List<Vista> vistas, VistaDao mVistaDao) {
        System.out.println("dentro del insertVistasToLocalDB");
        mVistaDao.addVistas(vistas);
        System.out.println("al final del insertVistasToLocalDB");
    }

}
