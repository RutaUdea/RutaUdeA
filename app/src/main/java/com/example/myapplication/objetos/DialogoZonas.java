package com.example.myapplication.objetos;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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

        final AutoCompleteTextView spinnerZonas=(AutoCompleteTextView) zonas.findViewById(R.id.spinerZonas);
        final Spinner spinnerL=(Spinner) zonas.findViewById(R.id.spinerL);
        final Spinner spinnerM=(Spinner) zonas.findViewById(R.id.spinerM);
        final Spinner spinnerW=(Spinner) zonas.findViewById(R.id.spinerW);
        final Spinner spinnerJ=(Spinner) zonas.findViewById(R.id.spinerJ);
        final Spinner spinnerV=(Spinner) zonas.findViewById(R.id.spinerV);
        final Spinner spinnerS=(Spinner) zonas.findViewById(R.id.spinerS);
        final Spinner spinnerLR=(Spinner) zonas.findViewById(R.id.spinerLR);
        final Spinner spinnerMR=(Spinner) zonas.findViewById(R.id.spinerMR);
        final Spinner spinnerWR=(Spinner) zonas.findViewById(R.id.spinerWR);
        final Spinner spinnerJR=(Spinner) zonas.findViewById(R.id.spinerJR);
        final Spinner spinnerVR=(Spinner) zonas.findViewById(R.id.spinerVR);
        final Spinner spinnerSR=(Spinner) zonas.findViewById(R.id.spinerSR);
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
        spinnerLR.setAdapter(adapterH);
        spinnerMR.setAdapter(adapterH);
        spinnerWR.setAdapter(adapterH);
        spinnerJR.setAdapter(adapterH);
        spinnerVR.setAdapter(adapterH);
        spinnerSR.setAdapter(adapterH);

        baseDatos=FirebaseDatabase.getInstance();
        bdReferencia=baseDatos.getReference();
        prefs=contexto.getSharedPreferences("Datos", Context.MODE_PRIVATE);

        compartirRuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String horariosH= spinnerL.getSelectedItem().toString()+","+spinnerM.getSelectedItem().toString()+","+spinnerW.getSelectedItem().toString()+","+spinnerJ.getSelectedItem().toString()+","+spinnerV.getSelectedItem().toString()+","+spinnerS.getSelectedItem().toString()+","+spinnerLR.getSelectedItem().toString()+","+spinnerMR.getSelectedItem().toString()+","+spinnerWR.getSelectedItem().toString()+","+spinnerJR.getSelectedItem().toString()+","+spinnerVR.getSelectedItem().toString()+","+spinnerSR.getSelectedItem().toString()+",";
                String zona=spinnerZonas.getText().toString();
                if(zona.equals("")){
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
        zonas.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        zonas.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);

    }
}
