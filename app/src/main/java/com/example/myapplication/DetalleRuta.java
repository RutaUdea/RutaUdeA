package com.example.myapplication;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.objetos.RutasBD;

public class DetalleRuta extends AppCompatActivity {

    RutasBD datos;
    TextView nombre;
    ImageView foto;
    WebView mapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_ruta);
        datos=(RutasBD) getIntent().getSerializableExtra("RutasBD");

        nombre= (TextView) findViewById(R.id.detalleNombre);
        foto=(ImageView) findViewById(R.id.foroDetalle);
        mapa=(WebView) findViewById(R.id.wvDetalle);

        nombre.setText(datos.getNombre());
        Uri fotoU= Uri.parse(datos.getFoto());
        Glide.with(this).load(fotoU).into(foto);

        mapa.getSettings().setJavaScriptEnabled(true);
        mapa.loadUrl(datos.getRuta());

    }
}
