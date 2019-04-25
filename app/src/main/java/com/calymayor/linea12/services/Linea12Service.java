package com.calymayor.linea12.services;

/**
 * Created by etorres on 02/08/17.
 */

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

public class Linea12Service {

    //private static final String FORUM_SERVER_URL = "http://www.calymayor.com.mx/sigCDMX/json/usuarios/get.php";
    private static final String FORUM_SERVER_URL = "http://www.calymayor.com.mx/linea12/json/";


    private ServiceAPI mForumApi;

    public Linea12Service() {

        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Accept", "application/json");
            }
        };

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(FORUM_SERVER_URL)
                .setRequestInterceptor(requestInterceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        mForumApi = restAdapter.create(ServiceAPI.class);

    }

    public ServiceAPI getApi() {

        return mForumApi;
    }
}
