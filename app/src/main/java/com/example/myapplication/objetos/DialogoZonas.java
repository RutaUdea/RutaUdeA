package com.example.myapplication.objetos;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import com.example.myapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class DialogoZonas {

    FirebaseDatabase baseDatos;
    DatabaseReference bdReferencia;
    RutasBD ruta;
    SharedPreferences prefs;

    public  DialogoZonas(Context contexto)
    {
        final Dialog zonas=new Dialog(contexto);
        zonas.requestWindowFeature(Window.FEATURE_NO_TITLE);
        zonas.setCancelable(false);
        zonas.setContentView(R.layout.cuadro_compartir);

        final Spinner spinnerZonas=(Spinner) zonas.findViewById(R.id.spinerZonas);
        Button compartirRuta=(Button) zonas.findViewById(R.id.btnCompartirRuta);
        Button cancelarC=zonas.findViewById(R.id.btnCancelarC);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(contexto,
                R.array.zonas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerZonas.setAdapter(adapter);


        baseDatos=FirebaseDatabase.getInstance();
        bdReferencia=baseDatos.getReference();
        prefs=contexto.getSharedPreferences("Datos", Context.MODE_PRIVATE);

        compartirRuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String zona=spinnerZonas.getSelectedItem().toString();
                if(zona.equals("--")){
                    Snackbar.make(v,R.string.selccionarBarrio,Snackbar.LENGTH_SHORT).show();
                }
                else{
                    ruta=new RutasBD(prefs.getString("nombre","nombre")
                            ,prefs.getString("correo","correo")
                            ,prefs.getString("foto","foto")
                            ,prefs.getString("telefono","telefono")
                            ,prefs.getString("ruta","ruta")
                            ,spinnerZonas.getSelectedItem().toString()
                            ,prefs.getString("id","id"));
                    bdReferencia.child(prefs.getString("id","")).setValue(ruta);
                    zonas.dismiss();
                }
            }
        });

        cancelarC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zonas.dismiss();
            }
        });

        zonas.show();


    }
}
