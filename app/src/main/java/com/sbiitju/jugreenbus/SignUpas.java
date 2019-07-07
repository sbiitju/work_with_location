package com.sbiitju.jugreenbus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_upas);
    }

    public void SignUP2(View view) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view1=getLayoutInflater().inflate(R.layout.check,null);
        Button driver,other;
        driver=view1.findViewById(R.id.driver);
        other=view1.findViewById(R.id.other);
        driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1=new AlertDialog.Builder(SignUpas.this);
                View view2=getLayoutInflater().inflate(R.layout.verify,null);
                final EditText otp,number;
                otp=view2.findViewById(R.id.ve);
                number=view2.findViewById(R.id.number);
                Button button=view2.findViewById(R.id.verify);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final ProgressDialog dialog=new ProgressDialog(SignUpas.this);
                        dialog.setMessage("Cheacking......");
                        dialog.show();
                        final String s=number.getText().toString();
                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference(s);
                        databaseReference1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                           if(dataSnapshot.exists()){
                               Toast.makeText(SignUpas.this, "An Account is found!! Please Sign In", Toast.LENGTH_SHORT).show();
                               dialog.hide();
                           }
                           else {
                               String n="+88".concat(s);
                               String o=otp.getText().toString();
                               if(o.contains("2522")){
                                   Intent intent=new Intent(SignUpas.this,Verification.class);
                                   intent.putExtra("number",n);
                                   intent.putExtra("Driver","Driver");
                                   DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference(s);
                                   Password password=new Password("2522","Driver");
                                   databaseReference.setValue(password);
                                   startActivity(intent);
                                   finish();
                                   dialog.hide();
                               }
                               else {
                                   Toast.makeText(SignUpas.this, "OTP is invalid!!", Toast.LENGTH_SHORT).show();
                                   dialog.hide();
                               }
                           }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });
                builder1.setView(view2).show();
            }
        });
        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpas.this,SignUpORSIgnIn.class));
            }
        });
        builder.setView(view1).show();
    }

    public void Login(View view) {
        startActivity(new Intent(SignUpas.this,CheckForDriver.class));
    }
}
