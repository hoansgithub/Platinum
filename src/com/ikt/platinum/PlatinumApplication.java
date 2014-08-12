package com.ikt.platinum;

import java.util.ArrayList;
import java.util.List;

import com.ikt.platinum.model.Venue;

import android.app.Application;

public class PlatinumApplication extends Application {
	 	private boolean LOGGED_IN;
	 	private int PGID=0;
	 	private int VENUE_ID=0;
	 	private boolean MANAGER=false;
	 	private List<Venue> VENUES;
	 	private String SERVICE_HOST="http://demo.iktknowledge.com/PlatinumWeb/service";
		public boolean isLOGGED_IN() {
			return LOGGED_IN;
		}

		public void setLOGGED_IN(boolean lOGGED_IN) {
			LOGGED_IN = lOGGED_IN;
		}

		public int getPGID() {
			return PGID;
		}

		public void setPGID(int pGID) {
			PGID = pGID;
		}

		public int getVENUE_ID() {
			return VENUE_ID;
		}

		public void setVENUE_ID(int vENUE_ID) {
			VENUE_ID = vENUE_ID;
		}

		public List<Venue> getVENUES() {
			if(this.VENUES==null)
			{
				VENUES=new ArrayList<Venue>();
			}
			return VENUES;
		}

		public void setVENUES(List<Venue> vENUES) {
			VENUES = vENUES;
		}

		public boolean isMANAGER() {
			return MANAGER;
		}

		public void setMANAGER(boolean mANAGER) {
			MANAGER = mANAGER;
		}

		public String getSERVICE_HOST() {
			return SERVICE_HOST;
		}

	   
}
