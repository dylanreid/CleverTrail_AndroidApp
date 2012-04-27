package com.clevertrail.mobile.viewtrail;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.clevertrail.mobile.R;
import com.clevertrail.mobile.utils.MapMarkerOverlay;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class Activity_ViewTrail_Map extends MapActivity {

	MapView mapView;
	List<Overlay> mapOverlays;
	Drawable drawTrailhead;
	Drawable drawPoi;
	Drawable drawTrailend;
	MapMarkerOverlay overlayTrailhead;
	MapMarkerOverlay overlayPoi;
	MapMarkerOverlay overlayTrailend;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.viewtrail_map);

		createMap();

		createMarkers();

	}

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
		if (mapZoom > 1)
			mapZoom--;

		GeoPoint p = new GeoPoint((int) (mapLat * 1E6), (int) (mapLong * 1E6));

		MapController mc = mapView.getController();
		mc.setCenter(p);
		mc.setZoom(mapZoom);
	}

	private void createMarkers() {
		if (Object_TrailArticle.mMarkerTypes == null
				|| Object_TrailArticle.mMarkerTypes.length <= 0)
			return;

		// sanity check
		if ((Object_TrailArticle.mMarkerTypes.length != Object_TrailArticle.mMarkerDescs.length)
				|| Object_TrailArticle.mMarkerTypes.length != Object_TrailArticle.mMarkerLats.length
				|| Object_TrailArticle.mMarkerTypes.length != Object_TrailArticle.mMarkerLngs.length)
			return;

		mapOverlays = mapView.getOverlays();
		drawTrailhead = getResources().getDrawable(R.drawable.marker_trailhead);
		drawPoi = getResources().getDrawable(R.drawable.marker_poi);
		drawTrailend = getResources().getDrawable(R.drawable.marker_trailend);

		int nMaxLat = 0, nMinLat = 0, nMaxLng = 0, nMinLng = 0;

		for (int i = 0; i < Object_TrailArticle.mMarkerTypes.length; i++) {
			String sType = Object_TrailArticle.mMarkerTypes[i];
			if (sType == null)
				break;
			
			if (sType.compareTo("Poi") == 0) {
				sType = "Point Of Interest";
			}

			int nLat = (int) (Object_TrailArticle.mMarkerLats[i] * 1E6);
			int nLng = (int) (Object_TrailArticle.mMarkerLngs[i] * 1E6);

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
			OverlayItem overlayItem = new OverlayItem(point, sType,
					Object_TrailArticle.mMarkerDescs[i]);

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
