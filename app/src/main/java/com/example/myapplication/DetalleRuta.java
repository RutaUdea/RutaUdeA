package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.objetos.RutasBD;

public class DetalleRuta extends AppCompatActivity {

    RutasBD datos;
    TextView nombre;
    TextView hLunes;
    TextView hMartes;
    TextView hMiercoles;
    TextView hJueves;
    TextView hViernes;
    TextView hSabado;
    TextView hLunesR;
    TextView hMartesR;
    TextView hMiercolesR;
    TextView hJuevesR;
    TextView hViernesR;
    TextView hSabadoR;
    ImageView foto;
    WebView mapa;
    ImageButton msgWhatsapp;
    ImageButton msgGmail;
    ImageButton msgHangout;
    String horarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_ruta);
        datos=(RutasBD) getIntent().getSerializableExtra("RutasBD");

        nombre= (TextView) findViewById(R.id.detalleNombre);
        hLunes= findViewById(R.id.tvLunes);
        hMartes= findViewById(R.id.tvMartes);
        hMiercoles= findViewById(R.id.tvMiercoles);
        hJueves= findViewById(R.id.tvJueves);
        hViernes= findViewById(R.id.tvViernes);
        hSabado= findViewById(R.id.tvSabado);
        hLunesR= findViewById(R.id.tvLunesR);
        hMartesR= findViewById(R.id.tvMartesR);
        hMiercolesR= findViewById(R.id.tvMiercolesR);
        hJuevesR= findViewById(R.id.tvJuevesR);
        hViernesR= findViewById(R.id.tvViernesR);
        hSabadoR= findViewById(R.id.tvSabadoR);
        foto=(ImageView) findViewById(R.id.foroDetalle);
        mapa=(WebView) findViewById(R.id.wvDetalle);
        msgWhatsapp=(ImageButton) findViewById(R.id.imgBtnWhatsApp);
        msgGmail=(ImageButton) findViewById(R.id.imgBtnCorreo);
        msgHangout=(ImageButton) findViewById(R.id.imgBtnHangouts);

        horarios=datos.getHorarios();

        String[] horariosS=horarios.split(",");

        hLunes.setText(horariosS[0]);
        hMartes.setText(horariosS[1]);
        hMiercoles.setText(horariosS[2]);
        hJueves.setText(horariosS[3]);
        hViernes.setText(horariosS[4]);
        hSabado.setText(horariosS[5]);
        hLunesR.setText(horariosS[6]);
        hMartesR.setText(horariosS[7]);
        hMiercolesR.setText(horariosS[8]);
        hJuevesR.setText(horariosS[9]);
        hViernesR.setText(horariosS[10]);
        hSabadoR.setText(horariosS[11]);

        nombre.setText(datos.getNombre());
        Uri fotoU= Uri.parse(datos.getFoto());
        Glide.with(this).load(fotoU).into(foto);

        mapa.getSettings().setJavaScriptEnabled(true);
        mapa.loadUrl(datos.getRuta());

        msgWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(datos.getNumero().equals("telefono") || datos.getNumero().equals("Null"))
                {
                    Snackbar.make(v,R.string.noNumero,Snackbar.LENGTH_SHORT).show();
                }
                else
                {
                    Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=57"+datos.getNumero());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }

            }
        });

        msgGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail(v);
            }
        });

        msgHangout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://hangouts.google.com/chat/person/"+datos.getId());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

    }

    protected void sendEmail(View v) {
        String[] TO = {datos.getCorreo()};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        try {
            startActivity(Intent.createChooser(emailIntent, "Enviar email..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Snackbar.make(v,
                    "No tienes clientes de email instalados.", Snackbar.LENGTH_SHORT).show();
        }
    }
}
