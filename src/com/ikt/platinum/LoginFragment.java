package com.ikt.platinum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class LoginFragment extends Fragment implements IMyFragment {
	private View rootView;
	private LayoutInflater inflater;
	private Spinner venueSpinner;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		this.inflater=inflater;
		rootView = this.inflater.inflate(R.layout.fragment_login,container, false);
		venueSpinner=(Spinner)rootView.findViewById(R.id.venue_spinner);
		List<String> list = new ArrayList<String>();
		list.add("VENUE 1");
		list.add("VENUE 2");
		list.add("VENUE 3");
		this.loadVenueSpinner(list);
		return rootView;
	}
	@Override
	public void onBecameVisible() {
	  try {
		  
		  Map<String, Object> params = new HashMap<String, Object>();   
		  params.put("param", 0);

		  
		String res= WebServiceController.makeRequest("http://demo.iktknowledge.com/PlatinumWeb/service/get-venues", params, null, 0);
		if (null != res) {
            JSONObject result = new JSONObject(res);
		}
		
	} catch (Exception e) {
		Context context = getActivity();
		CharSequence text = "Connection error";
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}
	    
	}
	public void loadVenueSpinner(List<String> list)
	{
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_1, list);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
			this.venueSpinner.setAdapter(dataAdapter);
	}

//	@Override
//	public void onResume() {
//	    super.onResume();
//	    TextView text=(TextView)rootView.findViewById(R.id.tabalbum);
//		Calendar c = Calendar.getInstance(); 
//		int seconds = c.get(Calendar.SECOND);
//		text.setText(String.valueOf(seconds));
//	    // Update your UI here.
//	}
}
