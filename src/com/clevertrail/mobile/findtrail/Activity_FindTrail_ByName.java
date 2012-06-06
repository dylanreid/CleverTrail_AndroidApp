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

package com.clevertrail.mobile.findtrail;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.clevertrail.mobile.Activity_ListTrails;
import com.clevertrail.mobile.Object_TrailList;
import com.clevertrail.mobile.R;
import com.clevertrail.mobile.utils.TitleBar;
import com.clevertrail.mobile.utils.Utils;

//class to find trails by name
public class Activity_FindTrail_ByName extends Activity {

	public Activity_FindTrail_ByName mActivity = null;
	public ProgressDialog mPD = null;
	public String sSearchText = "";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//create the titlebar
		TitleBar.setCustomTitleBar(this, R.layout.findtrail_byname,
				getString(R.string.title_findtrails),
				R.drawable.ic_viewtrailtab_details_unselected);

		mActivity = this;

		// register events
		Button btnSaveTrail = (Button) findViewById(R.id.btnSearchByName);
		btnSaveTrail.setOnClickListener(onclickSearchByNameButton);
	}

	//event to handle search button click
	private OnClickListener onclickSearchByNameButton = new OnClickListener() {
		public void onClick(View v) {
			if (!Utils.isNetworkAvailable(mActivity)) {
				Utils.showMessage(mActivity,
						R.string.error_nointernetconnection);
				return;
			}

			EditText etSearchByName = (EditText) findViewById(R.id.etSearchByName);
			Editable edSearchText = etSearchByName.getText();
			String sSearchText = edSearchText.toString();
			sSearchText = sSearchText.trim();
			
			//do we have a trail name to look for?
			if (sSearchText.compareTo("") != 0 && mActivity != null) {

				//display progress dialog while we contact clevertrail.com
				mActivity.mPD = ProgressDialog.show(mActivity, "",
						getString(R.string.progress_contactingclevertrail),
						true);

				mActivity.sSearchText = sSearchText;

				new Thread(new Runnable() {
					public void run() {
						int error = submitSearch();
						mPD.dismiss();
						Utils.showMessage(mActivity, error);
						return;
					}
				}).start();

			} else {
				// display message about empty string
				Utils.showMessage(mActivity, R.string.error_entertrailname);
			}
		}
	};

	//function to do the actual api call to clevertrail.com
	private int submitSearch() {
		JSONArray jsonArray = null;

		HttpURLConnection urlConnection = null;
		try {
			//api call to find trails by name
			String requestURL = "http://clevertrail.com/ajax/handleGetArticles.php";
			String sSearch = mActivity.sSearchText;
			sSearch = sSearch.replace(" ", "%20");
			sSearch = sSearch.replace("'", "\'");

			requestURL = requestURL.concat("?name=").concat(sSearch);

			URL url = new URL(requestURL);
			urlConnection = (HttpURLConnection) url.openConnection();

			InputStream in = new BufferedInputStream(
					urlConnection.getInputStream());

			BufferedReader r = new BufferedReader(new InputStreamReader(in));
			String line;
			//did we get a match from clevertrail.com?
			if ((line = r.readLine()) != null) {
				jsonArray = new JSONArray(line);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return R.string.error_contactingclevertrail;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// could not connect to clevertrail.com
			e.printStackTrace();
			return R.string.error_contactingclevertrail;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return R.string.error_corrupttrailinfo;
		} finally {
			if (urlConnection != null)
				urlConnection.disconnect();
		}

		//clear the static object that holds the trails
		Object_TrailList.clearTrails();
		if (jsonArray != null && jsonArray.length() > 0) {
			int len = jsonArray.length();
			try {
				//add each trail to the array of trails
				for (int i = 0; i < len && i < 20; ++i) {
					JSONObject trail = jsonArray.getJSONObject(i);
					Object_TrailList.addTrailWithJSON(trail);
				}
			} catch (JSONException e) {
				return R.string.error_corrupttrailinfo;
			}
		}

		//prepare the extras before starting the trail list activity
		int icon = R.drawable.ic_viewtrailtab_details_unselected;
		Intent i = new Intent(mActivity, Activity_ListTrails.class);
		i.putExtra("icon", icon);
		i.putExtra("title", mActivity.getString(R.string.title_foundtrails));
		mActivity.startActivity(i);

		return 0;
	}
}
