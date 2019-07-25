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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private Button BCerrarSession;
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
        BCerrarSession=(Button) v.findViewById(R.id.BLogOut);

        GoogleSignInOptions gso= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient= new GoogleApiClient.Builder(getActivity().getApplicationContext())
                .enableAutoManage(getActivity(),this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        BCerrarSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if(status.isSuccess()){
                            goLoginScreen();
                        }else{
                            Toast.makeText(getContext(),R.string.NoLogOut,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
            GoogleSignInAccount cuenta=result.getSignInAccount();
            nombrePerfil.setText(cuenta.getDisplayName());
            correoPErfil.setText(cuenta.getEmail());
            Glide.with(this).load(cuenta.getPhotoUrl()).into(fotoPerfil);
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
