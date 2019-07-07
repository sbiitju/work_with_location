package com.sbiitju.jugreenbus;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    TextView textView1,textView2,s;
    int i;
    FirebaseAuth firebaseAuth;
    ImageView  imageView,logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar=findViewById(R.id.progress);
        getSupportActionBar().hide();
        imageView=findViewById(R.id.ju);
//        textView1=findViewById(R.id.tex1);
//        textView2=findViewById(R.id.tex2);
//        s=findViewById(R.id.sobujbus);
        logo=findViewById(R.id.logo);
        firebaseAuth=FirebaseAuth.getInstance();
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.animation);
        Animation animation2= AnimationUtils.loadAnimation(this,R.anim.anim2);
        Animation animation3= AnimationUtils.loadAnimation(this,R.anim.anim3);
        imageView.startAnimation(animation);
//        textView1.startAnimation(animation3);
//        textView2.startAnimation(animation3);
        //s.startAnimation(animation3);
        //logo.startAnimation(animation2);

        final Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {

                dowork();
                boolean state=isNetworkAvailable();
                if(state==true){
                    newact();
                }

            }
        });

        thread.start();
    }
    public void dowork(){
        for(i=0;i<=100;i=i+25) {
            try {
                Thread.sleep(500);
                try{
                    progressBar.setProgress(i);

                }catch (NullPointerException ignored){

                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void newact(){
        if(firebaseAuth.getCurrentUser()!=null){
            final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference(firebaseAuth.getCurrentUser().getPhoneNumber());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        Profile profile=dataSnapshot.getValue(Profile.class);
                        String batch=profile.getBatch();
                        if(batch.contains("Driver")){
                            startActivity(new Intent(MainActivity.this,DriverTask.class));
                            finish();
                        }
                        else{
                            startActivity(new Intent(MainActivity.this,MainTaskForOthers.class));
                            finish();
                        }
                    }
                    else {
                        startActivity(new Intent(MainActivity.this,ProfileMake.class));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else {
            Intent intent=new Intent(this,SignUpas.class);
            startActivity(intent);
            finish();
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
