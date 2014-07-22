package com.ikt.platinum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

public class LoadingActivity extends FragmentActivity  {

    private int duration;
	private List<Venue> venueList;

	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        this.duration= Toast.LENGTH_SHORT;
        
        
        try {
			  
			  Map<String, Object> params = new HashMap<String, Object>();   
			  params.put("param", 0);
			  this.venueList=new ArrayList<Venue>();
			  WebServiceController svc=new WebServiceController();
			String res= svc.execute(new WebServiceProperties("http://demo.iktknowledge.com/PlatinumWeb/service/get-venues", params)).get();
			if (null != res) {
	            JSONArray result = new JSONArray(res);
	            for(int i=0;i<result.length();i++)
	            {
	            	
					JSONObject o=(JSONObject) result.get(i);
					String venuename = o.getString("venue_name");
					int venueid=o.getInt("venue_id");
					this.venueList.add(new Venue(venueid,venuename));
					//list.add(venuename);
					((PlatinumApplication)this.getApplication()).setVENUES(venueList);
	            }
	            Intent i = new Intent(LoadingActivity.this, MainActivity.class);
                startActivity(i);
                finish();
			}
			
			
		} catch (Exception e) {
			Context context = this;
			CharSequence text = "Connection error";

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		}
    }
   
}
