package com.example.myapplication;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.objetos.Adaptador;
import com.example.myapplication.objetos.RutasBD;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Rutas extends Fragment {

    RecyclerView rv;
    List<RutasBD> rutas;
    Adaptador adaptador;
    String restriccion;


    public Rutas() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_rutas, container, false);
        rv= (RecyclerView) v.findViewById(R.id.rvRutas);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        final Spinner spinnerZonas=(Spinner) v.findViewById(R.id.spinerZonasRutas);

        rutas= new ArrayList<>();

        final FirebaseDatabase baseDatos= FirebaseDatabase.getInstance();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(v.getContext(),
                R.array.zonas, R.layout.item_spiner);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerZonas.setAdapter(adapter);



        restriccion=spinnerZonas.getSelectedItem().toString();

        adaptador=new Adaptador(rutas);
        rv.setAdapter(adaptador);

        spinnerZonas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                restriccion =spinnerZonas.getSelectedItem().toString();
                if(restriccion.equals("Todos"))
                {
                    baseDatos.getReference().getRoot().addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            rutas.removeAll(rutas);
                            for (DataSnapshot snapshot :
                                    dataSnapshot.getChildren()) {
                                RutasBD ruta=snapshot.getValue(RutasBD.class);
                                rutas.add(ruta);
                            }
                            adaptador.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else
                {
                    baseDatos.getReference().getRoot().orderByChild("zona").equalTo(restriccion).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            rutas.removeAll(rutas);
                            for (DataSnapshot snapshot :
                                    dataSnapshot.getChildren()) {
                                RutasBD ruta=snapshot.getValue(RutasBD.class);
                                rutas.add(ruta);
                            }
                            adaptador.notifyDataSetChanged();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return v;
    }

}
