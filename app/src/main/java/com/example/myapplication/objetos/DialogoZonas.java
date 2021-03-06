package com.example.myapplication.objetos;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.example.myapplication.MainActivity;
import com.example.myapplication.MapsActivity;
import com.google.android.material.snackbar.Snackbar;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class DialogoZonas extends DialogFragment {

    FirebaseDatabase baseDatos;
    DatabaseReference bdReferencia;
    RutasBD ruta;
    SharedPreferences prefs;
    Context context;
    Dialog zonas;

    AutoCompleteTextView spinnerZonas;
    Spinner spinnerL;
    Spinner spinnerM;
    Spinner spinnerW;
    Spinner spinnerJ;
    Spinner spinnerV;
    Spinner spinnerS;
    Spinner spinnerLR;
    Spinner spinnerMR;
    Spinner spinnerWR;
    Spinner spinnerJR;
    Spinner spinnerVR;
    Spinner spinnerSR;
    Button compartirRuta;


    public  DialogoZonas(final Context contexto, String zona)
    {
        this.context=contexto;
        zonas=new Dialog(contexto);
        zonas.requestWindowFeature(Window.FEATURE_NO_TITLE);
        zonas.setCancelable(false);
        zonas.setContentView(R.layout.cuadro_compartir);

        spinnerZonas=(AutoCompleteTextView) zonas.findViewById(R.id.spinerZonas);
        spinnerL=(Spinner) zonas.findViewById(R.id.spinerL);
        spinnerM=(Spinner) zonas.findViewById(R.id.spinerM);
        spinnerW=(Spinner) zonas.findViewById(R.id.spinerW);
        spinnerJ=(Spinner) zonas.findViewById(R.id.spinerJ);
        spinnerV=(Spinner) zonas.findViewById(R.id.spinerV);
        spinnerS=(Spinner) zonas.findViewById(R.id.spinerS);
        spinnerLR=(Spinner) zonas.findViewById(R.id.spinerLR);
        spinnerMR=(Spinner) zonas.findViewById(R.id.spinerMR);
        spinnerWR=(Spinner) zonas.findViewById(R.id.spinerWR);
        spinnerJR=(Spinner) zonas.findViewById(R.id.spinerJR);
        spinnerVR=(Spinner) zonas.findViewById(R.id.spinerVR);
        spinnerSR=(Spinner) zonas.findViewById(R.id.spinerSR);
        compartirRuta=(Button) zonas.findViewById(R.id.btnCompartirRuta);
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
        spinnerZonas.setText(zona);

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


        zonas.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.cancel();
                    return true;
                }
                return false;
            }
        });

        cancelarC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zonas.dismiss();
            }
        });
        zonas.show();

        detectarHorarios(spinnerL,spinnerLR);
        detectarHorarios(spinnerM,spinnerMR);
        detectarHorarios(spinnerW,spinnerWR);
        detectarHorarios(spinnerJ,spinnerJR);
        detectarHorarios(spinnerV,spinnerVR);
        detectarHorarios(spinnerS,spinnerSR);

        zonas.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        zonas.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);

    }

    public void detectarHorarios(final Spinner ida, final Spinner regreso){
        regreso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String l= ida.getItemAtPosition(ida.getSelectedItemPosition()).toString();
                int lunes=0;
                int lunesR=0;
                String lR=regreso.getItemAtPosition(position).toString();
                if(!(l.equals("No"))){
                    lunes=Integer.parseInt(l);
                }
                if(!(lR.equals("No"))){
                    lunesR=Integer.parseInt(lR);
                }
                if(lunesR<lunes){
                    Snackbar.make(view,"La hora de regreso deber ser mayor a la de ida",Snackbar.LENGTH_SHORT).show();
                    regreso.setSelection(0);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ida.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String lR= regreso.getItemAtPosition(regreso.getSelectedItemPosition()).toString();
                int lunes=0;
                int lunesR=0;
                String l=ida.getItemAtPosition(position).toString();
                if(!(l.equals("No"))){
                    lunes=Integer.parseInt(l);
                }
                if(!(lR.equals("No"))){
                    lunesR=Integer.parseInt(lR);
                }
                if(lunesR<lunes && lunesR!=0){
                    Snackbar.make(view,"La hora de ida deber ser menor a la de regreso",Snackbar.LENGTH_SHORT).show();
                    ida.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


}
