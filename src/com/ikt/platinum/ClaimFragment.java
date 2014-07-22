package com.ikt.platinum;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ClaimFragment extends Fragment implements IMyFragment {
	private View rootView;
	private LayoutInflater inflater;
	private ViewGroup container;
	private Button btn_add_claim;
	private EditText textCardno;
	private EditText textValue;
	private Activity pcontext;
	private int duration ;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		this.inflater=inflater;
		this.container=container;
		this.duration= Toast.LENGTH_SHORT;
		this.pcontext=getActivity();
		
		 rootView = inflater.inflate(R.layout.fragment_claim,
				container, false);
		 textCardno=(EditText) rootView.findViewById(R.id.card_no_claim);
		 textValue=(EditText) rootView.findViewById(R.id.total_beer);
		 btn_add_claim=(Button) rootView.findViewById(R.id.btn_add_claim);
		 btn_add_claim.setOnClickListener(new View.OnClickListener() {
			    @Override
			    public void onClick(View v) {
			    	addClaimClick(v);
			    }
			});
		return rootView;
	}
	public void resetFields()
	{
		textCardno.setText("");
		textValue.setText("");
		
	}
	public void addClaimClick(View view)
	{
		boolean loggedin=((PlatinumApplication)getActivity().getApplication()).isLOGGED_IN();
		if(loggedin)
		{
		String cardnumbertex=textCardno.getText().toString();
		int cardnumber=(cardnumbertex.length()!=0)?Integer.parseInt(cardnumbertex):0;
		String valtext=textValue.getText().toString();
		int val=(valtext.length()!=0)?Integer.parseInt(valtext):0;
		int venue_id=((PlatinumApplication)getActivity().getApplication()).getVENUE_ID();
		int pgid=((PlatinumApplication)getActivity().getApplication()).getPGID();
		
		boolean er=false;
		String msg="";
		if(cardnumber==0||val==0)
		{
			er=true;
			msg+="* all fields are required \n";
		}
		
		if(er)
		{
			Toast toast = Toast.makeText(pcontext, msg, duration);
			toast.show();
		}
		else{
			Map<String, Object> params = new HashMap<String, Object>();   
			  params.put("cardnumber", cardnumber);
			  params.put("quantity",val);
			  params.put("pg_id",pgid);
			  params.put("venue_id",venue_id);
			  WebServiceController svc=new WebServiceController();
			  
			try {
				String res= svc.execute(new WebServiceProperties("http://demo.iktknowledge.com/PlatinumWeb/service/add-claim", params)).get();
				
				JSONObject result = new JSONObject(res);
				boolean serversc=result.getBoolean("success");
				String servermsg=result.getString("msg");
				if(!serversc)
				{
					Toast toast1 = Toast.makeText(pcontext, servermsg, duration);
					toast1.show();
				}
				else{
					resetFields();
					Toast toast2 = Toast.makeText(pcontext, "DONE !", duration);
					toast2.show();
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Toast toaste = Toast.makeText(pcontext, "connection error", duration);
				toaste.show();
			}
			
			  
		}
	}else{
		Toast toast = Toast.makeText(pcontext, "you are not logged in", duration);
		toast.show();
	}
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
