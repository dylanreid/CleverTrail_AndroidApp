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

public class Activity_FindTrail_ByName extends Activity {

	public Activity_FindTrail_ByName mActivity = null;
	public ProgressDialog mPD = null;
	public String sSearchText = "";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		TitleBar.setCustomTitleBar(this, R.layout.findtrail_byname,
				"CleverTrail", R.drawable.ic_viewtrailtab_details_unselected);

		mActivity = this;

		// register events
		Button btnSaveTrail = (Button) findViewById(R.id.btnSearchByName);
		btnSaveTrail.setOnClickListener(onclickSearchByNameButton);
	}

	private OnClickListener onclickSearchByNameButton = new OnClickListener() {
		public void onClick(View v) {
			if (!Utils.isNetworkAvailable(mActivity)) {
				Utils.showToastMessage(mActivity,
						"You Don't Appear To Be Connected To The Internet");
				return;
			}

			EditText etSearchByName = (EditText) findViewById(R.id.etSearchByName);
			Editable edSearchText = etSearchByName.getText();
			String sSearchText = edSearchText.toString();
			sSearchText = sSearchText.trim();
			if (sSearchText.compareTo("") != 0 && mActivity != null) {

				mActivity.mPD = ProgressDialog.show(mActivity, "",
						"Contacting http://clevertrail.com ...", true);

				mActivity.sSearchText = sSearchText;

				new Thread(new Runnable() {
					public void run() {
						int status = submitSearch();
						mPD.dismiss();
						if (status > 0)
							Utils.showToastMessage(mActivity,
									"Error Contacting Clevertrail.com");
						return;
					}
				}).start();

			} else {
				// display message about empty string
				Context context = getApplicationContext();
				CharSequence text = "Please Enter A Trail Name";
				int duration = Toast.LENGTH_LONG;

				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
			}
		}
	};

	// 0: success
	// 1: error contacting/reading clevertrail
	// 2: error reading data;
	private int submitSearch() {
		JSONArray jsonArray = null;

		HttpURLConnection urlConnection = null;
		try {
			String requestURL = "http://clevertrail.com/ajax/handleGetArticles.php";

			requestURL = requestURL.concat("?name=").concat(
					mActivity.sSearchText);

			URL url = new URL(requestURL);
			urlConnection = (HttpURLConnection) url.openConnection();

			InputStream in = new BufferedInputStream(
					urlConnection.getInputStream());

			BufferedReader r = new BufferedReader(new InputStreamReader(in));
			String line;
			if ((line = r.readLine()) != null) {
				jsonArray = new JSONArray(line);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// could not connect to clevertrail.com
			e.printStackTrace();
			return 1;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		} finally {
			if (urlConnection != null)
				urlConnection.disconnect();
		}

		Object_TrailList.clearTrails();
		if (jsonArray != null && jsonArray.length() > 0) {
			int len = jsonArray.length();
			try {				
				for (int i = 0; i < len && i < 20; ++i) {
					JSONObject trail = jsonArray.getJSONObject(i);
					Object_TrailList.addTrailWithJSON(trail);
				}
			} catch (JSONException e) {
				return 2;
			}
		}
		
		int icon = R.drawable.ic_viewtrailtab_details_unselected;
		String title = "CleverTrail - Found Trails";
		Intent i = new Intent(mActivity, Activity_ListTrails.class);
		i.putExtra("icon", icon);
		i.putExtra("title", title);
		mActivity.startActivity(i);
		
		return 0;
	}
}
