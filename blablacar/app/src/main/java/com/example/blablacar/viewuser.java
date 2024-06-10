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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class viewuser extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {

    EditText e1, e2, e3, e4, e5, e6, e7;
    Button b1;
    String fname, lname, place, phone, email, uname, password;
    String[] first_name, last_name, pla, pho, mail, value, student_id,lati,longi;
    ListView l1;

    public static String fn, lts, lgs, pns, em, logi_d;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewuser);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        l1 = (ListView) findViewById(R.id.list);

        l1.setOnItemClickListener(this);

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) viewuser.this;
        String q = "/viewuser?login_id=" + sh.getString("log_id", "")+"&uid="+searchRequest.uid;
        q = q.replace(" ", "%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method = jo.getString("method");
            if (method.equalsIgnoreCase("Addstudent")) {

                String status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "ADDED SUCCESSFULLY", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), viewuser.class));

                } else {

                    Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
                }
            } else if (method.equalsIgnoreCase("viewuser")) {
                String status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {
                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");

                    first_name = new String[ja1.length()];
                    last_name = new String[ja1.length()];
                    pla = new String[ja1.length()];
                    pho = new String[ja1.length()];
                    mail = new String[ja1.length()];
                    value = new String[ja1.length()];
                    student_id = new String[ja1.length()];
                    lati = new String[ja1.length()];
                    longi = new String[ja1.length()];


                    for (int i = 0; i < ja1.length(); i++) {
                        first_name[i] = ja1.getJSONObject(i).getString("fname");
                        last_name[i] = ja1.getJSONObject(i).getString("lname");
                        pla[i] = ja1.getJSONObject(i).getString("place");
                        pho[i] = ja1.getJSONObject(i).getString("phone");
                        mail[i] = ja1.getJSONObject(i).getString("email");
                        student_id[i] = ja1.getJSONObject(i).getString("login_id");
                        lati[i] = ja1.getJSONObject(i).getString("latitude");
                        longi[i] = ja1.getJSONObject(i).getString("longitude");

                        value[i] = "first name: " + first_name[i] + "\nlast name: " + last_name[i] + "\nplace: " + pla[i] + "\nphone:" + pho[i] + "\nemail:" + mail[i];

                    }
                    ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), R.layout.custtext, value);
                    l1.setAdapter(ar);
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
        lts = lati[i];
        lgs = longi[i];
        pns = pho[i];
        logi_d=student_id[i];

        final CharSequence[] items = {"Arrive SMS", "Call", "View Map","Chat"};

        AlertDialog.Builder builder = new AlertDialog.Builder(viewuser.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Arrive SMS")) {
                    String message = "Hello! I am On The way  To Pick YOU.";
                    sendSMS(pns, message);



                } else if (items[item].equals("Call")) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + pns)); // Change the number
                    startActivity(callIntent);

                } else if (items[item].equals("View Map")) {
                    String url = "http://www.google.com/maps?saddr=" + LocationService.lati + "," + LocationService.logi + "&daddr=" + lts + "," + lgs;
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else if (items[item].equals("Chat")) {
                    startActivity(new Intent(getApplicationContext(), ChatHere.class));
                }
            }
        });
        builder.show();


    }

    private void sendSMS(String pns, String message) {

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(pns, null, message, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "SMS failed to send", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
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
