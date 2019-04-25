package com.calymayor.linea12.services;

/**
 * Created by etorres on 27/02/17.
 */

import java.util.List;

import com.calymayor.linea12.modelos.Usuario;
import com.calymayor.linea12.modelos.Vista;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by etorres on 04/01/17.
 */

public interface ServiceAPI {


    //Métodos GET
    @GET("/usuarios/get.php")
    public Observable<List<Usuario>>
    getUsuarios();

    //Métodos GET
    /*@GET("/vistas/get.php")
    public Observable<List<Vista>>
    getVistas();*/

    //Vistas por grupo
    @GET("/vistas/get.php")
    public Observable<List<Vista>>
    getVistaPorGrupo(
            @Query("grupomovil") String grupoMovil
    );



}