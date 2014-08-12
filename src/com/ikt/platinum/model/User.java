package com.ikt.platinum.model;

public class User {
  private int user_id;
  private boolean admin;
private int venue_id;
  public User(int uid,boolean isadmin,int venueid)
  {
	  this.user_id=uid;
	  this.venue_id=venueid;
	  this.admin=isadmin;
  }
public int getUser_id() {
	return user_id;
}
public void setUser_id(int user_id) {
	this.user_id = user_id;
}
public boolean isAdmin() {
	return admin;
}
public void setAdmin(boolean admin) {
	this.admin = admin;
}
public int getVenue_id() {
	return venue_id;
}
public void setVenue_id(int venue_id) {
	this.venue_id = venue_id;
}
}
