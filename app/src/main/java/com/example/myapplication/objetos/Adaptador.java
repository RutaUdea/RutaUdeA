package com.example.myapplication.objetos;


import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.MapsActivity;
import com.example.myapplication.R;

import java.util.List;


public class Adaptador extends  RecyclerView.Adapter<Adaptador.cohesiveHolder>{

    List<RutasBD> rutas;

    public Adaptador(List<RutasBD> rutas) {
        this.rutas = rutas;
    }

    @NonNull
    @Override
    public cohesiveHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_lista,viewGroup,false);
       cohesiveHolder holder= new cohesiveHolder(v);
       return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final cohesiveHolder cohesiveHolder, int i) {
        final RutasBD ruta = rutas.get(i);
        cohesiveHolder.nombrel.setText(ruta.getNombre());
        cohesiveHolder.zonal.setText(ruta.getZona());
        String foto=ruta.getFoto();
        Uri myuri = Uri.parse(foto);
        if(!(foto.equals("foto")))
            Glide.with(cohesiveHolder.itemView.getContext()).load(myuri).into(cohesiveHolder.fotol);
        cohesiveHolder.imgRuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(cohesiveHolder.itemView.getContext(), MapsActivity.class);
                i.putExtra("RutasBD",ruta);
                cohesiveHolder.itemView.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rutas.size();
    }

    public static class cohesiveHolder extends RecyclerView.ViewHolder{

        TextView nombrel;
        ImageView fotol;
        TextView zonal;
        ImageView imgRuta;

        public cohesiveHolder(@NonNull View itemView) {
            super(itemView);
            nombrel= itemView.findViewById(R.id.tvNombrel);
            fotol=itemView.findViewById(R.id.ivFotol);
            zonal=itemView.findViewById(R.id.tvPartida);
            imgRuta=itemView.findViewById(R.id.imgRuts);
        }
    }
}
