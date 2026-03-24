package com.example.app1122;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Emergency extends AppCompatActivity {

    EditText e1n, e2n;
    Button b1j;

    FirebaseDatabase database;
    DatabaseReference myRef;

    FirebaseDatabase database2;
    DatabaseReference myRef2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        e1n = findViewById(R.id.e1);
        e2n = findViewById(R.id.e2);
        b1j = findViewById(R.id.b1);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("ab");
        myRef.setValue("");

        database2 = FirebaseDatabase.getInstance();
        myRef2 = database2.getReference("abc");
        myRef2.setValue("");

        b1j.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = String.valueOf(e1n.getText());
                myRef.setValue(a);
                String b = String.valueOf(e2n.getText());
                myRef2.setValue(b);
            }
        });

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String a = dataSnapshot.getValue(String.class);
                e1n.setText(a);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String b = dataSnapshot.getValue(String.class);
                e2n.setText(b);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
