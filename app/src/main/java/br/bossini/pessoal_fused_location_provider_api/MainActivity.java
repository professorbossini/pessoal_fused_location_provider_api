package br.bossini.pessoal_fused_location_provider_api;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {

    //private LocationListener locationListener;
    //private LocationManager locationManager;
    private FusedLocationProviderClient locationProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //locationManager = getSystemService(Context.LOCATION_SERVICE);
        locationProvider = new FusedLocationProviderClient(this);



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String []{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else{
            locationProvider.getLastLocation().addOnSuccessListener(
                    this,
                    new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            Toast.makeText(MainActivity.this, location.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
            );
            //locationManager.requestLocationUdpate....
            locationProvider.requestLocationUpdates(
                    LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY),
                    new LocationCallback(){
                        //onLocationChanged
                        @Override
                        public void onLocationResult(LocationResult locationResult) {
                            Toast.makeText(MainActivity.this, locationResult.toString(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    },
                    null
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
         == PackageManager.PERMISSION_GRANTED){
            locationProvider.getLastLocation().addOnSuccessListener(
                    this,
                    new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            Toast.makeText(MainActivity.this, location.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
            );
        }

    }
}
