package com.sbiitju.jugreenbus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpORSIgnIn extends AppCompatActivity {
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_orsign_in);
        getSupportActionBar().hide();
//        ActionBar actionBar=getSupportActionBar();
//        if(actionBar!=null){
//            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.headcolor));
//        }
    }

//    public void SignUP(View view) {
//        startActivity(new Intent(SignUpORSIgnIn.this,SignUp.class));
//
//    }
//
//    public void Login(View view) {
//        startActivity(new Intent(SignUpORSIgnIn.this,LogIn.class));
//    }

    public void SendOTP(View view) {
        EditText phonenumber=findViewById(R.id.phonenumber);
       // EditText pass,pass2;
//        pass=findViewById(R.id.pass);
//        pass2=findViewById(R.id.pass2);
        final String s=phonenumber.getText().toString();
        final String number="+88".concat(s);
//        final String p1=pass.getText().toString();
//        final String p2=pass2.getText().toString();
        final ProgressDialog dialog = new ProgressDialog(SignUpORSIgnIn.this);
        dialog.setMessage("Verifying Your Phone Number");
        dialog.show();
        final Intent intent=new Intent(SignUpORSIgnIn.this,Verification.class);
        intent.putExtra("number",number);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        System.exit(0);
        super.onBackPressed();
    }
}
