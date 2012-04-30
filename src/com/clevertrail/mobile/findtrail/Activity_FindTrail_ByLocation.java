package com.clevertrail.mobile.findtrail;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.clevertrail.mobile.Object_TrailList;
import com.clevertrail.mobile.R;
import com.clevertrail.mobile.utils.TitleBar;
import com.clevertrail.mobile.utils.Utils;

public class Activity_FindTrail_ByLocation extends Activity {

	private Spinner m_cbFindTrailByLocationProximity;
	public Activity_FindTrail_ByLocation mActivity = null;
	private ArrayAdapter<CharSequence> m_adapterForSpinner;
	public ProgressDialog mPD = null;

	private LocationManager locationManager;
	private Location currentLocation = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Register the listener with the Location Manager to receive location
		// updates
		locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

		TitleBar.setCustomTitleBar(this, R.layout.findtrail_bylocation,
				getString(R.string.title_findtrails), R.drawable.ic_viewtrailtab_map_unselected);

		mActivity = this;

		createComboBox();

		RadioButton rbCity = (RadioButton) findViewById(R.id.rbFindTrailByLocationCity);
		rbCity.setOnClickListener(onclickRBCity);

		RadioButton rbProximity = (RadioButton) findViewById(R.id.rbFindTrailByLocationProximity);
		rbProximity.setOnClickListener(onclickRBProximity);

