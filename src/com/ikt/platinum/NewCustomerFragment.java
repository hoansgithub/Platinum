package com.ikt.platinum;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import org.jraf.android.backport.switchwidget.Switch;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class NewCustomerFragment extends Fragment implements IMyFragment {
	private View rootView;
	private LayoutInflater inflater;
	private ViewGroup container;
	private Button btnAddnew;
	private TextView textcardno;
	private TextView textemail;
	private TextView textname;
	private Switch switchgender;
	private Switch switchNat;
	private RadioGroup rgAge;
	private RadioButton rad35;
	private RadioButton radmiddle;
	private RadioButton rad50;
	private Activity pcontext;
	private int duration ;
	public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
	          "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
	          "\\@" +
	          "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
	          "(" +
	          "\\." +
	          "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
	          ")+"
	      );
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		this.inflater=inflater;
		this.container=container;
		this.duration= Toast.LENGTH_SHORT;
		this.pcontext=getActivity();
		 rootView = this.inflater.inflate(R.layout.fragment_new_customer,container, false);
		 rad35=(RadioButton) rootView.findViewById(R.id.radiominus35);
		 radmiddle=(RadioButton) rootView.findViewById(R.id.radmiddle);
		 rad50=(RadioButton) rootView.findViewById(R.id.radio50plus);
		 btnAddnew=(Button) rootView.findViewById(R.id.btn_add_new);
		 btnAddnew.setOnClickListener(new View.OnClickListener() {
			    @Override
			    public void onClick(View v) {
			        newCusClick(v);
			    }
			});
		 textcardno=(TextView) rootView.findViewById(R.id.card_no);
		 textemail=(TextView) rootView.findViewById(R.id.cus_email);
		 textname=(TextView) rootView.findViewById(R.id.cus_name);
		 switchgender=(Switch) rootView.findViewById(R.id.gender_switch);
		 switchNat=(Switch)rootView.findViewById(R.id.nationality_switch);
		 rgAge=(RadioGroup)rootView.findViewById(R.id.radiogroup_age);
		 
		return rootView;
	}
	@Override
	public void onBecameVisible() {
	   
		//rootView = this.inflater.inflate(R.layout.fragment_album,container, false);
//				TextView text=(TextView)rootView.findViewById(R.id.tabalbum);
//				Calendar c = Calendar.getInstance(); 
//				int seconds = c.get(Calendar.SECOND);
//				text.setText(String.valueOf(seconds));
			    // Get the Camera instance as the activity achieves full user focus
	    
	}
	public void resetFields()
	{
		textcardno.setText("");
		textemail.setText("");
		textname.setText("");
		
	}
	public void newCusClick(View view)
	{
		
		boolean loggedin=((PlatinumApplication)getActivity().getApplication()).isLOGGED_IN();
		if(loggedin)
		{
		String cardstring=textcardno.getText().toString();
		int cardno=(cardstring.length()!=0)?Integer.parseInt(cardstring):0;
		String email=textemail.getText().toString();
		boolean emailcheck=EMAIL_ADDRESS_PATTERN.matcher(email).matches();
		String cusname=textname.getText().toString();
		int gender=switchgender.isChecked()?1:0;
		int nat=switchNat.isChecked()?1:0;
		int catageid=1;
		if(radmiddle.isChecked())
		{
			catageid=2;
		}
		else if(rad50.isChecked())
		{
			catageid=3;
		}
		boolean er=false;
		String msg="";
		if(cardno==0 ||email==null||cusname==null)
		{
			er=true;
			msg+="* all fields are required \n";
		}
		
		if(!emailcheck)
		{
			er=true;
			msg+="* incorrect email format \n";
		}
		if(er)
		{
			
			Toast toast = Toast.makeText(pcontext, msg, duration);
			toast.show();
		}
		else{
			Map<String, Object> params = new HashMap<String, Object>();   
			  params.put("cardnumber", cardno);
			  params.put("email",email);
			  params.put("fullname",cusname);
			  params.put("gender",gender);
			  params.put("age_category", catageid);
			  params.put("nationality", nat);
			  WebServiceController svc=new WebServiceController();
			  	try {
			  		
				String res= svc.execute(new WebServiceProperties("http://demo.iktknowledge.com/PlatinumWeb/service/new-customer", params)).get();
				
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
						Toast toast2 = Toast.makeText(pcontext, "new customer has been successfully added", duration);
						toast2.show();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Toast toaste = Toast.makeText(pcontext, "connection error", duration);
					toaste.show();
				}
			  
			
		}
		}
		else{
			Toast toast = Toast.makeText(pcontext, "you are not logged in", duration);
			toast.show();
		}
//		Map<String, Object> params = new HashMap<String, Object>();   
//		  params.put("cardnumber", cardno);
//		  params.put("venue_id", selectedVenueID);
//		  WebServiceController svc=new WebServiceController();
		
		
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
