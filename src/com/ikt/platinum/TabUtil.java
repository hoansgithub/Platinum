package com.ikt.platinum;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;



public class TabUtil{
	 public static class TabInfo {
         private String tag;
         private Class<?> clss;
         private Bundle args;
         private Fragment fragment;
         TabInfo(String tag, Class<?> clazz, Bundle args) {
             this.setTag(tag);
             this.setClss(clazz);
             this.setArgs(args);
         }
		public String getTag() {
			return this.tag;
		}
		public void setTag(String tag) {
			this.tag = tag;
		}
		public Class<?> getClss() {
			return clss;
		}
		public void setClss(Class<?> clss) {
			this.clss = clss;
		}
		public Bundle getArgs() {
			return args;
		}
		public void setArgs(Bundle args) {
			this.args = args;
		}
		public Fragment getFragment() {
			return fragment;
		}
		public void setFragment(Fragment fragment) {
			this.fragment = fragment;
		}
 
    }
	 public static class TabFactory implements TabContentFactory {
		 
	        private final Context mContext;
	        public TabFactory(Context context) {
	            mContext = context;
	        }
	        public View createTabContent(String tag) {
	            View v = new View(mContext);
	            v.setMinimumWidth(0);
	            v.setMinimumHeight(0);
	            return v;
	        }
	 
	    }
	 
	  public  static void AddTab(Activity mainActivity, TabHost tabHost, TabHost.TabSpec tabSpec, TabInfo tabInfo) {
	        // Attach a Tab view factory to the spec
	    	TabFactory t=new TabFactory(mainActivity);
	        tabSpec.setContent(t);
	        tabHost.addTab(tabSpec);
	    }
}
