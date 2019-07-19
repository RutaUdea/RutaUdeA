package com.example.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Mapa.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Mapa#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Mapa extends Fragment {

    public Mapa() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_mapa, container, false);
        TextView ubicacion=(TextView) v.findViewById(R.id.TUbicacion);
        MiUbicacion ubicacion1= new MiUbicacion(getActivity().getApplicationContext());
        String lati=Double.toString(ubicacion1.getLatitud());
        String longi=Double.toString(ubicacion1.getLongitud());
        ubicacion.setText(lati+" , "+longi);
        WebView Map = (WebView) v.findViewById(R.id.WMaps);
        Map.getSettings().setJavaScriptEnabled(true);
        Map.loadUrl("https://www.google.es/maps/dir/"+lati+","+longi+"/Universidad+de+Antioquia,+Cl.+67,+Medell%C3%ADn,+Antioquia/@"+lati+","+longi+",17.56z/data=!4m8!4m7!1m0!1m5!1m1!1s0x8e4428e0c9c8772f:0x10cce7ece69b2e2b!2m2!1d"+longi+"!2d"+lati);
        return v;
    }





}
