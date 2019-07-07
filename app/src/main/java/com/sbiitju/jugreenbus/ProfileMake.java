package com.sbiitju.jugreenbus;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileMake extends AppCompatActivity {

    EditText name,dep,mobile;
    Spinner batch;
    String n,d,m,b,value;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_make);
        //ActionBar actionBar=getSupportActionBar();
        firebaseAuth=FirebaseAuth.getInstance();
//        if(firebaseAuth.getCurrentUser()==null){
//            Toast.makeText(this, "You are not a valid user", Toast.LENGTH_SHORT).show();
//        startActivity(new Intent(this,CheckForDriver.class));
//        finish();
//        }
//        if(actionBar!=null){
//            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.headcolor));
//        }
        value=getIntent().getStringExtra("number");
        name=findViewById(R.id.profilename);
        dep=findViewById(R.id.profiledepartment);
        mobile=findViewById(R.id.profilenumber);
        batch=findViewById(R.id.profilespinner);
    }

    public void Submit(View view) {
        n=name.getText().toString();
        d=dep.getText().toString();
        m=mobile.getText().toString();
        b=batch.getSelectedItem().toString();
        Profile profile=new Profile(n,d,m,b);
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference(firebaseAuth.getCurrentUser().getPhoneNumber());
        databaseReference.setValue(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()&&b.contains("Driver")){
                    startActivity(new Intent(ProfileMake.this,DriverTask.class));
                    finish();
                }
                else {
                    startActivity(new Intent(ProfileMake.this,MainTaskForOthers.class));
                    finish();
                }
            }
        });
    }
}
