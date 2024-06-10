package com.example.blablacar;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class Userrequest extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
//    Spinner s1,s2;

    EditText e1;
    ListView l1;
    String[] name, place, value, bunk_id, latitude, longitude, countrequest, statu, requestid,car_id;
    String search, status, method;
    public static String bid, cid, qty, amt, rid, stat;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userrequest);


        e1 = (EditText) findViewById(R.id.search);
        l1 = (ListView) findViewById(R.id.lv);
        l1.setOnItemClickListener(this);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Userrequest.this;
        String q = "/userviewrequest?login_id=" + sh.getString("log_id", "");
        q = q.replace(" ", "%20");
        JR.execute(q);


    }


    @Override
    public void response(JSONObject jo) {

        try {


            method = jo.getString("method");


            if (method.equalsIgnoreCase("userviewrequest")) {


                status = jo.getString("status");

                Log.d("pearlssssss", status);


                if (status.equalsIgnoreCase("success")) {
                    l1.setVisibility(View.VISIBLE);
                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                    name = new String[ja1.length()];
                    place = new String[ja1.length()];
                    bunk_id = new String[ja1.length()];


                    latitude = new String[ja1.length()];
                    longitude = new String[ja1.length()];

                    statu = new String[ja1.length()];
                    value = new String[ja1.length()];
                    requestid = new String[ja1.length()];


                    for (int i = 0; i < ja1.length(); i++) {
                        name[i] = ja1.getJSONObject(i).getString("car_id");
                        place[i] = ja1.getJSONObject(i).getString("fplace");
                        bunk_id[i] = ja1.getJSONObject(i).getString("tplace");

                        latitude[i] = ja1.getJSONObject(i).getString("qty");
                        longitude[i] = ja1.getJSONObject(i).getString("total");
                        statu[i] = ja1.getJSONObject(i).getString("rstatus");
                        requestid[i] = ja1.getJSONObject(i).getString("request_id");


                        value[i] =   "From Place:" + place[i] + "\nTo Place:" + bunk_id[i] + "\nNO Person:" + latitude[i] + "\nAmount:" + longitude[i] + "\nStatus:" + statu[i];

                    }
                    ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), R.layout.custtext, value);

                    l1.setAdapter(ar);


                } else {
                    Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_LONG).show();
                    l1.setVisibility(View.GONE);
                }

            } else if (method.equalsIgnoreCase("PickUp")) {

                String status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "PickUp SUCCESSFULLY", Toast.LENGTH_LONG).show();
//                    String rid=jo.getString("rid");


                    startActivity(new Intent(getApplicationContext(), searchRequest.class));

                } else {

                    Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
                }
            } else if (method.equalsIgnoreCase("Drop")) {

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
        amt=longitude[i];
        qty=latitude[i];
        cid=name[i];

        if (stat.equalsIgnoreCase("Drop")) {
            final CharSequence[] items = {"Pay Now", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(Userrequest.this);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (items[item].equals("Pay Now")) {


                        startActivity(new Intent(getApplicationContext(), AndroidBarcodeQrExample.class));


                    }
                }

            });
            builder.show();

        }
    }
    }








