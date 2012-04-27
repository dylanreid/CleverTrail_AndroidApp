package com.clevertrail.mobile.findtrail;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
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
import android.widget.Toast;

import com.clevertrail.mobile.Database_SavedTrails;
import com.clevertrail.mobile.Object_TrailItem;
import com.clevertrail.mobile.Object_TrailList;
import com.clevertrail.mobile.R;
import com.clevertrail.mobile.utils.TitleBar;

public class Activity_FindTrail_ByLocation extends Activity {

	private Spinner m_cbFindTrailByLocationProximity;
	public Activity mActivity = null;
	private ArrayAdapter<CharSequence> m_adapterForSpinner;
	
	private LocationManager locationManager;
	private Location currentLocation = null;	

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Register the listener with the Location Manager to receive location updates
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

		TitleBar.setCustomTitleBar(this, R.layout.findtrail_bylocation,
				"CleverTrail", R.drawable.ic_viewtrailtab_map_unselected);

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
	      // Called when a new location is found by the network location provider.
	      currentLocation = location;
	    }

	    public void onStatusChanged(String provider, int status, Bundle extras) {}

	    public void onProviderEnabled(String provider) {}

	    public void onProviderDisabled(String provider) {}
	  };

	private void createComboBox() {
		m_cbFindTrailByLocationProximity = (Spinner) findViewById(R.id.cbFindTrailProximity);
		m_adapterForSpinner = new ArrayAdapter(this,
				android.R.layout.simple_spinner_item);
		m_adapterForSpinner
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		m_adapterForSpinner.add("Within 1 mile");
		m_adapterForSpinner.add("Within 5 miles");
		m_adapterForSpinner.add("Within 25 miles");
		m_adapterForSpinner.add("Within 50 miles");

		m_cbFindTrailByLocationProximity.setAdapter(m_adapterForSpinner);
		// by default make it 5 miles
		m_cbFindTrailByLocationProximity.setSelection(1);
	}
	
	private OnClickListener onclickSearch = new OnClickListener() {
		public void onClick(View v) {
			double dSearchLat;
			double dSearchLong;
			
			//is this searching by proximity
			RadioButton rbProximity = (RadioButton) findViewById(R.id.rbFindTrailByLocationProximity);
			if (rbProximity != null && rbProximity.isChecked()){
				//search by proximity
				
				//do we have a location?
				if (currentLocation != null){
					dSearchLat = currentLocation.getLatitude();
					dSearchLong = currentLocation.getLongitude();
				} else {
					showToastMessage("Your Current Location Cannot Be Found");
					return;
				}				
			} else {
				EditText etCity = (EditText) findViewById(R.id.txtFindTrailByLocationCity);
				if (etCity != null){
					String sCity = etCity.getText().toString();
					if (sCity.compareTo("") != 0){
						//search by city
						
					} else {
						//display error message if the city name is blank
						showToastMessage("Please Enter A City Name");
						return;
					}
				}				
			}
			
			
			//search for the given lat/long
			JSONArray trailListJSON = fetchTrailListJSON();
			
			if (trailListJSON != null && trailListJSON.length() > 0) {
				int len = trailListJSON.length();
				Object_TrailList.clearTrails();
				
				try {
					for (int i = 0; i < len; ++i) {
						JSONObject trail = trailListJSON.getJSONObject(i);
						Object_TrailList.addTrailWithJSON(trail);
					}
				} catch (JSONException e) {
				}
			}
			
			Intent i = new Intent(mActivity, 
					Activity_FindTrail_DisplayMap.class);
			mActivity.startActivity(i);
		}
	};
	
	private void showToastMessage(CharSequence sMessage){
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_LONG;

		Toast toast = Toast.makeText(context, sMessage, duration);
		toast.show();
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
	
	
	public JSONArray fetchTrailListJSON() {
		JSONArray returnJSON = null;
		Bundle b = getIntent().getExtras();
	
		HttpURLConnection urlConnection = null;
		try {
			String requestURL = "http://clevertrail.com/ajax/handleGetArticles.php?name=bear";

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
