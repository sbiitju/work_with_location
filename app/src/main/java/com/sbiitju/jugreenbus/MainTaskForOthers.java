package com.sbiitju.jugreenbus;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainTaskForOthers extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseAuth firebaseAuth;
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_task_for_others);
        firebaseAuth=FirebaseAuth.getInstance();
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null) {
            actionBar.setTitle("JU Bus");
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.headcolor));
        }
//        getSupportActionBar().hide();

        final Animation animation2= AnimationUtils.loadAnimation(this,R.anim.anim2);
        LinearLayout linearLayout =  findViewById(R.id.linearheader);
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_EMAIL, "sbiitju@gmail.com");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Make a Review");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            System.exit(0);
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main_task_for_others, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            firebaseAuth.signOut();
//            startActivity(new Intent(MainTaskForOthers.this,SignUpORSIgnIn.class));
//            finish();
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.editprofile) {
            startActivity(new Intent(MainTaskForOthers.this,ProfileMake.class));

        } else if (id == R.id.aboutus) {
            startActivity(new Intent(MainTaskForOthers.this,AboutUs.class));

        }
        else if (id == R.id.nav_share) {
            Toast.makeText(this, "Share is called!!", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.logout) {
            firebaseAuth.signOut();
            startActivity(new Intent(MainTaskForOthers.this,SignUpas.class));
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }





    public void BusSchedule(View view) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view1=getLayoutInflater().inflate(R.layout.teacherstudent,null);
        Button teacher,student;
        teacher=view1.findViewById(R.id.teacher);
        student=view1.findViewById(R.id.student);
        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainTaskForOthers.this,TeacherRoot.class));
            }
        });
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainTaskForOthers.this,StudentRoot.class));

            }
        });
        builder.setView(view1).show();

    }



    public void Search(View view) {
        final Animation animation2= AnimationUtils.loadAnimation(this,R.anim.anim2);

        AlertDialog.Builder builder=new AlertDialog.Builder(MainTaskForOthers.this);
        View view1=getLayoutInflater().inflate(R.layout.selectroute,null);
        final Spinner time,route,fromto,bustype;
        FloatingActionButton search;
        time=view1.findViewById(R.id.time);
        route=view1.findViewById(R.id.route);
        fromto=view1.findViewById(R.id.fromto);
        bustype=view1.findViewById(R.id.bustype);
        search=view1.findViewById(R.id.searcbtn);
        search.startAnimation(animation2);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String check=fromto.getSelectedItem().toString();
                if (check.contains("Campus")) {
                    String t = time.getSelectedItem().toString();
                    String r = route.getSelectedItem().toString();
                    String b = bustype.getSelectedItem().toString();
                    value ="C".concat(t).concat(r).concat(b.substring(3,5));
                    getlatlon();
                }
                else {
                    String t = time.getSelectedItem().toString();
                    String r = route.getSelectedItem().toString();
                    String b = bustype.getSelectedItem().toString();
                    value ="D".concat(t).concat(r).concat(b.substring(3,5));
                    getlatlon();
                }
            }
        });
        builder.setView(view1).show();

    }

    public void ChatRoom(View view) {
        startActivity(new Intent(MainTaskForOthers.this,Messege.class));
    }
    private void getlatlon() {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference(value);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    LatLon latLon=dataSnapshot.getValue(LatLon.class);
                    Intent intent=new Intent(MainTaskForOthers.this,MapTask.class);
                    intent.putExtra("Lat",latLon.getLat());
                    intent.putExtra("Lon",latLon.getLon());
                    startActivity(intent);
                }
                else {
                    Toast.makeText(MainTaskForOthers.this, "No Bus Found...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
