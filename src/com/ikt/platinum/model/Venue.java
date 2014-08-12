package com.ikt.platinum.model;

public class Venue {
 private int venue_id;
 private String venue_name;
public Venue(int venueid, String venuename) {
	this.venue_id=venueid;
	this.venue_name=venuename;
}
public Venue() {
	// TODO Auto-generated constructor stub
}
public int getVenue_id() {
	return venue_id;
}
public void setVenue_id(int venue_id) {
	this.venue_id = venue_id;
}
public String getVenue_name() {
	return venue_name;
}
public void setVenue_name(String venue_name) {
	this.venue_name = venue_name;
}
}
