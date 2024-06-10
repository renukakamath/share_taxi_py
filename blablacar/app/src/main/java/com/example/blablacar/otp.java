package com.example.blablacar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class otp extends AppCompatActivity {

    EditText e1;
    Button b1;
    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        e1=(EditText) findViewById(R.id.etotp);
        b1=(Button) findViewById(R.id.btotp);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              s=e1.getText().toString();

              if(Integer.parseInt(s)==Integer.parseInt(searchRequest.otp))
                {
                    Toast.makeText(getApplicationContext(),"Password is Correct Successfully Logged In",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),searchRequest.class));
                }
              else{
                  Toast.makeText(getApplicationContext(),"Check The Entered Password",Toast.LENGTH_LONG).show();
              }

            }
        });

    }
}