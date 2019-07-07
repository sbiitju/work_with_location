package com.sbiitju.jugreenbus;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CheckForDriver extends AppCompatActivity {
    EditText loginnumber,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_for_driver);
        loginnumber=findViewById(R.id.loginphone);
        password=findViewById(R.id.loginpass);
    }

    public void Others(View view) {
        final String p=loginnumber.getText().toString();
        final String id="+88".concat(p);
        final String pass=password.getText().toString();
        final ProgressDialog dialog = new ProgressDialog(CheckForDriver.this);
        dialog.setMessage("Verifying to Sign In..");
        dialog.show();
        final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference(p);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    Password password=dataSnapshot.getValue(Password.class);
                    String p2=password.getP1();
                    String check=password.getP2();
                    if(pass.equals(p2)){
                        if(check.equals("Driver")){
                            startActivity(new Intent(CheckForDriver.this,DriverTask.class));
                            DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference(id);
                            databaseReference1.setValue("1");
                            finish();
                            dialog.hide();
                        }
                        else {
                            startActivity(new Intent(CheckForDriver.this,MainTaskForOthers.class));
                            DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference(id);
                            databaseReference1.setValue("1");
                            finish();
                            dialog.hide();
                        }
//                        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference(id);
//                        databaseReference.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                if(dataSnapshot.exists()){
//                                    Profile profile=dataSnapshot.getValue(Profile.class);
//                                    String batch=profile.getBatch();
//                                    if(batch.contains("Driver")){
//                                        startActivity(new Intent(CheckForDriver.this,DriverTask.class));
//                                        finish();
//                                    }
//                                    else{
//                                        startActivity(new Intent(CheckForDriver.this,MainTaskForOthers.class));
//                                        dialog.hide();
//                                        finish();
//                                    }
//                                }
//                                else {
//                                    Intent intent=new Intent(CheckForDriver.this,ProfileMake.class);
//                                    intent.putExtra("id",id);
//                                    startActivity(intent);
//                                    dialog.hide();
//                                    finish();
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//                                startActivity(new Intent(CheckForDriver.this,ProfileMake.class));
//                                finish();
//
//                            }
//                        });
                    }
                    else {
                        Toast.makeText(CheckForDriver.this, "Password did not match", Toast.LENGTH_SHORT).show();
                        dialog.hide();
                    }
                }
                else {
                    Toast.makeText(CheckForDriver.this, "Please Sign Up First", Toast.LENGTH_SHORT).show();
                    dialog.hide();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void Forgot(View view) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view1=getLayoutInflater().inflate(R.layout.forgot,null);
        final EditText phone,pass,pass2;
        Button save;
        phone=view1.findViewById(R.id.forgotnumber);
        pass=view1.findViewById(R.id.forgotpass);
        pass2=view1.findViewById(R.id.forgotpass2);
        save=view1.findViewById(R.id.forgotsave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p,p1,p2;
                p=phone.getText().toString();
                p1=pass.getText().toString();
                p2=pass2.getText().toString();
                if(p1.equals(p2)){
                    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference(p);
                    Password password=new Password(p1,p2);
                    databaseReference.setValue(password).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                       if(task.isSuccessful()){
                           Toast.makeText(CheckForDriver.this, "Password Succesfully changed", Toast.LENGTH_SHORT).show();
                       }
                       else {
                           Toast.makeText(CheckForDriver.this, "Failed", Toast.LENGTH_SHORT).show();
                       }
                        }
                    });

                }
            }
        });
        builder.setView(view1).show();
    }
}
