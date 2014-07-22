package com.ikt.platinum;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;







import com.ikt.platinum.TabUtil.TabInfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;

public class MainActivity extends FragmentActivity implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
	private List<Fragment> fragments;
    private TabHost mTabHost;
    private ViewPager mViewPager;
    private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, TabInfo>();
    private PagerAdapter mPagerAdapter;
    private Bundle bdle;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.bdle=savedInstanceState;
        fragments = new Vector<Fragment>();
        fragments.add(Fragment.instantiate(this, NewCustomerFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, ClaimFragment.class.getName()));
        fragments.add(Fragment.instantiate(this,	LoginFragment.class.getName()));
        // Inflate the layout
        setContentView(R.layout.activity_main);
        // Initialise the TabHost
        this.initialiseTabHost(savedInstanceState);
        if (savedInstanceState != null) {
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab")); //set the tab as per the saved state
        }
        // Intialise ViewPager
        this.intialiseViewPager();
    }
    @Override
    public void onBackPressed() {
        if (mViewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
        	mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
        }
    }
   
    
    public void ReplaceLoginFragment()
    {
//    	this.mTabHost.getTabWidget().removeView(this.mTabHost.getTabWidget().getChildTabViewAt(2));
//    	TabInfo tab3=new TabInfo("Logout", LogoutFragment.class, this.bdle);
//    	mapTabInfo.remove("Login");
//    	this.mapTabInfo.put(tab3.getTag(), tab3);
    }
   
    private void intialiseViewPager() {
 
        
        this.mPagerAdapter  = new PagerAdapter(super.getSupportFragmentManager(), fragments);
        //
        this.mViewPager = (ViewPager)super.findViewById(R.id.viewpager);
        this.mViewPager.setAdapter(this.mPagerAdapter);
        this.mViewPager.setOnPageChangeListener(this);
    }
 
    /**
     * Initialise the Tab Host
     */
    private void initialiseTabHost(Bundle args) {
        mTabHost = (TabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup();
     
        TabInfo tab1=new TabInfo("Register", NewCustomerFragment.class, args);
        TabInfo tab2=new TabInfo("Claim", ClaimFragment.class, args);
        TabInfo tab3=new TabInfo("Login", ClaimFragment.class, args);
        TabUtil.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("Register").setIndicator("Register"), ( tab1));
        this.mapTabInfo.put(tab1.getTag(), tab1);
        TabUtil.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("Claim").setIndicator("Claim"), ( tab2));
        this.mapTabInfo.put(tab2.getTag(), tab2);
//        TabUtil.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("Login").setIndicator("Login",getResources().getDrawable(R.drawable.tab_indicator_ab_example)), ( tab2));
        TabUtil.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("Login").setIndicator("Login"), ( tab3));
        this.mapTabInfo.put(tab3.getTag(), tab3);
        mTabHost.setOnTabChangedListener(this);
    }
 

 

    public void onTabChanged(String tag) {
        //TabInfo newTab = this.mapTabInfo.get(tag);
        int pos = this.mTabHost.getCurrentTab();
        this.mViewPager.setCurrentItem(pos);
  //      getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.tabcontent)).commit();
//        Fragment cf=fragments.get(pos);
//        ((MyFragment) cf).changeViewContent();
    }
 
    /* (non-Javadoc)
     * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrolled(int, float, int)
     */
    @Override
    public void onPageScrolled(int position, float positionOffset,
            int positionOffsetPixels) {
        // TODO Auto-generated method stub
 
    }
 
    /* (non-Javadoc)
     * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageSelected(int)
     */
    @Override
    public void onPageSelected(int position) {
        // TODO Auto-generated method stub
        this.mTabHost.setCurrentTab(position);
        IMyFragment fragment = (IMyFragment) mPagerAdapter.instantiateItem(mViewPager, position);
        
        fragment.onBecameVisible();
    }
 
    /* (non-Javadoc)
     * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrollStateChanged(int)
     */
    @Override
    public void onPageScrollStateChanged(int state) {
        // TODO Auto-generated method stub
 
    }
}
