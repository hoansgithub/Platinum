package com.ikt.platinum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.ikt.platinum.model.Record;
import com.ikt.platinum.model.User;
import com.ikt.platinum.model.Venue;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

public class LoadingActivity extends FragmentActivity  {

    private int duration;
	private List<Venue> venueList;
	private String service_host;
	private DatabaseHandler  dbhandler; 
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebServiceController svc=new WebServiceController();
        setContentView(R.layout.activity_loading);
        this.duration= Toast.LENGTH_SHORT;
        this.venueList=new ArrayList<Venue>();
        this.service_host=((PlatinumApplication)this.getApplication()).getSERVICE_HOST();
        this.dbhandler=new DatabaseHandler(this);
        try {
			//records
        	
        	List<Record> lr=dbhandler.getAllRecords();
        	if(lr.size()>0)
        	{
        		Gson gson = new Gson();
            	String json = gson.toJson(lr);
        		 Map<String, Object> params = new HashMap<String, Object>();   
        		 params.put("records", json);
        		 String res= svc.execute(new WebServiceProperties(this.service_host+"/upload-records", params)).get();
        		 if (null != res) {
        			 JSONObject result = new JSONObject(res);
						boolean serversc=result.getBoolean("success");
						String servermsg=result.getString("msg");
						if(serversc)
						{
							dbhandler.deleteAllRecord();
						}
        			 
        		 }
        		
        	}
        	//get curr user
        	
        	User us=dbhandler.getUser();
        	if(us!=null)
        	{
        		((PlatinumApplication)this.getApplication()).setMANAGER(us.isAdmin());
            	((PlatinumApplication)this.getApplication()).setVENUE_ID(us.getVenue_id());
            	((PlatinumApplication)this.getApplication()).setPGID(us.getUser_id());
            	((PlatinumApplication)this.getApplication()).setLOGGED_IN(true);
            	
        	}
        	
        	//--------
			  Map<String, Object> params = new HashMap<String, Object>();   
			  params.put("param", 0);
			 
			  WebServiceController svc2=new WebServiceController();
			String res= svc2.execute(new WebServiceProperties(this.service_host+"/get-venues", params)).get();
			if (null != res) {
				dbhandler.deleteAllVenue();
	            JSONArray result = new JSONArray(res);
	            for(int i=0;i<result.length();i++)
	            {
	            	
					JSONObject o=(JSONObject) result.get(i);
					String venuename = o.getString("venue_name");
					int venueid=o.getInt("venue_id");
					this.venueList.add(new Venue(venueid,venuename));
					dbhandler.addVenue(new Venue(venueid,venuename));
					//list.add(venuename);
					((PlatinumApplication)this.getApplication()).setVENUES(venueList);
	            }
	            
			}
			else{
				venueList=dbhandler.getAllVenues();
				((PlatinumApplication)this.getApplication()).setVENUES(venueList);
			}
			Intent i = new Intent(LoadingActivity.this, MainActivity.class);
            startActivity(i);
            finish();
			
		} catch (Exception e) {
			Context context = this;
			CharSequence text = "Connection error";
			venueList=dbhandler.getAllVenues();
			((PlatinumApplication)this.getApplication()).setVENUES(venueList);
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			 Intent i = new Intent(LoadingActivity.this, MainActivity.class);
             startActivity(i);
             finish();
		}
    }
   
}
