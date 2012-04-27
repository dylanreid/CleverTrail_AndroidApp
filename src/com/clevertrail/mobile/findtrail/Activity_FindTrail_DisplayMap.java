package com.clevertrail.mobile.findtrail;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.clevertrail.mobile.Object_TrailItem;
import com.clevertrail.mobile.Object_TrailList;
import com.clevertrail.mobile.R;
import com.clevertrail.mobile.utils.MapTrailOverlay;
import com.clevertrail.mobile.utils.TitleBar;
import com.clevertrail.mobile.viewtrail.Object_TrailArticle;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class Activity_FindTrail_DisplayMap extends MapActivity {

	MapView mapView;
	List<Overlay> mapOverlays;
	Drawable drawTrailhead;
	MapTrailOverlay overlayTrailhead;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
		TitleBar.setCustomTitleBar(this, R.layout.findtrail_displaymap,
				"CleverTrail", R.drawable.ic_viewtrailtab_map_unselected);

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
		if (Object_TrailList.arTrails == null
				|| Object_TrailList.arTrails.size() <= 0)
			return;

		mapOverlays = mapView.getOverlays();
		drawTrailhead = getResources().getDrawable(R.drawable.trailmarker);
		
		int nMaxLat = 0, nMinLat = 0, nMaxLng = 0, nMinLng = 0;

		for (int i = 0; i < Object_TrailList.arTrails.size(); i++) {
			Object_TrailItem trail = Object_TrailList.arTrails.get(i);
			
			int nLat = (int) (trail.mTrailheadLat * 1E6);
			int nLng = (int) (trail.mTrailheadLng * 1E6);

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

			String sDescription = "";
			if (trail.sDifficulty.compareTo("") != 0){
				sDescription = sDescription.concat("Difficulty: " + trail.sDifficulty + "\n");
			}
			if (trail.sDistance.compareTo("") != 0){
				sDescription = sDescription.concat("Distance: " + trail.sDistance + "\n");
			}
			if (trail.sTrailType.compareTo("") != 0){
				sDescription = sDescription.concat("Trail Type: " + trail.sTrailType + "\n");
			}

			OverlayItem overlayItem = new OverlayItem(point, trail.sName,
					sDescription);

			if (overlayTrailhead == null)
				overlayTrailhead = new MapTrailOverlay(drawTrailhead, mapView, this, trail.sName);

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
			mc.animateTo(new GeoPoint((nMaxLat + nMinLat) / 2,
					(nMaxLng + nMinLng) / 2));
		} else {
			mc.setZoom(10);
		}
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

}
