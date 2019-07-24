package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class Perfil extends Fragment implements GoogleApiClient.OnConnectionFailedListener {

    private ImageView fotoPerfil;
    private TextView nombrePerfil;
    private TextView correoPErfil;
    private TextView celularPerfil;
    private GoogleApiClient googleApiClient;

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
        celularPerfil=(TextView) v.findViewById(R.id.TVtelefono);

        GoogleSignInOptions gso= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient= new GoogleApiClient.Builder(getActivity().getApplicationContext())
                .enableAutoManage(getActivity(),this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
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
            GoogleSignInAccount cuenta=result.getSignInAccount();

            nombrePerfil.setText(cuenta.getDisplayName());
            correoPErfil.setText(cuenta.getEmail());
            Glide.with(this).load(cuenta.getPhotoUrl()).into(fotoPerfil);
        }
        else{
            IniciarLogin();
        }

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
