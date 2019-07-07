package com.sbiitju.jugreenbus;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sbiitju.jugreenbus.R;

public class DriverTask extends AppCompatActivity {
    CheckBox from,to;
    Spinner time,route,driverfromto,driverbustype;
    FloatingActionButton floatingActionButton;
    LocationManager locationManager;
    LocationListener locationListener;
    String lat,lon;
    FirebaseAuth firebaseAuth;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_task);
//        ActionBar actionBar=getSupportActionBar();
//        if(actionBar!=null){
//            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.headcolor));
//        }
        time = findViewById(R.id.drivertime);
        route = findViewById(R.id.driverroute);
        driverbustype = findViewById(R.id.driverbustype);
        driverfromto = findViewById(R.id.driverfromto);
        firebaseAuth=FirebaseAuth.getInstance();
        floatingActionButton = findViewById(R.id.driversharebtn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String check=driverfromto.getSelectedItem().toString();
                if (check.contains("Campus")) {
                    String t = time.getSelectedItem().toString();
                    String r = route.getSelectedItem().toString();
                    String b=driverbustype.getSelectedItem().toString();
                    String reference ="C".concat(t).concat(r).concat(b.substring(3,5));
                    Intent intent=new Intent(DriverTask.this,Share.class);
                    intent.putExtra("Reference",reference);
                    startActivity(intent);
                    finish();
                }
                else {
                    String t = time.getSelectedItem().toString();
                    String r = route.getSelectedItem().toString();
                    String b=driverbustype.getSelectedItem().toString();
                    String reference ="D".concat(t).concat(r).concat(b.substring(3,5));
                    Intent intent=new Intent(DriverTask.this,Share.class);
                    intent.putExtra("Reference",reference);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.about) {
            showabout();
            return true;
        }
        if (id == R.id.share) {
            share();
            return true;
        }
        if (id == R.id.sigout) {
            firebaseAuth.signOut();
            startActivity(new Intent(DriverTask.this,SignUpORSIgnIn.class));
            finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
    private void share() {
        Toast.makeText(this, "Share method call", Toast.LENGTH_SHORT).show();
    }

    private void showabout() {
        Toast.makeText(this, "about method call", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBackPressed() {
        System.exit(0);
        super.onBackPressed();
    }
}