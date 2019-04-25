package com.calymayor.linea12.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.calymayor.linea12.R;

public class GenericActivity extends AppBaseActivity {

    private WebView navegador;
    String proyecto_URL = "";
    private static final FrameLayout.LayoutParams ZOOM_PARAMS =
            new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_general);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        proyecto_URL = (String) b.get("proyecto_URL");

        navegador = (WebView) findViewById(R.id.general_page);

        navegador.getSettings().setJavaScriptEnabled(true);
        //navegador.getSettings().setDisplayZoomControls(true);
        navegador.getSettings().setSupportZoom(true);
        navegador.getSettings().setBuiltInZoomControls(true);


        navegador.loadUrl(proyecto_URL);

        //Código añadido 22/10/2017
        navegador.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // open in Webview
                if (url.contains("calymayor") ){
                    // Can be clever about it like so where myshost is defined in your strings file
                    // if (Uri.parse(url).getHost().equals(getString(R.string.myhost)))
                    return false;
                }
                // open rest of URLS in default browser
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }
        });
        //Código añadico 22/10/2017

    }

    public String getProyecto_URL() {
        return proyecto_URL;
    }

    public void setProyecto_URL(String proyecto_URL) {
        this.proyecto_URL = proyecto_URL;
    }
}