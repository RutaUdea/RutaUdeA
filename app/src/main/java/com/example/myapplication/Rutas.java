package com.example.myapplication;


import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

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
        final AutoCompleteTextView spinnerZonas=(AutoCompleteTextView) v.findViewById(R.id.spinerZonasRutas);

        rutas= new ArrayList<>();

        final FirebaseDatabase baseDatos= FirebaseDatabase.getInstance();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(v.getContext(),
                R.array.zonas_filtro, android.R.layout.simple_spinner_dropdown_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerZonas.setAdapter(adapter);

        restriccion=spinnerZonas.getText().toString();

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

        spinnerZonas.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void afterTextChanged(Editable s) {
                restriccion =spinnerZonas.getText().toString();
                if(restriccion.equals("Todos") || restriccion.isEmpty())
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
        });



        return v;
    }

}
