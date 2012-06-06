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

import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.clevertrail.mobile.Object_TrailItem;
import com.clevertrail.mobile.Object_TrailList;
import com.clevertrail.mobile.R;
import com.clevertrail.mobile.utils.MapTrailOverlay;
import com.clevertrail.mobile.utils.TitleBar;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

//a class to display a google map of trails found during a search
public class Activity_FindTrail_DisplayMap extends MapActivity {

	MapView mapView;
	List<Overlay> mapOverlays;
	Drawable drawTrailhead;
	MapTrailOverlay overlayTrailhead;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//create the titlebar
		TitleBar.setCustomTitleBar(this, R.layout.findtrail_displaymap,
				getString(R.string.title_foundtrails), R.drawable.ic_viewtrailtab_map_unselected);

		//create the map and the found trailheads (markers)
		createMap();
		createMarkers();
	}

	private void createMap() {
		mapView = (MapView) findViewById(R.id.mvFindTrail);
		if (mapView != null)
			mapView.setBuiltInZoomControls(true);
	}

	private void createMarkers() {
		if (mapView == null)
			return;
		
		//return immediately if there are no trails
		if (Object_TrailList.arTrails == null
				|| Object_TrailList.arTrails.size() <= 0)
			return;

		mapOverlays = mapView.getOverlays();
		drawTrailhead = getResources().getDrawable(R.drawable.trailmarker);
		
		int nMaxLat = 0, nMinLat = 0, nMaxLng = 0, nMinLng = 0;

		//iterate through all the found trails and keep track of the max and min
		//lat/lng we have so we can zoom to the correct level in the google map
		for (int i = 0; i < Object_TrailList.arTrails.size(); i++) {
			Object_TrailItem trail = Object_TrailList.arTrails.get(i);
			
			int nLat = (int) (trail.mTrailheadLat * 1E6);
			int nLng = (int) (trail.mTrailheadLng * 1E6);

			//keep track of the max/min lat/lng for later
			if (i == 0) {
				nMaxLat = nLat;
				nMinLat = nLat;
				nMaxLng = nLng;
				nMinLng = nLng;
			} else {
				nMaxLat = Math.max(nLat, nMaxLat);
				nMinLat = Math.min(nLat, nMinLat);
				nMaxLng = Math.max(nLng, nMaxLng);
				nMinLng = Math.min(nLng, nMinLng);
			}

			GeoPoint point = new GeoPoint(nLat, nLng);

			//set up the marker's information
			String sDescription = "";
			if (trail.sDifficulty.compareTo("") != 0){
				sDescription = sDescription.concat("Difficulty: " + trail.sDifficulty);
			}
			if (trail.sDistance.compareTo("") != 0){
				if (sDescription.compareTo("") != 0)
					sDescription = sDescription.concat("\n");
				sDescription = sDescription.concat("Distance: " + trail.sDistance);
			}
			if (trail.sTimeRequired.compareTo("") != 0){
				if (sDescription.compareTo("") != 0)
					sDescription = sDescription.concat("\n");
				sDescription = sDescription.concat("Time Required: " + trail.sTimeRequired);
			}
			if (trail.sTrailType.compareTo("") != 0){
				if (sDescription.compareTo("") != 0)
					sDescription = sDescription.concat("\n");
				sDescription = sDescription.concat("Trail Type: " + trail.sTrailType);
			}
			if (trail.sNearestCity.compareTo("") != 0){
				if (sDescription.compareTo("") != 0)
					sDescription = sDescription.concat("\n");
				sDescription = sDescription.concat("Nearest City: " + trail.sNearestCity);
			}

			//add the marker to the overlay
			OverlayItem overlayItem = new OverlayItem(point, trail.sName,
					sDescription);

			if (overlayTrailhead == null)
				overlayTrailhead = new MapTrailOverlay(drawTrailhead, mapView, this);

			// add the overlay
			overlayTrailhead.addOverlay(overlayItem);
		}

		// now add the overlays to the map
		mapOverlays.add(overlayTrailhead);
		
		// position the map to show the markers
		MapController mc = mapView.getController();
		if (nMaxLat != nMinLat) {			
			mc.zoomToSpan(Math.abs(nMaxLat - nMinLat),
					Math.abs(nMaxLng - nMinLng));
		} else {
			mc.setZoom(10);
		}
		
		//now reposition one last time
		mc.animateTo(new GeoPoint((nMaxLat + nMinLat) / 2,
				(nMaxLng + nMinLng) / 2));
	}

	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

}
