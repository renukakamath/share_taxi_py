package com.example.blablacar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

public class Addcar extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    EditText e1,e2,e3,e4,e5;
    Button b1;
    ListView l1;
    SharedPreferences sh;
    String Activity,details,regs,splaces,eplaces;
    String[] act,det,date,value,admission_id,regss,splacess,eplacess;
    public static  String aid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcar);
        e1=(EditText)findViewById(R.id.car);
        l1=(ListView)findViewById(R.id.list);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        e2=(EditText) findViewById(R.id.seat);
        e3=(EditText) findViewById(R.id.reg);
        e4=(EditText) findViewById(R.id.splace);
        e5=(EditText) findViewById(R.id.eplace);

        b1=(Button) findViewById(R.id.actbutton);

        l1.setOnItemClickListener(this);



        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Addcar.this;
        String q = "/Viewcar?login_id="+ sh.getString("log_id", "");
        q = q.replace(" ", "%20");
        JR.execute(q);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity=e1.getText().toString();
                details=e2.getText().toString();
                regs=e3.getText().toString();
                splaces=e4.getText().toString();
                eplaces=e5.getText().toString();

                JsonReq JR = new JsonReq();
                JR.json_response = (JsonResponse) Addcar.this;
                String q = "/addcar?login_id="+ sh.getString("log_id", "") +"&details=" +details+"&Activity="+Activity+"&regs="+regs+"&splaces="+splaces+"&eplaces="+eplaces;
                q = q.replace(" ", "%20");
                JR.execute(q);
            }
        });
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("addcar")) {

                String status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "ADDED SUCCESSFULLY", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Addcar.class));

                } else {

                    Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Addcar.class));
                }
            }
            else if(method.equalsIgnoreCase("Viewcar"))
            {
                String status=jo.getString("status");
                Log.d("pearl",status);


                if(status.equalsIgnoreCase("success")){
                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");
                    admission_id=new String[ja1.length()];
                    act=new String[ja1.length()];
                    det=new String[ja1.length()];
                    date=new String[ja1.length()];
                    regss=new String[ja1.length()];
                    splacess=new String[ja1.length()];
                    eplacess=new String[ja1.length()];

                    value=new String[ja1.length()];


                    for(int i = 0;i<ja1.length();i++)
                    {
                        admission_id[i]=ja1.getJSONObject(i).getString("car_id");
                        act[i]=ja1.getJSONObject(i).getString("status");
                        det[i]=ja1.getJSONObject(i).getString("car");
                        date[i]=ja1.getJSONObject(i).getString("seat");
                        regss[i]=ja1.getJSONObject(i).getString("regno");
                        splacess[i]=ja1.getJSONObject(i).getString("splace");
                        eplacess[i]=ja1.getJSONObject(i).getString("eplace");




                        value[i]="status: "+act[i]+"\ncar: "+det[i]+"\nseat: "+date[i]+"\nregno: "+regss[i]+"\nstart place: "+splacess[i]+"\nend place: "+eplacess[i];

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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        aid=admission_id[i];
        final CharSequence[] items = {"Add Place"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Addcar.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Add Place")) {


                    startActivity(new Intent(getApplicationContext(),Addplace.class));


                }





            }

        });
        builder.show();
   }

    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),staffhome.class);
        startActivity(b);
    }
    }
