package com.example.myapplication;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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


    public Rutas() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_rutas, container, false);
        rv= (RecyclerView) v.findViewById(R.id.rvRutas);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        rutas= new ArrayList<>();

        FirebaseDatabase baseDatos= FirebaseDatabase.getInstance();

        adaptador=new Adaptador(rutas);
        rv.setAdapter(adaptador);

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

        return v;
    }

}
