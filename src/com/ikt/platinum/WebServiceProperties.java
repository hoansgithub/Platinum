package com.ikt.platinum;

import java.util.Map;

public class WebServiceProperties {
	private String path;private  Map<?, ?> params;
    public WebServiceProperties(String path,Map<?,?> params)
    {
    	this.params=params;
    	this.path=path;
    }
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Map<?, ?> getParams() {
		return params;
	}
	public void setParams(Map<?, ?> params) {
		this.params = params;
	}
	
}
