package com.clevertrail.mobile;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class Activity_ListTrails extends Activity {

	View_ListTrailsList trailList;
	Adapter_ListTrails trailAdapter;
	ArrayList<Object_TrailItem> arTrails;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.listtrails);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.window_title);

		arTrails = new ArrayList();
		JSONArray trailListJSON = fetchTrailListJSON();

		int len = trailListJSON.length();
		try {
			for (int i = 0; i < len; ++i) {
				JSONObject trail = trailListJSON.getJSONObject(i);

				arTrails.add(Object_TrailItem.createFromJSON(trail));
			}
		} catch (JSONException e) {
		}

		trailList = (View_ListTrailsList) findViewById(R.id.traillist);
		trailList.mActivity = this;

		trailAdapter = new Adapter_ListTrails(this, arTrails);
		trailList.setAdapter(trailAdapter);
	}

	@Override
	public void onDestroy() {
		trailList.setAdapter(null);
		super.onDestroy();
	}

	public JSONArray fetchTrailListJSON() {
		JSONArray returnJSON = null;

		HttpURLConnection urlConnection = null;
		try {
			String requestURL = "http://clevertrail.com/ajax/handleGetArticles.php";

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
