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

package com.clevertrail.mobile;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;

import com.clevertrail.mobile.utils.TitleBar;
import com.clevertrail.mobile.utils.Utils;

//Multi-purpose class for displaying trails from various sources
public class Activity_ListTrails extends Activity {

	View_ListTrailsList trailList;
	Adapter_ListTrails trailAdapter;
	ArrayList<Object_TrailItem> arTrails;
	final int nMaxTrails = 20;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		arTrails = Object_TrailList.arTrails;
		int icon = R.drawable.ic_viewtrailtab_save_unselected;
		String title = getString(R.string.title_traillist);

		//See if there was an icon and/or title sent into the class
		Bundle b = getIntent().getExtras();
		if (b != null) {
			icon = b.getInt("icon");
			title = b.getString("title");
		}

		//if there were trails sent in, display them, otherwise show the notrails page
		if (arTrails != null && arTrails.size() > 0) {
			TitleBar.setCustomTitleBar(this, R.layout.listtrails, title, icon);
		} else {
			TitleBar.setCustomTitleBar(this, R.layout.listtrails_notrails,
					title, icon);
		}

		//load the trail adapter with the trails
		trailList = (View_ListTrailsList) findViewById(R.id.traillist);
		if (trailList != null) {
			trailList.mActivity = this;
			trailAdapter = new Adapter_ListTrails(this, arTrails);
			trailList.setAdapter(trailAdapter);
		}
		
		//display message indicating only first nMaxTrails trails were shown
		if (arTrails != null && arTrails.size() >= nMaxTrails) {
			Utils.showMessage(this, R.string.alert_showing20trails);
		}
	}

	//get the trail name at the specified position in the arTrails array
	public String getTrailNameAt(int pos) {
		String sName = "";
		if (arTrails != null && arTrails.size() > pos) {
			return arTrails.get(pos).sName;
		}
		return sName;
	}

	@Override
	public void onDestroy() {
		if (trailList != null)
			trailList.setAdapter(null);

		super.onDestroy();
	}

}
