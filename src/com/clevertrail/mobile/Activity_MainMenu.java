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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.clevertrail.mobile.findtrail.Activity_FindTrail_ByLocation;
import com.clevertrail.mobile.findtrail.Activity_FindTrail_ByName;
import com.clevertrail.mobile.utils.TitleBar;
import com.clevertrail.mobile.utils.Utils;

//activity to display the startup main menu
//there are 4 options:
//1) Find Trail By Name
//2) Find Trail By Location
//3) Saved Trails
//4) About CleverTrail
public class Activity_MainMenu extends Activity {

	public Activity_MainMenu mActivity = null;
	public ProgressDialog mPD = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mActivity = this;

		TitleBar.setCustomTitleBar(this, R.layout.mainmenu, getString(R.string.app_name), 0);

		//register all the events for clicking on the different menu options
		LinearLayout llFindTrailsLocation = (LinearLayout) findViewById(R.id.mainmenu_findtrails_location);
		llFindTrailsLocation.setOnClickListener(onclickFindTrailsLocation);

		LinearLayout llFindTrailsName = (LinearLayout) findViewById(R.id.mainmenu_findtrails_name);
		llFindTrailsName.setOnClickListener(onclickFindTrailsName);

		LinearLayout llSavedTrail = (LinearLayout) findViewById(R.id.mainmenu_savedtrails);
		llSavedTrail.setOnClickListener(onclickSavedTrails);

		LinearLayout llAboutClevertrail = (LinearLayout) findViewById(R.id.mainmenu_about);
		llAboutClevertrail.setOnClickListener(onclickAboutClevertrail);
	}

	//start the activity for finding trails by location
	private OnClickListener onclickFindTrailsLocation = new OnClickListener() {
		public void onClick(View v) {
			Intent i = new Intent(mActivity,
					Activity_FindTrail_ByLocation.class);
			mActivity.startActivity(i);
		}
	};

	//start the activity for finding trails by name
	private OnClickListener onclickFindTrailsName = new OnClickListener() {
		public void onClick(View v) {
			Intent i = new Intent(mActivity, Activity_FindTrail_ByName.class);
			mActivity.startActivity(i);
		}
	};

	//start the activity for displaying saved trails
	private OnClickListener onclickSavedTrails = new OnClickListener() {
		public void onClick(View v) {
			
			//display a progress dialog while loading the saved trails
			mActivity.mPD = ProgressDialog.show(mActivity, "",
					getString(R.string.progress_loadingsavedtrails), true);

			new Thread(new Runnable() {
				public void run() {
					int error = Database_SavedTrails.openSavedTrails(mActivity);
					mPD.dismiss();
					//if there was an error, display it
					Utils.showMessage(mActivity, error);
					return;
				}
			}).start();

		}
	};
	
	//start the activity for displaying about clevertrail
	private OnClickListener onclickAboutClevertrail = new OnClickListener() {
		public void onClick(View v) {
			Intent i = new Intent(mActivity, Activity_AboutClevertrail.class);
			mActivity.startActivity(i);
		}
	};

}
