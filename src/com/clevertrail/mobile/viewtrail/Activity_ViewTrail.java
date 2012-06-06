/* 
	Copyright (C) 2012 Dylan Reid

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.clevertrail.mobile.viewtrail;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

import com.clevertrail.mobile.R;
import com.clevertrail.mobile.utils.TitleBar;

//main class for display a trail (tabbed activity)
public class Activity_ViewTrail extends TabActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//add the title and title bar
		String title = getString(R.string.app_name) + " - ";
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

		//first tab should be selected (stats tab)
		tabHost.setCurrentTab(0);
	}
}
