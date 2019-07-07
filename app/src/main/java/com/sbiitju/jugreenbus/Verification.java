package com.sbiitju.jugreenbus;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sbiitju.jugreenbus.R;

import java.util.concurrent.TimeUnit;

public class Verification extends AppCompatActivity {
    String verificationcode;
    FirebaseAuth firebaseAuth;
    EditText code;
    String driver,p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        firebaseAuth=FirebaseAuth.getInstance();
        code=findViewById(R.id.verification);
        String phone=getIntent().getStringExtra("number");
        p=phone;
        String value=getIntent().getStringExtra("Driver");
        sendverificationcode(phone);
    }
    private void sendverificationcode(String phone) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phone,60, TimeUnit.SECONDS,
                this,mcallbacks);
    }
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationcode=s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code=phoneAuthCredential.getSmsCode();
            if(code!=null){
                verifycode(code);
            }


        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(Verification.this, e.toString(), Toast.LENGTH_SHORT).show();

        }
    };

    public void Verify(View view) {
        if(code.getText().toString()==null){
            Toast.makeText(this, "Enter your varification code", Toast.LENGTH_SHORT).show();
        }
        else {
            verifycode(code.getText().toString());

        }
    }

    private void verifycode(String code) {
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationcode,code);
        signinwithcredintial(credential);
    }

    private void signinwithcredintial(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    final ProgressDialog dialog = new ProgressDialog(Verification.this);
                    dialog.setMessage("Verifying to Sign In..");
                    dialog.show();
                    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
//                    String id=firebaseAuth.getCurrentUser().getPhoneNumber();
//                    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference(id);
//                    databaseReference.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            if(dataSnapshot.exists()){
//                                Profile profile=dataSnapshot.getValue(Profile.class);
//                                String batch=profile.getBatch();
//                                if(batch.contains("Driver")){
//                                    startActivity(new Intent(Verification.this,DriverTask.class));
//                                    finish();
//                                }
//                                else{
//                                    startActivity(new Intent(Verification.this,MainTaskForOthers.class));
//                                    dialog.hide();
//                                    finish();
//                                }
//                            }
//                            else {
//                                startActivity(new Intent(Verification.this,ProfileMake.class));
//                                dialog.hide();
//                                finish();
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//                            startActivity(new Intent(Verification.this,ProfileMake.class));
//                            finish();
//
//                        }
//                    });
                    dialog.show();
                    Intent intent=new Intent(Verification.this,ProfileMake.class);
                    intent.putExtra("driver",driver);
//                    intent.putExtra("id",id);
                    startActivity(intent);
                    finish();
                    dialog.hide();
                }
                else{
                    Toast.makeText(Verification.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Verification.this,SignUpORSIgnIn.class));
        super.onBackPressed();
    }

    public void SendAgain(View view) {
        sendverificationcode(p);
        Toast.makeText(this, "Wait a minute or check your internet connection", Toast.LENGTH_SHORT).show();
    }
}
