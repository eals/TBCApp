package com.azwraith.apps.tbcapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class Timetable extends AppCompatActivity {

    TextView tv11,tv12,tv13,tv14,tv15,tv21,tv22,tv23,tv24,tv25,tv31,tv32,tv33,tv34,tv35,class_sche,sec;
    Button btn2;
    RadioGroup rg1,rg2;
    TextView txt,txt2;
    Button btn;
    RadioButton getbsc,getbba,geta_level,getday,getmorning,rb,rb2;
    String suburl = "class",suburl2 = "seciton";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
    }

    @Override
    protected void onStart() {
        super.onStart();
        tv11 = (TextView) findViewById(R.id.txt11);
        tv12 = (TextView) findViewById(R.id.txt12);
        tv13 = (TextView) findViewById(R.id.txt13);
        tv14 = (TextView) findViewById(R.id.txt14);
        tv15 = (TextView) findViewById(R.id.txt15);
        tv21 = (TextView) findViewById(R.id.txt21);
        tv22 = (TextView) findViewById(R.id.txt22);
        tv23 = (TextView) findViewById(R.id.txt23);
        tv24 = (TextView) findViewById(R.id.txt24);
        tv25 = (TextView) findViewById(R.id.txt25);
        tv31 = (TextView) findViewById(R.id.txt31);
        tv32 = (TextView) findViewById(R.id.txt32);
        tv33 = (TextView) findViewById(R.id.txt33);
        tv34 = (TextView) findViewById(R.id.txt34);
        tv35 = (TextView) findViewById(R.id.txt35);

        rg1 = (RadioGroup) findViewById(R.id.first);
        rg2 = (RadioGroup) findViewById(R.id.second);
        btn = (Button) findViewById(R.id.submit);
        txt = (TextView) findViewById(R.id.class_schedules);
        txt2 = (TextView)findViewById(R.id.section);
        Firebase.setAndroidContext(this);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radiobuttonid = rg1.getCheckedRadioButtonId();
                int radiobuttonid2 = rg2.getCheckedRadioButtonId();
                rb2 = (RadioButton) findViewById(radiobuttonid2);
                rb = (RadioButton) findViewById(radiobuttonid);
                suburl = rb.getText().toString();
                suburl2 = rb2.getText().toString();
                txt.setText(suburl);
                Firebase mRef = null;
                switch (suburl){
                    case "BBA":
                        mRef = new Firebase("https://blazing-heat-4318.firebaseio.com/");
                        Toast.makeText(Timetable.this,suburl,Toast.LENGTH_SHORT).show();
                        break;
                    case "BSC":
                        mRef = new Firebase("https://sectionbba.firebaseio.com/");
                        Toast.makeText(Timetable.this,"https://sectionbba.firebaseio.com/"+ suburl,Toast.LENGTH_SHORT).show();
                        break;
                    case "A_level":
                        mRef = new Firebase("https://alevel-5c0e8.firebaseio.com/");
                        Toast.makeText(Timetable.this,"a",Toast.LENGTH_SHORT).show();
                        break;
                }
                Firebase messagesRef11 = mRef.child("sub11");
                messagesRef11.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class);
                        tv11.setText(value);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
                });

                Firebase messagesRef21 = mRef.child("sub21");
                messagesRef21.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class);
                        tv21.setText(value);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
                });
                Firebase messagesRef31 = mRef.child("sub31");
                messagesRef31.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class);
                        tv31.setText(value);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
                });
                Firebase messagesRef12 = mRef.child("sub12");
                messagesRef11.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class);
                        tv12.setText(value);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
                });

                Firebase messagesRef22 = mRef.child("sub22");
                messagesRef22.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class);
                        tv22.setText(value);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
                });
                Firebase messagesRef32 = mRef.child("sub32");
                messagesRef31.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class);
                        tv32.setText(value);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
                });
                Firebase messagesRef13 = mRef.child("sub13");
                messagesRef13.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class);
                        tv13.setText(value);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
                });

                Firebase messagesRef23 = mRef.child("sub23");
                messagesRef23.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class);
                        tv23.setText(value);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
                });
                Firebase messagesRef33 = mRef.child("sub33");
                messagesRef33.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class);
                        tv33.setText(value);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
                });
                Firebase messagesRef14 = mRef.child("sub14");
                messagesRef14.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class);
                        tv14.setText(value);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
                });

                Firebase messagesRef24 = mRef.child("sub24");
                messagesRef24.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class);
                        tv24.setText(value);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
                });
                Firebase messagesRef34 = mRef.child("sub34");
                messagesRef34.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class);
                        tv34.setText(value);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
                });
                Firebase messagesRef15 = mRef.child("sub15");
                messagesRef15.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class);
                        tv15.setText(value);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
                });

                Firebase messagesRef25 = mRef.child("sub25");
                messagesRef25.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class);
                        tv25.setText(value);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
                });
                Firebase messagesRef35 = mRef.child("sub35");
                messagesRef35.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class);
                        tv35.setText(value);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
                });
            }
        });


    }
}