		Button btnSearch = (Button) findViewById(R.id.btnSearchByLocation);
		btnSearch.setOnClickListener(onclickSearch);
	}

	// Define a listener that responds to location updates
	LocationListener locationListener = new LocationListener() {
		public void onLocationChanged(Location location) {
			// Called when a new location is found by the network location
			// provider.
			currentLocation = location;
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onProviderDisabled(String provider) {
		}
	};

	private void createComboBox() {
		m_cbFindTrailByLocationProximity = (Spinner) findViewById(R.id.cbFindTrailProximity);
		m_adapterForSpinner = new ArrayAdapter(this,
				android.R.layout.simple_spinner_item);
		m_adapterForSpinner
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		m_adapterForSpinner.add(getString(R.string.proximityspinner_5miles));
		m_adapterForSpinner.add(getString(R.string.proximityspinner_10miles));
		m_adapterForSpinner.add(getString(R.string.proximityspinner_25miles));
		m_adapterForSpinner.add(getString(R.string.proximityspinner_50miles));
		m_adapterForSpinner.add(getString(R.string.proximityspinner_100miles));

		m_cbFindTrailByLocationProximity.setAdapter(m_adapterForSpinner);
		// by default make it 25 miles
		m_cbFindTrailByLocationProximity.setSelection(2);
	}

	private OnClickListener onclickSearch = new OnClickListener() {
		public void onClick(View v) {
			mActivity.mPD = ProgressDialog.show(mActivity, "",
					getString(R.string.progress_contactingclevertrail), true);
			new Thread(new Runnable() {
				public void run() {
					int error = submitSearch();
					mPD.dismiss();
					Utils.showMessage(mActivity, error);						
					return;
				}
			}).start();
		}
	};

	//return error code if there was one
	private int submitSearch() {
		double dSearchLat = 0;
		double dSearchLong = 0;
		double dSearchDistance = 0;

		if (!Utils.isNetworkAvailable(mActivity)) {
			return R.string.error_nointernetconnection;
		}

		// is this searching by proximity
		RadioButton rbProximity = (RadioButton) findViewById(R.id.rbFindTrailByLocationProximity);
		if (rbProximity != null && rbProximity.isChecked()) {
			// search by proximity

			// do we have a location?
			if (currentLocation != null) {
				dSearchLat = currentLocation.getLatitude();
				dSearchLong = currentLocation.getLongitude();
			} else {
				return R.string.error_currentlocationnotfound;
			}
		} else {
			EditText etCity = (EditText) findViewById(R.id.txtFindTrailByLocationCity);
			if (etCity != null) {
				String sCity = etCity.getText().toString();
				if (sCity.compareTo("") != 0) {
					JSONObject json = getLocationInfo(sCity);
					if (json == null) {
						return R.string.error_couldnotconnecttogooglemaps;
					} else {
						Point pt = getLatLong(json);
						if (pt != null) {
							dSearchLat = pt.dLat;
							dSearchLong = pt.dLng;
						} else {
							return R.string.error_googlecouldnotfindcity;
						}
					}

				} else {
					return R.string.error_entercityname;
				}
			}
		}

		int nProximityPos = m_cbFindTrailByLocationProximity
				.getSelectedItemPosition();
		switch (nProximityPos) {
		case 0:
			dSearchDistance = 5;
			break;
		case 1:
			dSearchDistance = 10;
			break;
		case 2:
			dSearchDistance = 25;
			break;
		case 3:
			dSearchDistance = 50;
			break;
		case 4:
			dSearchDistance = 100;
			break;
		}

		// search for the given lat/long/dist
		JSONArray trailListJSON = fetchTrailListJSON(dSearchLat, dSearchLong,
				dSearchDistance);

		if (trailListJSON != null && trailListJSON.length() > 0) {
			int len = trailListJSON.length();

			try {
				Object_TrailList.clearTrails();
				for (int i = 0; i < len; ++i) {
					JSONObject trail = trailListJSON.getJSONObject(i);
					Object_TrailList.addTrailWithJSON(trail);
				}
			} catch (JSONException e) {
				return R.string.error_corrupttrailinfo;
			}

			Intent i = new Intent(mActivity,
					Activity_FindTrail_DisplayMap.class);
			mActivity.startActivity(i);
		} else {
			return R.string.error_notrailsfoundinsearchradius;
		}
		return 0;
	}

	private OnClickListener onclickRBCity = new OnClickListener() {
		public void onClick(View v) {
			toggleRadiobuttons(false);
		}
	};

	private OnClickListener onclickRBProximity = new OnClickListener() {
		public void onClick(View v) {
			toggleRadiobuttons(true);
		}
	};

	private void toggleRadiobuttons(boolean bProximity) {

		RadioButton rbProximity = (RadioButton) findViewById(R.id.rbFindTrailByLocationProximity);
		rbProximity.setChecked(bProximity);
		EditText etCity = (EditText) findViewById(R.id.txtFindTrailByLocationCity);
		etCity.setEnabled(!bProximity);
		RadioButton rbCity = (RadioButton) findViewById(R.id.rbFindTrailByLocationCity);
		rbCity.setChecked(!bProximity);

	}

	public JSONObject getLocationInfo(String address) {
		StringBuilder stringBuilder = new StringBuilder();
		try {

			address = address.trim();
			address = address.replaceAll(" ", "%20");

			HttpPost httppost = new HttpPost(
					"http://maps.google.com/maps/api/geocode/json?address="
							+ address + "&sensor=false");
			HttpClient client = new DefaultHttpClient();
			HttpResponse response;
			stringBuilder = new StringBuilder();

			response = client.execute(httppost);
			HttpEntity entity = response.getEntity();
			InputStream stream = entity.getContent();
			int b;
			while ((b = stream.read()) != -1) {
				stringBuilder.append((char) b);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = new JSONObject(stringBuilder.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jsonObject;
	}

	public Point getLatLong(JSONObject jsonObject) {

		Double lon = new Double(0);
		Double lat = new Double(0);

		try {
			String sStatus = ((String) jsonObject.get("status"));
			if (sStatus.compareTo("OK") != 0)
				return null;

			lon = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
					.getJSONObject("geometry").getJSONObject("location")
					.getDouble("lng");

			lat = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
					.getJSONObject("geometry").getJSONObject("location")
					.getDouble("lat");

		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}

		return new Point(lat, lon);
	}

	private class Point {

		public Point(double lat, double lng) {
			dLat = lat;
			dLng = lng;
		}

		public double dLat;
		public double dLng;
	}

	protected JSONArray fetchTrailListJSON(double dSearchLat,
			double dSearchLong, double dSearchDistance) {
		JSONArray returnJSON = null;

		HttpURLConnection urlConnection = null;
		try {
			String params = "lat=" + dSearchLat + "&lng=" + dSearchLong
					+ "&dist=" + dSearchDistance;
			String requestURL = "http://clevertrail.com/ajax/handleGetArticles.php?"
					+ params;

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
