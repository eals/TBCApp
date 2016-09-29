package com.azwraith.apps.tbcapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

/**
 * Created by B50i7D on 9/29/2016.
 */
public class SubEbookActivity extends AppCompatActivity {
    CardView maths,networking,sad,programming;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subebook_activity);
        maths = (CardView) findViewById(R.id.maths);
        networking = (CardView) findViewById(R.id.networking);
        programming = (CardView) findViewById(R.id.programming);
        sad = (CardView) findViewById(R.id.sad);
        maths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SubEbookActivity.this,MathsActivity.class);
                startActivity(intent);
            }
        });
        networking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        sad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        programming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
