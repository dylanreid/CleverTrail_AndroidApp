package com.clevertrail.mobile.viewtrail;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.widget.TabHost;

import com.clevertrail.mobile.Database_SavedTrails;
import com.clevertrail.mobile.R;

public class Activity_ViewTrail extends TabActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String sTrailName = "Annapurna Circuit";
		setTitle(sTrailName);

		setContentView(R.layout.viewtrail);

		// get the trail information from clevertrail.com
		JSONObject json = fetchTrailJSON(sTrailName);
		Object_TrailArticle.bSaved = false;

		// if json is null, we might not have internet activity so check saved
		// files
		if (json == null) {
			Database_SavedTrails db = new Database_SavedTrails(this);
			db.openToRead();
			String jsonString = db.getJSONString(sTrailName);
			try {
				json = new JSONObject(jsonString);
				
				// if we found the json object in the db, mark it as saved
				if (json != null) {
					Object_TrailArticle.bSaved = true;
				}

			} catch (JSONException e) {

			}
			db.close();
		}

		Object_TrailArticle.createFromJSON(sTrailName, json);
		// if creating from the web, set bSaved to false

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
						res.getDrawable(R.drawable.ic_tab_artists))
				.setContent(intent);
		tabHost.addTab(spec);

		// Do the same for the other tabs
		intent = new Intent().setClass(this, Activity_ViewTrail_Map.class);
		spec = tabHost
				.newTabSpec("map")
				.setIndicator("Map", res.getDrawable(R.drawable.ic_tab_artists))
				.setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, Activity_ViewTrail_Details.class);
		spec = tabHost
				.newTabSpec("details")
				.setIndicator("Details",
						res.getDrawable(R.drawable.ic_tab_artists))
				.setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, Activity_ViewTrail_Photos.class);
		spec = tabHost
				.newTabSpec("photos")
				.setIndicator("Photos",
						res.getDrawable(R.drawable.ic_tab_artists))
				.setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, Activity_ViewTrail_Save.class);
		spec = tabHost
				.newTabSpec("save")
				.setIndicator("Save",
						res.getDrawable(R.drawable.ic_tab_artists))
				.setContent(intent);
		tabHost.addTab(spec);

		tabHost.setCurrentTab(1);

	}

	public JSONObject fetchTrailJSON(String sTrailName) {
		JSONObject returnJSON = null;

		if (sTrailName == null || sTrailName == "")
			return null;

		HttpURLConnection urlConnection = null;
		try {
			String requestURL = String
					.format("http://clevertrail.com/ajax/handleGetArticleJSONByName.php?name=%s",
							Uri.encode(sTrailName));

			URL url = new URL(requestURL);
			urlConnection = (HttpURLConnection) url.openConnection();

			InputStream in = new BufferedInputStream(
					urlConnection.getInputStream());

			BufferedReader r = new BufferedReader(new InputStreamReader(in));
			String line;
			if ((line = r.readLine()) != null) {
				returnJSON = new JSONObject(line);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// could not connect to clevertrail.com
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (urlConnection != null)
				urlConnection.disconnect();
		}

		return returnJSON;
	}

}
