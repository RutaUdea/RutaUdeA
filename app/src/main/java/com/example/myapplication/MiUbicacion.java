package com.example.myapplication;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

import androidx.core.app.ActivityCompat;

public class MiUbicacion extends Service implements LocationListener {

    private final Context ctx;
    private double latitud;
    private double longitud;
    private Location location;
    private boolean gpsActivo;
    private LocationManager locationManager;

    public MiUbicacion() {
        super();
        this.ctx = this.getApplicationContext();
    }

    public MiUbicacion(Context c) {
        super();
        this.ctx = c;
        obtenerUbicacion();
    }



    public void obtenerUbicacion() {
        try {
            locationManager = (LocationManager) this.ctx.getSystemService(LOCATION_SERVICE);
            gpsActivo = locationManager.isProviderEnabled(locationManager.GPS_PROVIDER);
        } catch (Exception e) {
        }
        if (gpsActivo) {
            if (ActivityCompat.checkSelfPermission(this.ctx.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            try
            {
                locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER
                        , 1000 * 60
                        , 10
                        , this);
            }catch (SecurityException ex){}

            location=locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
            longitud=location.getLongitude();
            latitud=location.getLatitude();
            if(location.hasAccuracy())
            {
                locationManager.removeUpdates(this);
            }
        }
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
