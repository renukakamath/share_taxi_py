package com.example.blablacar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class staffhome extends AppCompatActivity {
    ImageView b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12,b13,b14;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staffhome);
        b1=(ImageView) findViewById(R.id.viewclass);

//        b11=(ImageView) findViewById(R.id.sss);

        b2=(ImageView) findViewById(R.id.searchnearbunk);

//        b3=(ImageView) findViewById(R.id.Viewrechargerequests);
        b5=(ImageView) findViewById(R.id.searchmechanic);

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),login.class));
            }
        });

//        b3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                startActivity(new Intent(getApplicationContext(),Addplace.class));
//            }
//        });



        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),searchRequest.class));
            }
        });


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Addcar.class));
            }
        });
//
//        b11.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                startActivity(new Intent(getApplicationContext(),student_viewstimetable.class));
//            }
//        });
    }

}