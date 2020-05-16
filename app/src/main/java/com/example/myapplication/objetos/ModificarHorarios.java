package com.example.myapplication.objetos;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.MainActivity;
import com.example.myapplication.MapsActivity;
import com.example.myapplication.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ModificarHorarios extends DialogFragment {

    FirebaseDatabase baseDatos;
    DatabaseReference bdReferencia;
    RutasBD ruta;
    SharedPreferences prefs;
    Context context;
    Dialog zonas;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View zonas = inflater.inflate(R.layout.modificar_horarios,container,false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCancelable(false);

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


        ArrayAdapter<CharSequence> adapterH = ArrayAdapter.createFromResource(getContext(),
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
        prefs=getContext().getSharedPreferences("Datos", Context.MODE_PRIVATE);

        ObtenerHorariosActuales();

        compartirRuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String horariosH= spinnerL.getSelectedItem().toString()+","+spinnerM.getSelectedItem().toString()+","+spinnerW.getSelectedItem().toString()+","+spinnerJ.getSelectedItem().toString()+","+spinnerV.getSelectedItem().toString()+","+spinnerS.getSelectedItem().toString()+","+spinnerLR.getSelectedItem().toString()+","+spinnerMR.getSelectedItem().toString()+","+spinnerWR.getSelectedItem().toString()+","+spinnerJR.getSelectedItem().toString()+","+spinnerVR.getSelectedItem().toString()+","+spinnerSR.getSelectedItem().toString()+",";
                ruta=new RutasBD(prefs.getString("nombre","nombre")
                        ,prefs.getString("correo","correo")
                        ,prefs.getString("foto","foto")
                        ,prefs.getString("telefono","telefono")
                        ,prefs.getString("ruta","ruta")
                        ,prefs.getString("zona","zona")
                        ,prefs.getString("id","id")
                        ,horariosH);
                bdReferencia.child(prefs.getString("id","")).setValue(ruta);
                actualizarHorarios();
                getDialog().dismiss();
            }
        });


        cancelarC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        detectarHorarios(spinnerL,spinnerLR);
        detectarHorarios(spinnerM,spinnerMR);
        detectarHorarios(spinnerW,spinnerWR);
        detectarHorarios(spinnerJ,spinnerJR);
        detectarHorarios(spinnerV,spinnerVR);
        detectarHorarios(spinnerS,spinnerSR);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        return zonas;

    }

    private void ObtenerHorariosActuales() {
        int pos=obtenerIndice(((MapsActivity)getActivity()).hLunes.getText().toString());
        spinnerL.setSelection(pos);
        pos=obtenerIndice(((MapsActivity)getActivity()).hMartes.getText().toString());
        spinnerM.setSelection(pos);
        pos=obtenerIndice(((MapsActivity)getActivity()).hMiercoles.getText().toString());
        spinnerW.setSelection(pos);
        pos=obtenerIndice(((MapsActivity)getActivity()).hJueves.getText().toString());
        spinnerJ.setSelection(pos);
        pos=obtenerIndice(((MapsActivity)getActivity()).hViernes.getText().toString());
        spinnerV.setSelection(pos);
        pos=obtenerIndice(((MapsActivity)getActivity()).hSabado.getText().toString());
        spinnerS.setSelection(pos);
        pos=obtenerIndice(((MapsActivity)getActivity()).hLunesR.getText().toString());
        spinnerLR.setSelection(pos);
        pos=obtenerIndice(((MapsActivity)getActivity()).hMartesR.getText().toString());
        spinnerMR.setSelection(pos);
        pos=obtenerIndice(((MapsActivity)getActivity()).hMiercolesR.getText().toString());
        spinnerWR.setSelection(pos);
        pos=obtenerIndice(((MapsActivity)getActivity()).hJuevesR.getText().toString());
        spinnerJR.setSelection(pos);
        pos=obtenerIndice(((MapsActivity)getActivity()).hViernesR.getText().toString());
        spinnerVR.setSelection(pos);
        pos=obtenerIndice(((MapsActivity)getActivity()).hSabadoR.getText().toString());
        spinnerSR.setSelection(pos);
    }

    private void actualizarHorarios(){
        ((MapsActivity)getActivity()).hLunes.setText(spinnerL.getSelectedItem().toString());
        ((MapsActivity)getActivity()).hMartes.setText(spinnerM.getSelectedItem().toString());
        ((MapsActivity)getActivity()).hMiercoles.setText(spinnerW.getSelectedItem().toString());
        ((MapsActivity)getActivity()).hJueves.setText(spinnerJ.getSelectedItem().toString());
        ((MapsActivity)getActivity()).hViernes.setText(spinnerV.getSelectedItem().toString());
        ((MapsActivity)getActivity()).hSabado.setText(spinnerS.getSelectedItem().toString());
        ((MapsActivity)getActivity()).hLunesR.setText(spinnerLR.getSelectedItem().toString());
        ((MapsActivity)getActivity()).hMartesR.setText(spinnerMR.getSelectedItem().toString());
        ((MapsActivity)getActivity()).hMiercolesR.setText(spinnerWR.getSelectedItem().toString());
        ((MapsActivity)getActivity()).hJuevesR.setText(spinnerJR.getSelectedItem().toString());
        ((MapsActivity)getActivity()).hViernesR.setText(spinnerVR.getSelectedItem().toString());
        ((MapsActivity)getActivity()).hSabadoR.setText(spinnerSR.getSelectedItem().toString());
    }

    private int obtenerIndice(String opcion){
        if(opcion.equals("No")){
            return 0;
        }else{
            int pos=Integer.parseInt(opcion);
            pos=pos-5;
            return pos;
        }
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
