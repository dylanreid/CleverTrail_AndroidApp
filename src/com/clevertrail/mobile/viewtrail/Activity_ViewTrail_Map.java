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


package com.clevertrail.mobile.viewtrail;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.clevertrail.mobile.R;
import com.clevertrail.mobile.utils.MapMarkerOverlay;
import com.clevertrail.mobile.utils.Utils;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

//class to display a google map of the trail with trailheads, pois, and trail ends
public class Activity_ViewTrail_Map extends MapActivity {

	MapView mapView;
	List<Overlay> mapOverlays;
	//different colored markers for each category
	Drawable drawTrailhead;
	Drawable drawPoi;
	Drawable drawTrailend;
	//different overlays for each category
	MapMarkerOverlay overlayTrailhead;
	MapMarkerOverlay overlayPoi;
	MapMarkerOverlay overlayTrailend;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//confirm network is available before attempting to build map
		if (Utils.isNetworkAvailable(this)) {
			setContentView(R.layout.viewtrail_map);
			createMap();
			createMarkers();
		} else {
			setContentView(R.layout.viewtrail_nodata);
		}

	}

	//build the google map
	private void createMap() {
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);

		if (Object_TrailArticle.sMapType.compareTo("satellite") == 0)
			mapView.setSatellite(true);
		else
			mapView.setSatellite(false);

		// position the google map
		double mapLat = Object_TrailArticle.dCenterLat;
		double mapLong = Object_TrailArticle.dCenterLng;
		int mapZoom = Object_TrailArticle.nCenterZoom;

		// zoom out 1 to show all what you would see on the website
		// most screens are smaller than a normal monitor so err on the safe side
		if (mapZoom > 1)
			mapZoom--;

		//convert the lat/long
		GeoPoint p = new GeoPoint((int) (mapLat * 1E6), (int) (mapLong * 1E6));

		//now zoom to correct coords
		MapController mc = mapView.getController();
		mc.setCenter(p);
		mc.setZoom(mapZoom);
	}

	//function to create map markers for trailheads, pois, and trail ends
	private void createMarkers() {
		//check if there are markers to display
		if (Object_TrailArticle.mMarkerTypes == null
				|| Object_TrailArticle.mMarkerTypes.length <= 0)
			return;

		// sanity check
		if ((Object_TrailArticle.mMarkerTypes.length != Object_TrailArticle.mMarkerDescs.length)
				|| Object_TrailArticle.mMarkerTypes.length != Object_TrailArticle.mMarkerLats.length
				|| Object_TrailArticle.mMarkerTypes.length != Object_TrailArticle.mMarkerLngs.length)
			return;

		//assign drawables (marker icons)
		mapOverlays = mapView.getOverlays();
		drawTrailhead = getResources().getDrawable(R.drawable.marker_trailhead);
		drawPoi = getResources().getDrawable(R.drawable.marker_poi);
		drawTrailend = getResources().getDrawable(R.drawable.marker_trailend);

		int nMaxLat = 0, nMinLat = 0, nMaxLng = 0, nMinLng = 0;

		//iterate through the markers
		for (int i = 0; i < Object_TrailArticle.mMarkerTypes.length; i++) {
			String sType = Object_TrailArticle.mMarkerTypes[i];
			if (sType == null)
				break;

			//change the name from poi to human understandable
			if (sType.compareTo("Poi") == 0) {
				sType = "Point Of Interest";
			}

			//get the lat/long
			int nLat = (int) (Object_TrailArticle.mMarkerLats[i] * 1E6);
			int nLng = (int) (Object_TrailArticle.mMarkerLngs[i] * 1E6);

			//keep track of the max/min lat/long for resizing of the map later
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

			//create the marker
			GeoPoint point = new GeoPoint(nLat, nLng);
			OverlayItem overlayItem = new OverlayItem(point, sType,
					Object_TrailArticle.mMarkerDescs[i]);

			//add the new marker to the correct overlay category
			if (sType.compareTo("Trailhead") == 0) {
				if (overlayTrailhead == null)
					overlayTrailhead = new MapMarkerOverlay(drawTrailhead,
							mapView);

				// add the overlay
				overlayTrailhead.addOverlay(overlayItem);
			}

			if (sType.compareTo("Point Of Interest") == 0) {
				if (overlayPoi == null)
					overlayPoi = new MapMarkerOverlay(drawPoi, mapView);

				// add the overlay
				overlayPoi.addOverlay(overlayItem);
			}

			if (sType.compareTo("Trailend") == 0) {
				if (overlayTrailend == null)
					overlayTrailend = new MapMarkerOverlay(drawTrailend,
							mapView);

				// add to the overlay
				overlayTrailend.addOverlay(overlayItem);
			}
		}

		// now add the overlays to the map
		if (overlayTrailhead != null)
			mapOverlays.add(overlayTrailhead);
		if (overlayPoi != null)
			mapOverlays.add(overlayPoi);
		if (overlayTrailend != null)
			mapOverlays.add(overlayTrailend);

		// position the map to show the markers
		if (nMaxLat != nMinLat) {
			MapController mc = mapView.getController();
			mc.zoomToSpan(Math.abs(nMaxLat - nMinLat),
					Math.abs(nMaxLng - nMinLng));
			mc.animateTo(new GeoPoint((nMaxLat + nMinLat) / 2,
					(nMaxLng + nMinLng) / 2));
		}

	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}
