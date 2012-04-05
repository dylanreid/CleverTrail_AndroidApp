package com.clevertrail.mobile.findtrail;

import android.app.Activity;
import android.os.Bundle;

import com.clevertrail.mobile.R;
import com.clevertrail.mobile.utils.TitleBar;

public class Activity_FindTrail_ByLocation extends Activity {
	
	public Activity mActivity = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		TitleBar.setCustomTitleBar(this, R.layout.findtrail_bylocation, "CleverTrail", R.drawable.ic_viewtrailtab_map_unselected);		
		
		mActivity = this;
		
	}
}
