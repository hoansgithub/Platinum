package com.ikt.platinum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ikt.platinum.model.Record;
import com.ikt.platinum.model.User;
import com.ikt.platinum.model.Venue;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class LoginFragment extends Fragment implements IMyFragment {
	private View rootView;
	private LayoutInflater inflater;
	private Spinner venueSpinner;
	private List<Venue> venueList;
	private Button LoginBtn;
	private Button LogoutBtn;
	private Button VenueReortBtn;
	private EditText pswdField;
	private EditText reportField;
	private Activity pcontext;
	private String service_host;
	private int duration ;
	private DatabaseHandler  dbhandler; 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		this.inflater=inflater;
		this.duration= Toast.LENGTH_SHORT;
		this.pcontext=getActivity();
		this.service_host=((PlatinumApplication)getActivity().getApplication()).getSERVICE_HOST();
		rootView = this.inflater.inflate(R.layout.fragment_login,container, false);
		this.pswdField=(EditText) rootView.findViewById(R.id.login_password);
		this.reportField=(EditText) rootView.findViewById(R.id.venue_total);
		venueSpinner=(Spinner)rootView.findViewById(R.id.venue_spinner);
		this.dbhandler=new DatabaseHandler(this.getActivity());
	//	List<String> list = new ArrayList<String>();
		venueList=((PlatinumApplication)getActivity().getApplication()).getVENUES();
		  WebServiceController svc=new WebServiceController();
		 // if(venueList==null){venueList=new ArrayList<Venue>();}
		 
		  this.loadVenueSpinner();
		
		//onclick implement
		
		LoginBtn = (Button) rootView.findViewById(R.id.btn_login);

		LoginBtn.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		        loginClick(v);
		    }
		});
		LogoutBtn = (Button) rootView.findViewById(R.id.btn_logout);

		LogoutBtn.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		        logoutClick(v);
		    }
		});
		
		VenueReortBtn=(Button) rootView.findViewById(R.id.btn_venue_report);
		VenueReortBtn.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		        venueReportClick(v);
		    }
		});
		//check login
		LinearLayout loginlo=(LinearLayout) rootView.findViewById(R.id.login_layout);
		LinearLayout logoutlo=(LinearLayout) rootView.findViewById(R.id.logout_layout);
		if(((PlatinumApplication)getActivity().getApplication()).isLOGGED_IN())
		{
			
			loginlo.setVisibility(LinearLayout.INVISIBLE);
			
			logoutlo.setVisibility(LinearLayout.VISIBLE);
		}
		else{
			
			loginlo.setVisibility(LinearLayout.VISIBLE);
			
			logoutlo.setVisibility(LinearLayout.INVISIBLE);
		}
		//check manager
		LinearLayout lmg=(LinearLayout) rootView.findViewById(R.id.manager_layout);
		if(((PlatinumApplication)getActivity().getApplication()).isMANAGER())
		{
			lmg.setVisibility(LinearLayout.VISIBLE);
		}
		else{
			lmg.setVisibility(LinearLayout.INVISIBLE);
		}
		return rootView;
	}
	@Override
	public void onBecameVisible() {
	 
	    
	}
	public void loadVenueSpinner()
	{
		
		List<String> list=new ArrayList<String>();
		List<Venue> vn=((PlatinumApplication)getActivity().getApplication()).getVENUES();
		
		for(int v=0;v<vn.size();v++){
			String vname=((PlatinumApplication)getActivity().getApplication()).getVENUES().get(v).getVenue_name();
			list.add(vname);
		}
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item,list );
 
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
 
        // attaching data adapter to spinner
        venueSpinner.setAdapter(dataAdapter);
	}
	public void venueReportClick(View view)
	{
		if(((PlatinumApplication)getActivity().getApplication()).isMANAGER())
		{
			String total=reportField.getText().toString();
			if(total.length()>0)
			{
				int venue_id=((PlatinumApplication)getActivity().getApplication()).getVENUE_ID();
				int pgid=((PlatinumApplication)getActivity().getApplication()).getPGID();
				Map<String, Object> params = new HashMap<String, Object>();   
				Time now = new Time();
				now.setToNow();
				String curdate=now.year+"/"+(now.month+1)+"/"+now.monthDay;
				String curTime=now.hour+":"+now.minute+":"+now.second;
				  params.put("total",total);
				  params.put("pg_id",pgid);
				  params.put("venue_id",venue_id);
				  params.put("curdate",curdate);
				  params.put("curtime",curTime);
				  WebServiceController svc=new WebServiceController();
				try {
					String res= svc.execute(new WebServiceProperties(this.service_host+"/venue-report", params)).get();
					if(res!=null)
					{
						JSONObject result = new JSONObject(res);
						boolean serversc=result.getBoolean("success");
						String servermsg=result.getString("msg");
						if(serversc){
							Toast toast = Toast.makeText(pcontext, "DONE !", duration);
							toast.show();
							reportField.setText("");
						}
						else{
							Toast toast = Toast.makeText(pcontext, servermsg, duration);
							toast.show();
						}
					}
					else{
						int qtity=Integer.parseInt(total);
						this.dbhandler.addRecord(new Record(0,curTime, curdate, qtity, venue_id, pgid,0, 2));
						Toast toast = Toast.makeText(pcontext, "DONE !", duration);
						toast.show();
						reportField.setText("");
					}
				} catch (Exception e) {
					Toast toaste = Toast.makeText(pcontext, "connection error", duration);
					toaste.show();
				} 
			}
			else{
				CharSequence text = "field is required";
				Toast toast = Toast.makeText(pcontext, text, duration);
				toast.show();
			}
			
		}
		else{
			
		}
	}
	public void loginClick(View view)
	{
		
		String val= pswdField.getText().toString();
		int len=val.length();
		int selectedVenueID=0;
		int selectedVenueIndex=venueSpinner.getSelectedItemPosition();
		if(venueList.size()>0)
		{
			selectedVenueID=venueList.get(selectedVenueIndex).getVenue_id();
		}
		if(len!=0 && selectedVenueID>0)
		{
			
			Map<String, Object> params = new HashMap<String, Object>();   
			  params.put("password", val);
			  params.put("venue_id", selectedVenueID);
			  WebServiceController svc=new WebServiceController();
			  try{
			String res= svc.execute(new WebServiceProperties(this.service_host+"/get-auth", params)).get();
			if (null != res) {
	            JSONObject result = new JSONObject(res);
	            boolean checklogin=result.getBoolean("success");
	            boolean ismanager=result.getBoolean("is_mg");
	            if(checklogin)
	            {
	            	this.dbhandler.deleteAllUser();
	            	((PlatinumApplication)getActivity().getApplication()).setMANAGER(ismanager);
	            	((PlatinumApplication)getActivity().getApplication()).setVENUE_ID(selectedVenueID);
	            	((PlatinumApplication)getActivity().getApplication()).setPGID(result.getInt("pg_id"));
	            	this.dbhandler.addUser(new User(result.getInt("pg_id"), ismanager, selectedVenueID));
	            	LinearLayout loginlo=(LinearLayout) rootView.findViewById(R.id.login_layout);
	    			loginlo.setVisibility(LinearLayout.INVISIBLE);
	    			LinearLayout logoutlo=(LinearLayout) rootView.findViewById(R.id.logout_layout);
	    			logoutlo.setVisibility(LinearLayout.VISIBLE);
	    			((PlatinumApplication)getActivity().getApplication()).setLOGGED_IN(true);
	    			LinearLayout lmg=(LinearLayout) rootView.findViewById(R.id.manager_layout);
	    			if(ismanager)
	    			{
	    				lmg.setVisibility(LinearLayout.VISIBLE);
	    			}
	            }
	            else{
	            	CharSequence text = "wrong password";
					Toast toast = Toast.makeText(pcontext, text, duration);
					toast.show();
	            }
			}
			  }
			  catch(Exception ex)
			  {
				
					CharSequence text = "Connection error";
					Toast toast = Toast.makeText(pcontext, text, duration);
					toast.show();
			  }
			
			
			
			
			
		}
		else if(len==0){
			CharSequence text = "password field is required";

			Toast toast = Toast.makeText(pcontext, text, duration);
			toast.show();
		}
		else if(selectedVenueID==0){
			CharSequence text = "please select an outlet";

			Toast toast = Toast.makeText(pcontext, text, duration);
			toast.show();
		}
		
		//MainActivity ma=(MainActivity) getActivity();
		//ma.ReplaceLoginFragment();
	}
	public void logoutClick(View view)
	{
		dbhandler.deleteAllUser();
		LinearLayout loginlo=(LinearLayout) rootView.findViewById(R.id.login_layout);
		loginlo.setVisibility(LinearLayout.VISIBLE);
		LinearLayout logoutlo=(LinearLayout) rootView.findViewById(R.id.logout_layout);
		logoutlo.setVisibility(LinearLayout.INVISIBLE);
		LinearLayout lmg=(LinearLayout) rootView.findViewById(R.id.manager_layout);
		lmg.setVisibility(LinearLayout.INVISIBLE);
		((PlatinumApplication)getActivity().getApplication()).setMANAGER(false);
		((PlatinumApplication)getActivity().getApplication()).setLOGGED_IN(false);
		((PlatinumApplication)getActivity().getApplication()).setPGID(0);
		((PlatinumApplication)getActivity().getApplication()).setVENUE_ID(0);
		//MainActivity ma=(MainActivity) getActivity();
		//ma.ReplaceLoginFragment();
	}
}
