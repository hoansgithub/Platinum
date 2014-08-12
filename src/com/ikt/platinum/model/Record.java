package com.ikt.platinum.model;

public class Record {
private String time;
private String date;
private int quantity;
private int venue_id;
private int pg_id;
private int cardnumber;
private int record_type_id;
private int customer_id;
public Record(int Cusid,String time,String date,int quantity,int venue_id,int pg_id,int cardnumber,int record_type_id)
{
	this.customer_id=Cusid;
	this.time=time;
	this.date=date;
	this.quantity=quantity;
	this.venue_id=venue_id;
	this.pg_id=pg_id;
	this.cardnumber=cardnumber;
	this.record_type_id=record_type_id;
}

public Record() {
	// TODO Auto-generated constructor stub
}

public String getTime() {
	return time;
}
public void setTime(String time) {
	this.time = time;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public int getQuantity() {
	return quantity;
}
public void setQuantity(int quantity) {
	this.quantity = quantity;
}
public int getVenue_id() {
	return venue_id;
}
public void setVenue_id(int venue_id) {
	this.venue_id = venue_id;
}
public int getPg_id() {
	return pg_id;
}
public void setPg_id(int pg_id) {
	this.pg_id = pg_id;
}
public int getCardnumber() {
	return cardnumber;
}
public void setCardnumber(int cardnumber) {
	this.cardnumber = cardnumber;
}
public int getRecord_type_id() {
	return record_type_id;
}
public void setRecord_type_id(int record_type_id) {
	this.record_type_id = record_type_id;
}

public int getCustomer_id() {
	return customer_id;
}

public void setCustomer_id(int customer_id) {
	this.customer_id = customer_id;
}

}
