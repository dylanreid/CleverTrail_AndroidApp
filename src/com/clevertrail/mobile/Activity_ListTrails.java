package com.clevertrail.mobile;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;

import com.clevertrail.mobile.utils.TitleBar;

public class Activity_ListTrails extends Activity {

	View_ListTrailsList trailList;
	Adapter_ListTrails trailAdapter;
	ArrayList<Object_TrailItem> arTrails;
	
	private boolean bSavedTrails = false;
	private boolean bFindTrails = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle b = getIntent().getExtras();
		int icon = R.drawable.ic_viewtrailtab_save_unselected;
		String title;
		
		if (b != null && b.getBoolean("savedtrails")){
			bSavedTrails = true;
			icon = R.drawable.ic_viewtrailtab_save_unselected;
			title = "CleverTrail - Saved Trails";
		} else {
			bFindTrails = true;
			icon = R.drawable.ic_viewtrailtab_details_unselected;
			title = "CleverTrail - Found Trails";
		}

		arTrails = new ArrayList();
		JSONArray trailListJSON = fetchTrailListJSON();
		
		if (trailListJSON != null && trailListJSON.length() > 0) {
			int len = trailListJSON.length();
			try {
				for (int i = 0; i < len; ++i) {
					JSONObject trail = trailListJSON.getJSONObject(i);

					arTrails.add(Object_TrailItem.createFromJSON(trail));
				}
			} catch (JSONException e) {
			}

			TitleBar.setCustomTitleBar(this, R.layout.listtrails, title, icon);
			
			trailList = (View_ListTrailsList) findViewById(R.id.traillist);
			if (trailList != null) {
				trailList.mActivity = this;
				trailAdapter = new Adapter_ListTrails(this, arTrails);
				trailList.setAdapter(trailAdapter);
			}
		} else {
			// else there are no trails to display
			TitleBar.setCustomTitleBar(this, R.layout.listtrails_notrails, title, icon);
		}
	}

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

	public JSONArray fetchTrailListJSON() {
		JSONArray returnJSON = null;
		Bundle b = getIntent().getExtras();
		
		// if the activity was launched to open the saved trails, check now
		if (bSavedTrails) {
			Database_SavedTrails db = new Database_SavedTrails(this);
			db.openToRead();
			String jsonString = db.getJSONString();						
			
			try {
				returnJSON = new JSONArray(jsonString);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			db.close();
			
			return returnJSON;
		}

		HttpURLConnection urlConnection = null;
		try {
			String requestURL = "http://clevertrail.com/ajax/handleGetArticles.php";

			String sName = URLEncoder.encode(b.getString("name"), "utf-8");

			if (sName != "") {
				requestURL = requestURL.concat("?name=").concat(sName);
			}

			URL url = new URL(requestURL);
			urlConnection = (HttpURLConnection) url.openConnection();

			InputStream in = new BufferedInputStream(
					urlConnection.getInputStream());

			BufferedReader r = new BufferedReader(new InputStreamReader(in));
			String line;
			if ((line = r.readLine()) != null) {
				returnJSON = new JSONArray(line);
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
