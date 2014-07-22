package com.ikt.platinum;

import java.util.Map;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import android.os.AsyncTask;

public class WebServiceController extends  AsyncTask<WebServiceProperties, Void, String> {

    public static String makeRequest(String path, Map<?, ?> params ) throws Exception {
    	
    	 HttpParams httpParameters = new BasicHttpParams();
		    int timeoutConnection = 10000;
		    HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		    DefaultHttpClient httpclient = new DefaultHttpClient(httpParameters);
		
		    // url with the post data
		    HttpPost httpost = new HttpPost(path);
		
		    // convert parameters into JSON object
		    JSONObject holder = new JSONObject(params);
		
		    // passes the results to a string builder/entity
		    StringEntity se = new StringEntity(holder.toString());
		
		    // sets the post request as the resulting string
		    httpost.setEntity(se);
		
		    // sets a request header so the page receiving the request
		    // will know what to do with it
		    httpost.setHeader("Accept", "application/json");
		    httpost.setHeader("Content-type", "application/json");
		    
		   
		    // Handles what is returned from the page
		    ResponseHandler<String> responseHandler = new BasicResponseHandler();
		    String httpResponse = httpclient.execute(httpost, responseHandler);
		    return httpResponse;
}


    @Override
protected String doInBackground(WebServiceProperties... params) {
	// TODO Auto-generated method stub
	int count = params.length;
    for (int i = 0; i < count; i++) {
        WebServiceProperties sp=params[i];
        try {
        	return makeRequest(sp.getPath(), sp.getParams());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	return null;
}



}
