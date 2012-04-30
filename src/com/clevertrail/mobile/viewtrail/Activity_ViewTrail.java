package com.clevertrail.mobile.viewtrail;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

import com.clevertrail.mobile.R;
import com.clevertrail.mobile.utils.TitleBar;

public class Activity_ViewTrail extends TabActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		String title = "CleverTrail - ";
		title = title.concat(Object_TrailArticle.sName);
		TitleBar.setCustomTitleBar(this, R.layout.viewtrail, title, 0);
		
		Resources res = getResources(); // Resource object to get Drawables
		TabHost tabHost = getTabHost(); // The activity TabHost
		TabHost.TabSpec spec; // Resusable TabSpec for each tab
		Intent intent; // Reusable Intent for each tab

		// Create an Intent to launch an Activity for the tab (to be reused)
		intent = new Intent().setClass(this, Activity_ViewTrail_Stats.class);

		// Initialize a TabSpec for each tab and add it to the TabHost
		spec = tabHost
				.newTabSpec("stats")
				.setIndicator("Stats",
						res.getDrawable(R.drawable.viewtrail_tab_stats))
				.setContent(intent);
		tabHost.addTab(spec);

		// Do the same for the other tabs
		intent = new Intent().setClass(this, Activity_ViewTrail_Map.class);
		spec = tabHost
				.newTabSpec("map")
				.setIndicator("Map",
						res.getDrawable(R.drawable.viewtrail_tab_map))
				.setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, Activity_ViewTrail_Details.class);
		spec = tabHost
				.newTabSpec("details")
				.setIndicator("Details",
						res.getDrawable(R.drawable.viewtrail_tab_details))
				.setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, Activity_ViewTrail_Photos.class);
		spec = tabHost
				.newTabSpec("photos")
				.setIndicator("Photos",
						res.getDrawable(R.drawable.viewtrail_tab_photos))
				.setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, Activity_ViewTrail_Save.class);
		spec = tabHost
				.newTabSpec("save")
				.setIndicator("Save",
						res.getDrawable(R.drawable.viewtrail_tab_save))
				.setContent(intent);
		tabHost.addTab(spec);

		tabHost.setCurrentTab(0);
	}
}
