package com.calymayor.linea12.views;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.calymayor.linea12.R;
import com.calymayor.linea12.modelos.Usuario;
import com.calymayor.linea12.modelos.Vista;
import com.calymayor.linea12.presenters.AppBasePresenter;

public abstract class AppBaseActivity extends AppCompatActivity implements MenuItem.OnMenuItemClickListener {
    private FrameLayout view_stub; //This is the framelayout to keep your content view
    private NavigationView navigation_view; // The new navigation view from Android Design Library. Can inflate menu resources. Easy
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Menu drawerMenu;
    private Menu dynamicMenu;
    private List<Vista> vistas = new ArrayList<>();
    private AppBasePresenter presenter;
    private static final int PERMISSION_REQUEST_CODE = 1;
    //private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.app_base_layout);// The base layout that contains your navigation drawer.
        presenter = new AppBasePresenter(this);
        view_stub = (FrameLayout) findViewById(R.id.view_stub);
        navigation_view = (NavigationView) findViewById(R.id.navigation_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, 0, 0);
        //mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        vistas = presenter.createMenu(this);

        /*drawerMenu = navigation_view.getMenu();

        for(int i = 0; i < drawerMenu.size(); i++) {
            drawerMenu.getItem(i).setOnMenuItemClickListener(this);
        }*/

        dynamicMenu = navigation_view.getMenu();
        for (int i = 0; i < vistas.size(); i++) {
            //dynamicMenu.add(0,Menu.NONE,0,vistas.get(i).getNombre());
            dynamicMenu.add(0, vistas.get(i).getId(), Menu.NONE, vistas.get(i).getNombre());
        }
        dynamicMenu.add(0, 100, Menu.NONE, "Cerrar Sesión");


        for (int i = 0; i < dynamicMenu.size(); i++) {
            dynamicMenu.getItem(i).setOnMenuItemClickListener(this);
        }

        // and so on...
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /* Override all setContentView methods to put the content view to the FrameLayout view_stub
     * so that, we can make other activity implementations looks like normal activity subclasses.
     */
    @Override
    public void setContentView(int layoutResID) {
        if (view_stub != null) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            View stubView = inflater.inflate(layoutResID, view_stub, false);
            view_stub.addView(stubView, lp);
        }
    }

    @Override
    public void setContentView(View view) {
        if (view_stub != null) {
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            view_stub.addView(view, lp);
        }
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        if (view_stub != null) {
            view_stub.addView(view, params);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        //Obtener datos del usuario

        Intent intent1 = getIntent();
        Bundle b = intent1.getExtras();
        Usuario usuario = (Usuario) b.get("usuario");

        //return true;

        switch (item.getItemId()) {
            case R.id.nav_home:
                System.out.println("HOME");
                item.setChecked(true);

                Intent welcomeIntent = new Intent(this, BienvenidaActivity.class);
                welcomeIntent.putExtra("usuario",usuario);
                startActivity(welcomeIntent);
                //startActivity(new Intent(this, BienvenidaActivity.class));
                break;

            case 100:
                System.out.println("YA ME SACASTE :(");
                finish();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                startActivity(intent);
                break;

            default:
                for (int i = 0; i < vistas.size(); i++) {
                    if (item.getItemId() == vistas.get(i).getId()) {
                        String url = vistas.get(i).getLiga();
                        item.setChecked(true);
                        Intent myIntent = new Intent(this, GenericActivity.class);
                        myIntent.putExtra("proyecto_URL", url);
                        myIntent.putExtra("usuario",usuario);
                        System.out.println("El usuario es-->: "+usuario.getNombre());

                        if (presenter.validateInternet(this)) {
                            startActivity(myIntent);
                        } else
                            setInternetError();
                    }

                }

        }
        return false;
    }

    public void setInternetError() {
        Toast.makeText(this, "No hay conexión a Internet", Toast.LENGTH_LONG).show();
    }


}