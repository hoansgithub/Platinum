package com.ikt.platinum;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LogoutFragment extends Fragment implements IMyFragment {
	private View rootView;
	private LayoutInflater inflater;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		this.inflater=inflater;
		rootView = this.inflater.inflate(R.layout.fragment_logout,container, false);
		
		return rootView;
	}
	@Override
	public void onBecameVisible() {
	 
	    
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
