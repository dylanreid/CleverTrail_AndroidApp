package com.clevertrail.mobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.clevertrail.mobile.findtrail.Activity_FindTrail_ByLocation;
import com.clevertrail.mobile.findtrail.Activity_FindTrail_ByName;
import com.clevertrail.mobile.utils.TitleBar;

public class Activity_MainMenu extends Activity {
	
	public Activity_MainMenu mActivity = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mActivity = this;
        
        TitleBar.setCustomTitleBar(this, R.layout.mainmenu, "CleverTrail", 0);
        
        LinearLayout llFindTrailsLocation = (LinearLayout) findViewById(R.id.mainmenu_findtrails_location);
        llFindTrailsLocation.setOnClickListener(onclickFindTrailsLocation);
        
        LinearLayout llFindTrailsName = (LinearLayout) findViewById(R.id.mainmenu_findtrails_name);
        llFindTrailsName.setOnClickListener(onclickFindTrailsName);
        
        LinearLayout llSavedTrail = (LinearLayout) findViewById(R.id.mainmenu_savedtrails);
        llSavedTrail.setOnClickListener(onclickSavedTrails);
        
        LinearLayout llAboutClevertrail = (LinearLayout) findViewById(R.id.mainmenu_about);
        llAboutClevertrail.setOnClickListener(onclickAboutClevertrail);
    }
    
    private OnClickListener onclickFindTrailsLocation = new OnClickListener() {
		public void onClick(View v) {
				Intent i = new Intent(mActivity,
						Activity_FindTrail_ByLocation.class);
				mActivity.startActivity(i);
		}
	};
    
    private OnClickListener onclickFindTrailsName = new OnClickListener() {
		public void onClick(View v) {
				Intent i = new Intent(mActivity,
						Activity_FindTrail_ByName.class);
				mActivity.startActivity(i);
		}
	};
	
    private OnClickListener onclickSavedTrails = new OnClickListener() {
		public void onClick(View v) {
				Intent i = new Intent(mActivity,
						Activity_ListTrails.class);
				i.putExtra("savedtrails", true);			
				mActivity.startActivity(i);
		}
	};
	
    private OnClickListener onclickAboutClevertrail = new OnClickListener() {
		public void onClick(View v) {
				Intent i = new Intent(mActivity,
						Activity_AboutClevertrail.class);
				mActivity.startActivity(i);
		}
	};
 
}
