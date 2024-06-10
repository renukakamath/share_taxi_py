package com.example.blablacar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

public class BookNow extends AppCompatActivity implements JsonResponse {

    EditText e1,e2,e3;
    Button b1;

    String amount,hours,total;

    String [] cycle_id,location,value;
    SharedPreferences sh;
    public static String cid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_now);

        e1=(EditText) findViewById(R.id.Amonut);
        e2=(EditText) findViewById(R.id.hours);
        e3=(EditText) findViewById(R.id.total);
        b1=(Button) findViewById(R.id.booknow);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        e1.setText(Userviewrequest.rat);



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hours=e2.getText().toString();
                total=e3.getText().toString();
                if(hours.equalsIgnoreCase(""))
                {
                    e2.setError("Enter Quantity");
                    e2.setFocusable(true);
                }
                else {


//                Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!"+sid, Toast.LENGTH_LONG).show();
                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) BookNow.this;
                    String q = "/BookCycle?login_id=" + sh.getString("login_id", "") + "&quantity=" + hours + "&total=" + total  + "&rid=" + Userviewrequest.rid + "&st=" + Userviewrequest.se+"&fplace="+Userviewrequest.fp+"&tplace="+Userviewrequest.tp+"&car_id="+Userviewrequest.car_id;
                    q = q.replace(" ", "%20");
                    JR.execute(q);
                }


            }
        });


        e2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(e2.getText().toString().equalsIgnoreCase(""))
                {

                }
                else if(e2.getText().toString().equalsIgnoreCase("."))
                {

                }
                else
                {
                    if(Integer.parseInt(e2.getText().toString())<=Integer.parseInt(Userviewrequest.se)) {
                        Integer s = Integer.parseInt(e1.getText().toString()) * Integer.parseInt(e2.getText().toString());
                        e3.setText(s + "");
                    }
                    else{
                        e3.setText("");
                        e2.setText("");
                        Toast.makeText(getApplicationContext(),"Enter a seat greater than "+Userviewrequest.se,Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }

    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                Toast.makeText(getApplicationContext(), " SUCCESS", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Userviewrequest.class));

            }
            else if (status.equalsIgnoreCase("out of stock")) {
                Toast.makeText(getApplicationContext(), "no seat", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), BookNow.class));

            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

        }
    }

    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Userviewrequest.class);
        startActivity(b);
    }
    }
