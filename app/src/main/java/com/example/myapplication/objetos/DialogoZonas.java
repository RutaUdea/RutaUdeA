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
        final Spinner spinnerL=(Spinner) zonas.findViewById(R.id.spinerL);
        final Spinner spinnerM=(Spinner) zonas.findViewById(R.id.spinerM);
        final Spinner spinnerW=(Spinner) zonas.findViewById(R.id.spinerW);
        final Spinner spinnerJ=(Spinner) zonas.findViewById(R.id.spinerJ);
        final Spinner spinnerV=(Spinner) zonas.findViewById(R.id.spinerV);
        final Spinner spinnerS=(Spinner) zonas.findViewById(R.id.spinerS);
        Button compartirRuta=(Button) zonas.findViewById(R.id.btnCompartirRuta);
        Button cancelarC=zonas.findViewById(R.id.btnCancelarC);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(contexto,
                R.array.zonas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerZonas.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapterH = ArrayAdapter.createFromResource(contexto,
                R.array.horarios, R.layout.itemspinerh);
        adapterH.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerL.setAdapter(adapterH);
        spinnerM.setAdapter(adapterH);
        spinnerW.setAdapter(adapterH);
        spinnerJ.setAdapter(adapterH);
        spinnerV.setAdapter(adapterH);
        spinnerS.setAdapter(adapterH);

        baseDatos=FirebaseDatabase.getInstance();
        bdReferencia=baseDatos.getReference();
        prefs=contexto.getSharedPreferences("Datos", Context.MODE_PRIVATE);

        compartirRuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String horariosH= spinnerL.getSelectedItem().toString()+","+spinnerM.getSelectedItem().toString()+","+spinnerW.getSelectedItem().toString()+","+spinnerJ.getSelectedItem().toString()+","+spinnerV.getSelectedItem().toString()+","+spinnerS.getSelectedItem().toString()+",";
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
                            ,zona
                            ,prefs.getString("id","id")
                            ,horariosH);
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
