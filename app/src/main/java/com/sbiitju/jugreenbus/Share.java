package com.sbiitju.jugreenbus;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sbiitju.jugreenbus.R;

public class Share extends AppCompatActivity {
    TextView textView;
    LocationManager locationManager;
    LocationListener locationListener;
    ImageButton button;
    ProgressDialog progressDialog;
    String reference;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        getSupportActionBar().hide();
        reference=getIntent().getStringExtra("Reference");
        final String value=reference;
        progressDialog=new ProgressDialog(Share.this);
        progressDialog.setMessage("Location Is shahring......");
        //textView = findViewById(R.id.textview);
        button = findViewById(R.id.button);
        final Animation animation2= AnimationUtils.loadAnimation(this,R.anim.anim2);
        Animation animation3= AnimationUtils.loadAnimation(this,R.anim.anim3);
        Animation animation= AnimationUtils.loadAnimation(Share.this,R.anim.animation);
        button.startAnimation(animation2);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //textView.append("\n" + location.getLatitude() + " " + location.getLongitude());
//                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
//                        Uri.parse("http://maps.google.com/maps?saddr="+location.getLatitude()+"&daddr="+location.getLongitude()));
//                startActivity(intent);
//                String value="geo:"+location.getLatitude()+","+location.getLongitude();
//                Uri gmmIntentUri = Uri.parse(value);
//                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//                mapIntent.setPackage("com.google.android.apps.maps");
//                startActivity(mapIntent);
                String lat=String.valueOf(location.getLatitude());
                String lon=String.valueOf(location.getLongitude());
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference(value);
                LatLon latLon=new LatLon(lat,lon);
                databaseReference.setValue(latLon).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Share.this, "Success", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(Share.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

//                Intent intent=new Intent(Bus.this,Select.class);
//                intent.putExtra("Lat",lat);
//                intent.putExtra("Lon",lon);
//                startActivity(intent);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));

            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            requestPermissions(new String[]{

                    Manifest.permission.INTERNET, Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, 10);
            return;
        } else {
            configButton();
        }
    }

    private void configButton() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                Toast.makeText(Share.this,reference, Toast.LENGTH_SHORT).show();
                if (ActivityCompat.checkSelfPermission(Share.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager
                        .PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Share.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    return;
                }
                locationManager.requestLocationUpdates("gps",
                        5000, 0, locationListener);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    configButton();
                }
                return;
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "লোকেশন শেয়ার  করতে অনলাইনে থাকুন!!", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }
}

