package com.example.blablacar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Addplace extends AppCompatActivity  implements JsonResponse {
    EditText e1,e2;
    Button b1;
    ListView l1;
    SharedPreferences sh;
    String Activity,details;
    String[] act,det,date,value,admission_id;
    public static  String aid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addplace);

        e1=(EditText)findViewById(R.id.car);
        l1=(ListView)findViewById(R.id.list);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());



        b1=(Button) findViewById(R.id.actbutton);

//        l1.setOnItemClickListener(this);



        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Addplace.this;
        String q = "/Viewplace?login_id="+ sh.getString("log_id", "")+"&cid="+Addcar.aid;
        q = q.replace(" ", "%20");
        JR.execute(q);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity=e1.getText().toString();




                JsonReq JR = new JsonReq();
                JR.json_response = (JsonResponse) Addplace.this;
                String q = "/addplace?login_id="+ sh.getString("log_id", "") +"&Activity="+Activity+"&cid="+Addcar.aid+"&lati="+LocationService.lati +"&longi="+LocationService.logi;
                q = q.replace(" ", "%20");
                JR.execute(q);
            }
        });
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("addplace")) {

                String status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "ADDED SUCCESSFULLY", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Addplace.class));

                } else {

                    Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
                }
            }
            else if(method.equalsIgnoreCase("Viewplace"))
            {
                String status=jo.getString("status");
                Log.d("pearl",status);


                if(status.equalsIgnoreCase("success")){
                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");
                    admission_id=new String[ja1.length()];
                    act=new String[ja1.length()];

                    value=new String[ja1.length()];


                    for(int i = 0;i<ja1.length();i++)
                    {
                        admission_id[i]=ja1.getJSONObject(i).getString("place_id");
                        act[i]=ja1.getJSONObject(i).getString("place");




                        value[i]="place: "+act[i];

                    }
                    ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),R.layout.custtext,value);
                    l1.setAdapter(ar);
                }
            }

        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Addcar.class);
        startActivity(b);
    }
}