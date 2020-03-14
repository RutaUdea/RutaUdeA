package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

import com.example.myapplication.objetos.DialogoZonas;

public class Mapa extends Fragment {

    SharedPreferences prefs;
    FloatingActionButton fbtnCompartir;
    String lati="6.278967";
    String longi="-75.5663743";

    public Mapa() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_mapa, container, false);

        MiUbicacion ubicacion1= new MiUbicacion(getActivity().getApplicationContext());
        lati=Double.toString(ubicacion1.getLatitud());
        longi=Double.toString(ubicacion1.getLongitud());

        final WebView Map = (WebView) v.findViewById(R.id.WMaps);
        fbtnCompartir= (FloatingActionButton) v.findViewById(R.id.fab);
        prefs=this.getActivity().getSharedPreferences("Datos", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();

        Map.getSettings().setJavaScriptEnabled(true);
        final String[] url = {"https://www.google.es/maps/dir/" + lati + "," + longi + "/Universidad+de+Antioquia,+Cl.+67,+Medell%C3%ADn,+Antioquia/@" + lati + "," + longi + ",17.56z/data=!4m8!4m7!1m0!1m5!1m1!1s0x8e4428e0c9c8772f:0x10cce7ece69b2e2b!2m2!1d" + longi + "!2d" + lati};
        Map.loadUrl(url[0]);

        fbtnCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url[0] =Map.getUrl();
                editor.putString("ruta", url[0]);
                editor.commit();
                new DialogoZonas(getActivity());
            }
        });
        return v;
    }





}
