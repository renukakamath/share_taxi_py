package com.example.blablacar;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class Userviewrequest extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
    Spinner s1,s2;

    EditText e1,e2,ne1,ne2;
    TextView t1,t2,t3;
    String nfplace,ntplace;
    Button b1;
    ListView l1;
    RadioButton r1,r2,r3,r4,r5;
    public static String tolatti,tolongi,folatti,folongi,rat,rid,fp,tp,car_id,se,pns,lid;
    public static String sid,sid1,types;
    String attendance;
    String[] fplace,value,tplace,amount,type,stu_id,first_name,seat,cid,cars,seats,sata,pho,regss,splacess,eplacess,driver,login_ids;
    SharedPreferences sh;

  String [] stations={"Ernakulam south","Town hall","Jln stadium","M.g. Road renakulam","Maharaja's College","Edapally","Kadavanthra","Kaloor","Cochin University","Palarivattom","petta" ,"pathadipalam"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userviewrequest);


        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        l1 = (ListView) findViewById(R.id.lvview);
        l1.setOnItemClickListener(this);

        s1=(Spinner) findViewById(R.id.spinner);


        ne2=(EditText) findViewById(R.id.ne2);
        ne1=(EditText) findViewById(R.id.ne1);
        b1=(Button) findViewById(R.id.button);


        r4=(RadioButton) findViewById(R.id.r4);

        s1.setOnItemSelectedListener(this);

        JsonReq JR1 = new JsonReq();
        JR1.json_response = (JsonResponse) Userviewrequest.this;
        String q1 = "/viewcarr";
        q1 = q1.replace(" ", "%20");
        JR1.execute(q1);





        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Userviewrequest.this;
        String q = "/Userviewrequestssss?login_id=" + sh.getString("log_id", "");
        q = q.replace(" ", "%20");
        JR.execute(q);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nfplace =ne1.getText().toString();
                ntplace=ne2.getText().toString();

                 if(r4.isChecked())
                {
                    types="Car";
                }



//                if  (stations.equals(nfplace)){
//                    if (stations.equals(ntplace)){


                        GeocodingLocation locationAddress = new GeocodingLocation();
                        locationAddress.getAddressFromLocation(nfplace,
                                getApplicationContext(), new GeocoderHandler());

                        locationAddress.getAddressFromLocation(ntplace,
                                getApplicationContext(), new GeocoderHandler1());

                        Toast.makeText(getApplicationContext(), tolatti+" "+tolongi+" "+folatti+" "+folongi, Toast.LENGTH_LONG).show();
                        JsonReq JR = new JsonReq();
                        JR.json_response = (JsonResponse) Userviewrequest.this;
                        String q = "/AddRequestss?login_id=" + sh.getString("log_id", "") +"&fplace=" +nfplace+"&tplace="+ntplace+"&types="+types+"&fromlati="+folatti +"&fromlongi="+folongi +"&tolati="+tolatti+"&tolongi="+tolongi+"&cid="+sid;
                        q = q.replace(" ", "%20");
                        JR.execute(q);
