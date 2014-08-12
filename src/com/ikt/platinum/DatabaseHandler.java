package com.ikt.platinum;

import java.util.ArrayList;
import java.util.List;

import com.ikt.platinum.model.Record;
import com.ikt.platinum.model.User;
import com.ikt.platinum.model.Venue;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper  {

	 private static final int DATABASE_VERSION = 3;
	    // Database Name
	    private static final String DATABASE_NAME = "platinumDB";
	    
	    
	    //*tables
		    //VENUES
		    private final static String TBLVENUE_NAME="venue";
		    private final static String VENUE_COLUMN_ID="venue_id";
		    private final static String VENUE_COLUMN_NAME="venue_name";
		    
		    //Credential
		    private final static String TBLUSER_NAME="user";
		    private final static String USER_COLUMN_ID="user_id";
		    private final static String USER_COLUMN_ADMIN="is_admin";
		    private final static String USER_COLUMN_VENUE="venue_id";
		    //Record
		    private final static String TBLRECORD_NAME="record";
		    private final static String RECORD_COLUMN_CUS="customer_id";
		    private final static String RECORD_COLUMN_TIME= "time";
		    private final static String RECORD_COLUMN_DATE="date";
		    private final static String RECORD_COLUMN_QUANTITY= "quantity";
		    private final static String RECORD_COLUMN_VENUE ="venue_id";
		    private final static String RECORD_COLUMN_PG ="pg_id";
		    private final static String RECORD_COLUMN_CARD ="cardnumber";
		    private final static String RECORD_COLUMN_TYPE ="record_type_id";
		    
	    //*end tables
	    
	    
	    
	public DatabaseHandler(Context context) {
		 super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		 String CREATE_VENUE_TABLE = "CREATE TABLE  " + TBLVENUE_NAME + "("
	                + VENUE_COLUMN_ID + " INTEGER," + VENUE_COLUMN_NAME + " TEXT"+")";
	        db.execSQL(CREATE_VENUE_TABLE);
	        
	        String CREATE_USER_TABLE = "CREATE TABLE  " + TBLUSER_NAME + "("
	                + USER_COLUMN_ID + " INTEGER," + USER_COLUMN_ADMIN + " INTEGER"+", "+USER_COLUMN_VENUE+" INTEGER)";
	        db.execSQL(CREATE_USER_TABLE);
	        
	        String CREATE_RECORD_TABLE="CREATE TABLE "+TBLRECORD_NAME +"("
	        		+RECORD_COLUMN_CUS+" INTEGER,"
	        +RECORD_COLUMN_TIME+" TEXT,"
	        +RECORD_COLUMN_DATE+" TEXT, "
	        +RECORD_COLUMN_QUANTITY+" INTEGER,"
	        +RECORD_COLUMN_VENUE+" INTEGER, "
	        +RECORD_COLUMN_PG+" INTEGER,"
	        +RECORD_COLUMN_CARD+" INTEGER,"
	        +RECORD_COLUMN_TYPE+" INTEGER )";
	        db.execSQL(CREATE_RECORD_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		   // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TBLVENUE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TBLUSER_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TBLRECORD_NAME);
 
        // Create tables again
        onCreate(db);
	}
	
	//venue functions
	public void addVenue(Venue venue) {
		 SQLiteDatabase db = this.getWritableDatabase();
		 
		    ContentValues values = new ContentValues();
		    values.put(VENUE_COLUMN_ID, venue.getVenue_id()); 
		    values.put(VENUE_COLUMN_NAME, venue.getVenue_name()); 
		 
		    // Inserting Row
		    db.insert(TBLVENUE_NAME, null, values);
		    db.close(); // Closing database connection
		
	}
	 
	public Venue getVenue(int id) {
		 SQLiteDatabase db = this.getReadableDatabase();
		 
		    Cursor cursor = db.query(TBLVENUE_NAME,
		    		new String[] {  VENUE_COLUMN_ID, VENUE_COLUMN_NAME }, VENUE_COLUMN_ID + "=?",
		            new String[] { String.valueOf(id) }, null, null, null, null);
		    if (cursor != null)
		        cursor.moveToFirst();
		 
		    Venue v = new Venue(Integer.parseInt(cursor.getString(0)), cursor.getString(1));
		    // return contact
		    return v;
	}
	 
	public List<Venue> getAllVenues() {
		 // Select All Query
	    String selectQuery = "SELECT  * FROM " + TBLVENUE_NAME;
	    List<Venue> vl=new ArrayList<Venue>();
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	            Venue ve = new Venue();
	            ve.setVenue_id(Integer.parseInt(cursor.getString(0)));
	            ve.setVenue_name(cursor.getString(1));
	           
	            // Adding contact to list
	            vl.add(ve);
	        } while (cursor.moveToNext());
	    }
	 
	    // return contact list
	    return vl;
	}
	 

	public int updateVenue(Venue venue) {
		return 0;
	}
	 
	public int deleteVenue(int venueid) {
		return 0;
	}
	public int deleteAllVenue()
	{
		 SQLiteDatabase db = this.getWritableDatabase();
		   int res= db.delete(TBLVENUE_NAME,"",null);
		    db.close();
		    return res;
	}
	//user  functions
	public int addUser(User user) {
		 SQLiteDatabase db = this.getWritableDatabase();
		User checku= this.getUser();
		int res=0;
		if(checku!=null)
		{
			this.updateUser(user);
		}
		else{
			 ContentValues values = new ContentValues();
			    values.put(USER_COLUMN_ID, user.getUser_id()); 
			    values.put(USER_COLUMN_ADMIN, user.isAdmin()); 
			    values.put(USER_COLUMN_VENUE, user.getVenue_id());
			  res=   (int) db.insert(TBLUSER_NAME, null, values);
		}
		
		
		
		    db.close(); // Closing database connection
		return res;
	}
	public User getUser() {
		 SQLiteDatabase db = this.getReadableDatabase();
		 
		    Cursor cursor = db.query(TBLUSER_NAME,
		    		new String[] {  USER_COLUMN_ID, USER_COLUMN_ADMIN,USER_COLUMN_VENUE }, "",
		           null, null, null, null, null);
		    if  (cursor!=null)
		    {
		    	if(cursor.moveToFirst())
		    	{
				    boolean isadmin=cursor.getInt(1)==0?false:true;
				    return new User(Integer.parseInt(cursor.getString(0)),isadmin, cursor.getInt(2));
		    	}
		    	else{
		    		return null;
		    	}
		    }
		    else{
		    	return null;
		    }
		   
	}
	public int updateUser(User user)
	{
		  SQLiteDatabase db = this.getWritableDatabase();
		  
		    ContentValues values = new ContentValues();
		    values.put(USER_COLUMN_ID, user.getUser_id());
		    values.put(USER_COLUMN_ADMIN, user.isAdmin());
		    values.put(USER_COLUMN_VENUE,user.getVenue_id());
		    // updating row
		    return db.update(TBLUSER_NAME, values,"",
		            new String[] {  });
	}
	public int deleteAllUser()
	{
		 SQLiteDatabase db = this.getWritableDatabase();
		   int res= db.delete(TBLUSER_NAME,"",null);
		    db.close();
		    return res;
	}
	//record functions
	public int addRecord(Record r)
	{
		 SQLiteDatabase db = this.getWritableDatabase();
			
				 ContentValues values = new ContentValues();
				 values.put(RECORD_COLUMN_CUS, r.getCustomer_id());
				 values.put(RECORD_COLUMN_CARD, r.getCardnumber());
				 values.put(RECORD_COLUMN_DATE,r.getDate());
				 values.put(RECORD_COLUMN_PG,r.getPg_id());
				 values.put(RECORD_COLUMN_QUANTITY,r.getQuantity());
				 values.put(RECORD_COLUMN_TIME,r.getTime());
				 values.put(RECORD_COLUMN_TYPE,r.getRecord_type_id());
				 values.put(RECORD_COLUMN_VENUE,r.getVenue_id());
				 int	  res=   (int) db.insert(TBLRECORD_NAME, null, values);
			
			
			
			
			    db.close(); // Closing database connection
			return res;
		
	}
	public List<Record> getAllRecords()
	{
		 // Select All Query
	    String selectQuery = "SELECT  * FROM " + TBLRECORD_NAME;
	    List<Record> vl=new ArrayList<Record>();
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	            Record ve = new Record();
	            ve.setCustomer_id(cursor.getInt(cursor.getColumnIndex("customer_id")));
	            ve.setCardnumber(cursor.getInt(cursor.getColumnIndex("cardnumber")));
	            ve.setDate(cursor.getString(cursor.getColumnIndex("date")));
	            ve.setTime(cursor.getString(cursor.getColumnIndex("time")));
	            ve.setPg_id(cursor.getInt(cursor.getColumnIndex("pg_id")));
	            ve.setQuantity(cursor.getInt(cursor.getColumnIndex("quantity")));
	            ve.setRecord_type_id(cursor.getInt(cursor.getColumnIndex("record_type_id")));
	            ve.setVenue_id(cursor.getInt(cursor.getColumnIndex("venue_id")));
	            // Adding contact to list
	            vl.add(ve);
	        } while (cursor.moveToNext());
	    }
	 
	    // return contact list
	    return vl;
	}
	public int deleteAllRecord()
	{
		 SQLiteDatabase db = this.getWritableDatabase();
		   int res= db.delete(TBLRECORD_NAME,"",null);
		    db.close();
		    return res;
	}

}
