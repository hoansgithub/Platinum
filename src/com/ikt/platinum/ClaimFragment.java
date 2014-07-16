package com.ikt.platinum;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ClaimFragment extends Fragment implements IMyFragment {
	private View rootView;
	private LayoutInflater inflater;
	private ViewGroup container;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		this.inflater=inflater;
		this.container=container;
		 rootView = inflater.inflate(R.layout.fragment_claim,
				container, false);
		
		return rootView;
	}
	@Override
	public void onBecameVisible() {
		//rootView = this.inflater.inflate(R.layout.fragment_album,container, false);
//		TextView text=(TextView)rootView.findViewById(R.id.tabartist);
//		Calendar c = Calendar.getInstance(); 
//		int seconds = c.get(Calendar.SECOND);
//		text.setText(String.valueOf(seconds));
	    // Get the Camera instance as the activity achieves full user focus
	    
	}

//	public void changeViewContent(){
//		ViewGroup container =((ViewGroup)getView().getParent());
//		LayoutInflater inflater = (LayoutInflater) getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		rootView = inflater.inflate(R.layout.fragment_artist,
//				container, false);
//		
//		if(rootView!=null){
//			TextView text=(TextView)rootView.findViewById(R.id.tabartist);
//			Calendar c = Calendar.getInstance(); 
//			int seconds = c.get(Calendar.SECOND);
//			text.setText(String.valueOf(seconds));
//			}
//	}
//	@Override
//	public void onResume()
//	{
//		 super.onResume();
//		    TextView text=(TextView)rootView.findViewById(R.id.tabartist);
//			Calendar c = Calendar.getInstance(); 
//			int seconds = c.get(Calendar.SECOND);
//			text.setText(String.valueOf(seconds));
//		    // Update your UI here.
//	}
}
