package com.clevertrail.mobile.findtrail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

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
		m_cbFindTrailByLocationProximity = (Spinner) findViewById(R.id.cbFindTrailByLocationProximity);
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
			//is this searching by proximity
			RadioButton rbProximity = (RadioButton) findViewById(R.id.rbFindTrailByLocationProximity);
			if (rbProximity != null && rbProximity.isChecked()){
				//search by proximity
				
				//do we have a location?
				if (currentLocation != null){
					double lat = currentLocation.getLatitude();
					double lng = currentLocation.getLongitude();
					
					Intent i = new Intent(mActivity, 
							Activity_FindTrail_DisplayMap.class);
					mActivity.startActivity(i);
				} else {
					showToastMessage("Your Current Location Cannot Be Found");
				}
				
			} else {
				EditText etCity = (EditText) findViewById(R.id.txtFindTrailByLocationCity);
				if (etCity != null){
					String sCity = etCity.getText().toString();
					if (sCity.compareTo("") != 0){
						//search by city
						Intent i = new Intent(mActivity, 
								Activity_FindTrail_DisplayMap.class);
						mActivity.startActivity(i);
					} else {
						//display error message if the city name is blank
						showToastMessage("Please Enter A City Name");
					}
				}				
			}
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

		Spinner cbProximity = (Spinner) findViewById(R.id.cbFindTrailByLocationProximity);
		cbProximity.setEnabled(bProximity);
		RadioButton rbProximity = (RadioButton) findViewById(R.id.rbFindTrailByLocationProximity);
		rbProximity.setChecked(bProximity);
		EditText etCity = (EditText) findViewById(R.id.txtFindTrailByLocationCity);
		etCity.setEnabled(!bProximity);
		RadioButton rbCity = (RadioButton) findViewById(R.id.rbFindTrailByLocationCity);
		rbCity.setChecked(!bProximity);

	}
}