//                    }
//                }



            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        sid=stu_id[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            try {
                String locationAddress;
                switch (message.what) {
                    case 1:
                        Bundle bundle = message.getData();
                        locationAddress = bundle.getString("address");
                        break;
                    default:
                        locationAddress = null;
                }
                String[] tmp = locationAddress.split("\\,");
                Toast.makeText(getApplicationContext(), "check spelling" + tmp, Toast.LENGTH_LONG).show();
                folatti = tmp[0];
                folongi = tmp[1];


//                folatti = tmp[0];
//                folongi = tmp[1];

//                sendreq();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "check spelling" + e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }
        class GeocoderHandler1 extends Handler {
            @Override
            public void handleMessage(Message message) {
                try {
                    String locationAddress;
                    switch (message.what) {
                        case 1:
                            Bundle bundle = message.getData();
                            locationAddress = bundle.getString("address");
                            break;
                        default:
                            locationAddress = null;
                    }
                    String[] tmp = locationAddress.split("\\,");
                    Toast.makeText(getApplicationContext(), "check spelling" + tmp, Toast.LENGTH_LONG).show();
                    tolatti = tmp[0];
                    tolongi = tmp[1];


//                folatti = tmp[0];
//                folongi = tmp[1];

//                    sendreq();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "check spelling" + e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }


//        private void sendreq() {
//            JsonReq JR = new JsonReq();
//            JR.json_response = (JsonResponse) Userviewrequest.this;
//            String q = "/AddRequestss?rid=" + sh.getString("rid", "")  +"&fplace=" +nfplace+"&tplace="+ntplace+"&types="+types;
//            q = q.replace(" ", "%20");
//            JR.execute(q);
//
//        }
//    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("Userviewrequestssss")) {
                String status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {


                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");

                    fplace = new String[ja1.length()];
                    tplace = new String[ja1.length()];
                    amount = new String[ja1.length()];
                    type = new String[ja1.length()];
                    cid = new String[ja1.length()];
                    cars=new String[ja1.length()];
                    seats=new String[ja1.length()];
                    sata=new String[ja1.length()];
                    pho = new String[ja1.length()];
                    regss=new String[ja1.length()];
                    driver=new String[ja1.length()];
                            login_ids=new String[ja1.length()];

                    value = new String[ja1.length()];

                    for (int i = 0; i < ja1.length(); i++) {


                        fplace[i] = ja1.getJSONObject(i).getString("fplaces");
                        tplace[i] = ja1.getJSONObject(i).getString("tplaces");
                        amount[i] = ja1.getJSONObject(i).getString("amounts");
                        cid[i] = ja1.getJSONObject(i).getString("car_id");
                        type[i] = ja1.getJSONObject(i).getString("requestdetails_id");
                        cars[i]=ja1.getJSONObject(i).getString("car");
                        seats[i]=ja1.getJSONObject(i).getString("seat");
                        sata[i]=ja1.getJSONObject(i).getString("status");
                        pho[i] = ja1.getJSONObject(i).getString("phone");
                        regss[i]=ja1.getJSONObject(i).getString("regno");

                        driver[i]=ja1.getJSONObject(i).getString("fname");
                                login_ids[i]=ja1.getJSONObject(i).getString("login_id");


                        value[i] = "From Place: " + fplace[i] + "\nTo Place: " + tplace[i] + "\nAmount: " + amount[i]+ "\nCar: " + cars[i] + "\nReg No: " + regss[i]+ "\nTotal seat: " + seats[i]+ "\n Status: " + sata[i]+ "\nDriver Name: " + driver[i]+ "\n Phone No: " + pho[i];

                    }
                    ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), R.layout.custtext, value);
                    l1.setAdapter(ar);
                }
            }
            else if(method.equalsIgnoreCase("AddRequestss")) {

                String status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "booking SUCCESSFULLY", Toast.LENGTH_LONG).show();
//                    String rid=jo.getString("rid");



                    startActivity(new Intent(getApplicationContext(), Userviewrequest.class));

                } else {

                    Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
                }
            }

            else if(method.equalsIgnoreCase("booknow")) {

                String status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "booking SUCCESSFULLY", Toast.LENGTH_LONG).show();
//                    String rid=jo.getString("rid");



                    startActivity(new Intent(getApplicationContext(), Userviewrequest.class));

                } else {

                    Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
                }
            }



            else if(method.equalsIgnoreCase("viewcar"))
            {
                String status=jo.getString("status");
                Log.d("pearl",status);


                if(status.equalsIgnoreCase("success")){
                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");

                    stu_id=new String[ja1.length()];

                    first_name=new String[ja1.length()];
                    seat=new String[ja1.length()];
                    value=new String[ja1.length()];
                    regss=new String[ja1.length()];
                    splacess=new String[ja1.length()];
                    eplacess=new String[ja1.length()];



                    for(int i = 0;i<ja1.length();i++)
                    {
                        stu_id[i]=ja1.getJSONObject(i).getString("car_id");
                        first_name[i]=ja1.getJSONObject(i).getString("car");
                        seat[i]=ja1.getJSONObject(i).getString("seat");
                        regss[i]=ja1.getJSONObject(i).getString("regno");
                        splacess[i]=ja1.getJSONObject(i).getString("splace");
                        eplacess[i]=ja1.getJSONObject(i).getString("eplace");






                        value[i]=splacess[i]+" to "+eplacess[i]+" ,Seat:"+seat[i];


                    }
                    ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                    s1.setAdapter(ar);
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
                rat=amount[i];
                rid=type[i];
                fp=fplace[i];
                tp=tplace[i];
                car_id=cid[i];
                se=seats[i];
                pns = pho[i];
                lid=login_ids[i];



                final CharSequence[] items = {"Book Now","call","chat"};

                AlertDialog.Builder builder = new AlertDialog.Builder(Userviewrequest.this);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {

                        if (items[item].equals("Book Now")) {

                            String message = "Hello! I Want To Go."+",From place:"+fp +",To Place:"+tp;
                            sendSMS(pns, message);



                            startActivity(new Intent(getApplicationContext(), BookNow.class));


                        }




                        else if (items[item].equals("call")) {


                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + pns)); // Change the number
                            startActivity(callIntent);
                        }

                        else if (items[item].equals("chat")) {


                           startActivity(new Intent(getApplicationContext(),ChatHere1.class));
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
        Intent b=new Intent(getApplicationContext(),Userhome.class);
        startActivity(b);
    }
}