package com.example.blablacar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class ChatHere extends Activity  implements JsonResponse
{
	EditText ed1;
	ImageView b1;
	String chat;
	ListView l1;
	ImageView iv10;

	String[] aid,aname,r_id1,msg1;
	String[] msgid,s_id,r_id,message,date,re;

	SharedPreferences sh;

	
	Timer timer;
	TimerTask timerTask;
	final Handler handler = new Handler();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat_here);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//		url = sh.getString("url", "");
		ed1=(EditText)findViewById(R.id.editText1);
		l1=(ListView)findViewById(R.id.listView1);
		b1=(ImageView)findViewById(R.id.imageView1);
		//Toast.makeText(getApplicationContext(), "hii1", Toast.LENGTH_SHORT).show();	
		
		startTimer();
		getChats();
		
		b1.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View arg0)
			{
				chat=ed1.getText().toString();
				if(chat.equalsIgnoreCase(""))
				{
					ed1.setError("Empty Message ");
					ed1.setFocusable(true);
				}
				else
				{
					JsonReq JR=new JsonReq();
			        JR.json_response=(JsonResponse) ChatHere.this;
			        String q = "/chat?sender_id="+sh.getString("log_id", "")+"&details="+chat+"&receiver_id="+viewuser.logi_d;
			        q=q.replace(" ","%20");
			        JR.execute(q);
					
				} 
			}
		});
	}
	
	void startTimer() {
		timer = new Timer();
		initializeTimerTask();
		timer.schedule(timerTask, 0, 30000);
	}
	
	void initializeTimerTask() {
		timerTask = new TimerTask() {
			
			@Override
			public void run() {
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						getChats();
					}
				});
			}
		};
	}
	
	void getChats() {
		

			JsonReq JR=new JsonReq();
	        JR.json_response=(JsonResponse) ChatHere.this;
	        String q = "/chatdetail?sender_id="+sh.getString("log_id", "")+"&receiver_id="+viewuser.logi_d;
	        q=q.replace(" ","%20");
//	        Toast.makeText(getApplicationContext(),q, Toast.LENGTH_SHORT).show();
	        JR.execute(q);

	}

	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		
		try {
			
			String method=jo.getString("method");
			if(method.equalsIgnoreCase("chatdetail")){
				String status=jo.getString("status");
				Log.d("pearl",status);
//				Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
				if(status.equalsIgnoreCase("success")){
			
					JSONArray ja1=(JSONArray)jo.getJSONArray("data");
					
					 s_id= new String[ja1.length()];
						r_id=new String[ja1.length()];
						message=new String[ja1.length()];
						date=new String[ja1.length()];
					 
//					 val=new String[ja1.length()];
			     
			    
				     
					for(int i = 0;i<ja1.length();i++)
					{ 
						
						s_id[i]=ja1.getJSONObject(i).getString("sender_id");
						r_id[i]=ja1.getJSONObject(i).getString("receiver_id");
						message[i]=ja1.getJSONObject(i).getString("message");
						date[i]=ja1.getJSONObject(i).getString("date");
						
						
						
					
//						Toast.makeText(getApplicationContext(),"adsdf"+s_id[i]+"   "+r_id[i], Toast.LENGTH_SHORT).show();
//						val[i]="\n\nTeacher : "+teacher[i]+"\nPlace : "+place[i]+"\nDesignation : "+desig[i]+"\nDate of Joining : "+doj[i]+"\nWork Status : "+yoc[i]+"\nSubject : "+subject[i]+"\nPhone : "+phone[i]+"\nEmail : "+email[i];
	//					Toast.makeText(getApplicationContext(),val[i], Toast.LENGTH_SHORT).show();
					
					}
//					ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,val);
//					lv1.setAdapter(ar);
					l1.setAdapter(new Customchat(this, message, s_id,date));
				
			      
			       
				}
				
				else {
					Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();
		
					} 
				}
			if(method.equalsIgnoreCase("chat")){
			
				String status=jo.getString("status");
				Log.d("pearl",status);
				
				
				if(status.equalsIgnoreCase("success")){
					
//					 Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_LONG).show();
					 startActivity(new Intent(getApplicationContext(),ChatHere.class));
					 
				}
				
			}
			
			
			}catch(Exception e)
			{
			// TODO: handle exception

			  Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
			}
		
		

	}
	
	
//	public void onBackPressed()
//	{
//		// TODO Auto-generated method stub
//
//		if(contentcheck.equalsIgnoreCase("userviewstudentchat"))
//		{
//			super.onBackPressed();
//			Intent b=new Intent(getApplicationContext(),UserViewChats.class);
//			startActivity(b);
//		}
//
//	}

	
}
