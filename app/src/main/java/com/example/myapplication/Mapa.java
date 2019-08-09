package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

import com.example.myapplication.objetos.RutasBD;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Mapa extends Fragment {

    FirebaseDatabase baseDatos;
    DatabaseReference bdReferencia;
    RutasBD ruta;
    SharedPreferences prefs;
    Button fbnCompratir;

    public Mapa() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_mapa, container, false);

        MiUbicacion ubicacion1= new MiUbicacion(getActivity().getApplicationContext());
        String lati=Double.toString(ubicacion1.getLatitud());
        String longi=Double.toString(ubicacion1.getLongitud());

        WebView Map = (WebView) v.findViewById(R.id.WMaps);
        fbnCompratir= (Button) v.findViewById(R.id.fbtnCompartir);
        baseDatos=FirebaseDatabase.getInstance();
        bdReferencia=baseDatos.getReference();
        prefs=this.getActivity().getSharedPreferences("Datos", Context.MODE_PRIVATE);

        Map.getSettings().setJavaScriptEnabled(true);
        final String url="https://www.google.es/maps/dir/"+lati+","+longi+"/Universidad+de+Antioquia,+Cl.+67,+Medell%C3%ADn,+Antioquia/@"+lati+","+longi+",17.56z/data=!4m8!4m7!1m0!1m5!1m1!1s0x8e4428e0c9c8772f:0x10cce7ece69b2e2b!2m2!1d"+longi+"!2d"+lati;
        Map.loadUrl(url);

        fbnCompratir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ruta=new RutasBD(prefs.getString("nombre","nombre")
                        ,prefs.getString("correo","correo")
                        ,prefs.getString("foto","foto")
                        ,prefs.getString("telefono","telefono")
                        ,url);
                bdReferencia.child(prefs.getString("id","")).setValue(ruta);
            }
        });
        return v;
    }





}
