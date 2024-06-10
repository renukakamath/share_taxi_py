package com.example.blablacar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Random;

public class searchRequest extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {

    EditText e1;
    ListView l1;
    String[] name,place,value,bunk_id,latitude,longitude,countrequest,statu,requestid,user_id,phone;
    String search,status,method;
    public static String bid,tlati,tlongi,otp,rid,stat,uid,phno;
    SharedPreferences sh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_request);


        e1=(EditText)findViewById(R.id.search);
        l1=(ListView) findViewById(R.id.list);
        l1.setOnItemClickListener(this);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) searchRequest.this;
        String q = "/viewrequest";
        q = q.replace(" ", "%20");
        JR.execute(q);

        e1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                search=e1.getText().toString();



                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) searchRequest.this;
                    String q = "/searchRequest?&search=" + search;
                    q = q.replace(" ", "%20");
                    JR.execute(q);



            }
        });

    }

    @Override
    public void response(JSONObject jo) {
        try {


            method = jo.getString("method");


            if (method.equalsIgnoreCase("viewrequest")) {


                status = jo.getString("status");

                Log.d("pearlssssss", status);


                if (status.equalsIgnoreCase("success")) {
                    l1.setVisibility(View.VISIBLE);
                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                    name = new String[ja1.length()];
                    phone= new String[ja1.length()];
                    place = new String[ja1.length()];
                    bunk_id = new String[ja1.length()];


                    latitude = new String[ja1.length()];
                    longitude = new String[ja1.length()];

                    statu = new String[ja1.length()];
                    value = new String[ja1.length()];
                    requestid= new String[ja1.length()];
                    user_id= new String[ja1.length()];

                    for (int i = 0; i < ja1.length(); i++) {
                        name[i] = ja1.getJSONObject(i).getString("fname");
                        phone[i] = ja1.getJSONObject(i).getString("phone");
                        place[i] = ja1.getJSONObject(i).getString("fplace");
                        bunk_id[i] = ja1.getJSONObject(i).getString("tplace");

                        latitude[i] = ja1.getJSONObject(i).getString("qty");
                        longitude[i] = ja1.getJSONObject(i).getString("total");
                        statu[i] = ja1.getJSONObject(i).getString("rstatus");
                        requestid[i] = ja1.getJSONObject(i).getString("request_id");
                        user_id[i] = ja1.getJSONObject(i).getString("user_id");


                        value[i] = "User name:" + name[i] + "\nFrom Place:" + place[i] + "\nTo Place:" + bunk_id[i]+ "\nNO Person:" + latitude[i] + "\nAmount:" + longitude[i]+ "\nStatus:" + statu[i];

                    }
                    ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), R.layout.custtext, value);

                    l1.setAdapter(ar);


                } else {
                    Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_LONG).show();
                    l1.setVisibility(View.GONE);
                }

            }    else if(method.equalsIgnoreCase("PickUp")) {

                String status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "PickUp SUCCESSFULLY", Toast.LENGTH_LONG).show();
//                    String rid=jo.getString("rid");



                    Toast.makeText(getApplicationContext(),phno,Toast.LENGTH_LONG).show();

                    otp= new DecimalFormat("0000").format(new Random().nextInt(9999));
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phno, null, "Your OTP is : " +otp, null, null);

//					   Toast.makeText(getApplicationContext(),otp,Toast.LENGTH_LONG).show();
//			           Toast.makeText(getApplicationContext()," You are Login Successfully!...,",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),otp.class));

                } else {

                    Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
                }
            }

            else if(method.equalsIgnoreCase("Drop")) {

                String status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "Drop SUCCESSFULLY", Toast.LENGTH_LONG).show();
//                    String rid=jo.getString("rid");



                    startActivity(new Intent(getApplicationContext(), searchRequest.class));

                } else {

                    Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
                }
            }



        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        rid=requestid[i];
        stat=statu[i];
        uid=user_id[i];
        phno=phone[i];

        if (stat.equalsIgnoreCase("pending")) {

            final CharSequence[] items = {"PickUp","User Details"};

            AlertDialog.Builder builder = new AlertDialog.Builder(searchRequest.this);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (items[item].equals("PickUp")) {




                        JsonReq JR = new JsonReq();
                        JR.json_response = (JsonResponse) searchRequest.this;
                        String q = "/PickUp?log_id=" + sh.getString("log_id", "") + "&rid=" + rid;
                        q = q.replace(" ", "%20");
                        JR.execute(q);





                    }
                    else if (items[item].equals("User Details")) {

                        startActivity(new Intent(getApplicationContext(), viewuser.class));



                    }
                }

            });
            builder.show();
        }




        else if (stat.equalsIgnoreCase("PickUp")) {
            final CharSequence[] items = {"Drop"};

            AlertDialog.Builder builder = new AlertDialog.Builder(searchRequest.this);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (items[item].equals("Drop")) {


                        JsonReq JR = new JsonReq();
                        JR.json_response = (JsonResponse) searchRequest.this;
                        String q = "/Drop?log_id=" + sh.getString("log_id", "") + "&rid=" + rid;
                        q = q.replace(" ", "%20");
                        JR.execute(q);


                    }


                }

            });
            builder.show();
        }

        else if (stat.equalsIgnoreCase("Drop")) {
            final CharSequence[] items = {"View QR"};

            AlertDialog.Builder builder = new AlertDialog.Builder(searchRequest.this);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (items[item].equals("View QR")) {

                        startActivity(new Intent(getApplicationContext(), View_my_qr.class));


                    }


                }

            });
            builder.show();
        }
    }
}