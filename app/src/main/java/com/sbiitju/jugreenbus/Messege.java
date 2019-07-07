package com.sbiitju.jugreenbus;

import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class Messege extends AppCompatActivity {
    ListView listView;
    ImageButton imageButton;
    EditText messege;
    ArrayList<String> arrayList;
    String m;
    ArrayList<String> arrayList2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messege);
//        ActionBar actionBar=getSupportActionBar();
//        if(actionBar!=null){
//            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.headcolor));
//        }
        listView=findViewById(R.id.listview);
        imageButton=findViewById(R.id.imagebutton);
        messege=findViewById(R.id.textmsg);
        arrayList=new ArrayList();
        arrayList2=new ArrayList();
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Message");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                arrayList2.clear();
                if(dataSnapshot.exists()){
                    for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                        m=dataSnapshot1.getValue(String.class);
                        arrayList.add(m);
                    }
                    Collections.reverse(arrayList);

//                    for(int j=arrayList.size();j>=0;j--){
//                        arrayList2.add(arrayList.get(j));
//                    }

                }
                else {
                    Toast.makeText(Messege.this, "There are no message", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);

    }

    public void MSG(View view) {
        String msg=messege.getText().toString();
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Message");
        String key=databaseReference.push().getKey();
        databaseReference.child(key).setValue(msg);
        messege.setText("");
    }

}
