package com.example.myapplication;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.myapplication.objetos.RutasBD;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

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
    ImageButton msgWhatsapp;
    ImageButton msgGmail;
    ImageButton msgHangout;
    String horarios;

    private GoogleMap mMap;

    Boolean actualPosition = true;
    JSONObject jso;
    Double longitudOrigen, latitudOrigen;
    String url="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapdes);
        mapFragment.getMapAsync(this);

        datos=(RutasBD) getIntent().getSerializableExtra("RutasBD");

        url=datos.getRuta();

        nombre=  findViewById(R.id.detalleNombre);
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
        if(!(datos.getFoto().equals("foto")))
            Glide.with(this).load(fotoU).into(foto);


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


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {


                if (actualPosition){
                    latitudOrigen = location.getLatitude();
                    longitudOrigen = location.getLongitude();
                    actualPosition=false;

                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(new LatLng(6.252429419882802,-75.56922870565965))      // Sets the center of the map to Mountain View
                            .zoom(12)
                            .build();                   // Creates a CameraPosition from the builder
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            try {
                                jso = new JSONObject(response);
                                trazarRuta(jso);
                                Log.i("jsonRuta: ",""+response);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

                    queue.add(stringRequest);
                }
            }
        });



    }

    private void trazarRuta(JSONObject jso) {

        JSONArray jRoutes;
        JSONArray jLegs;
        JSONArray jSteps;

        try {
            jRoutes = jso.getJSONArray("routes");
            for (int i=0; i<jRoutes.length();i++){

                jLegs = ((JSONObject)(jRoutes.get(i))).getJSONArray("legs");

                for (int j=0; j<jLegs.length();j++){

                    jSteps = ((JSONObject)jLegs.get(j)).getJSONArray("steps");

                    for (int k = 0; k<jSteps.length();k++){
                        String polyline = ""+((JSONObject)((JSONObject)jSteps.get(k)).get("polyline")).get("points");
                        Log.i("end",""+polyline);
                        List<LatLng> list = PolyUtil.decode(polyline);
                        mMap.addPolyline(new PolylineOptions().addAll(list).color(Color.BLUE).width(15));

                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
