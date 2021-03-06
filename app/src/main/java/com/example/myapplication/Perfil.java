package com.example.myapplication;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;


/**
 * A simple {@link Fragment} subclass.
 */
public class Perfil extends Fragment implements GoogleApiClient.OnConnectionFailedListener {

    private ImageView fotoPerfil;
    private TextView nombrePerfil;
    private TextView correoPErfil;
    private TextView celularPerfil;
    private Button bCerrarSession;
    private Button btnActualizar;
    private EditText etNumero;
    public GoogleApiClient googleApiClient;
    public SharedPreferences prefs;
    public GoogleSignInAccount cuenta;
    public SharedPreferences.Editor editor;


    public Perfil() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_perfil, container, false);

        fotoPerfil=(ImageView) v.findViewById(R.id.IVfoto);
        nombrePerfil=(TextView) v.findViewById(R.id.TVnombre);
        correoPErfil= (TextView) v.findViewById(R.id.TVcorreo);
        bCerrarSession =(Button) v.findViewById(R.id.BLogOut);
        btnActualizar=(Button) v.findViewById(R.id.btnActualizar);
        etNumero=(EditText) v.findViewById(R.id.etNumero);

        prefs=this.getActivity().getSharedPreferences("Datos", Context.MODE_PRIVATE);
        editor= prefs.edit();

        String tel=prefs.getString("telefono","None");
        if(tel.equals("None") || tel.equals("Null")){
            etNumero.setHint(R.string.DefaultWhatsapp);
        }
        else
        {
            etNumero.setText(tel);
        }

        GoogleSignInOptions gso= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient= new GoogleApiClient.Builder(getActivity().getApplicationContext())
                .enableAutoManage(getActivity(),this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        bCerrarSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if(status.isSuccess()){
                            goLoginScreen();
                        }else{
                            Snackbar.make(v,R.string.NoLogOut,Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cadena="";
                String numerot="Null";
                if(cadena.compareTo(etNumero.getText().toString())!=0){
                    numerot=etNumero.getText().toString();
                }
                editor.putString("telefono",numerot);
                editor.commit();

            }
        });
        return  v;
    }

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr= Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if(opr.isDone()){
            GoogleSignInResult result= opr.get();
            ObteenerResutlado(result);
        }
        else{
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    ObteenerResutlado(googleSignInResult);
                }
            });
        }

    }

    private void ObteenerResutlado(GoogleSignInResult result) {
        if(result.isSuccess()){
            cuenta=result.getSignInAccount();
            String nombre=cuenta.getDisplayName();
            String correo=cuenta.getEmail();
            Uri foto=cuenta.getPhotoUrl();
            String id=cuenta.getId();
            nombrePerfil.setText(cuenta.getDisplayName());
            correoPErfil.setText(correo);
            editor.putString("nombre",nombre);
            editor.putString("correo",correo);
            editor.putString("id",id);
            if(!(cuenta.getPhotoUrl()==null)) {
                editor.putString("foto", foto.toString());
                Glide.with(this).load(foto).into(fotoPerfil);
            }
            editor.commit();
        }
        else{
            IniciarLogin();
        }
    }



    private void goLoginScreen(){
        Intent i= new Intent(getActivity(), LoginGoogle.class);
        getActivity().startActivity(i);
    }

    private void IniciarLogin() {
        Intent i= new Intent(getActivity(), LoginGoogle.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        getActivity().startActivity(i);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
