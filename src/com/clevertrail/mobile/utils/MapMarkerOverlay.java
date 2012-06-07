package com.clevertrail.mobile.utils;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.readystatesoftware.mapviewballoons.BalloonItemizedOverlay;

public class MapMarkerOverlay extends BalloonItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem> m_overlays = new ArrayList<OverlayItem>();
	private Activity mActivity;
	
	public MapMarkerOverlay(Drawable defaultMarker, MapView mapView, Activity activity) {
		super(boundCenter(defaultMarker), mapView);
		mActivity = activity;
	}

	public void addOverlay(OverlayItem overlay) {
	    m_overlays.add(overlay);
	    populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return m_overlays.get(i);
	}

	@Override
	public int size() {
		return m_overlays.size();
	}

	@Override
	//launch google maps for directions if user taps marker
	protected boolean onBalloonTap(int index, OverlayItem item) {
		GeoPoint p = item.getPoint();
		double lat = (double) (p.getLatitudeE6() / 1E6);
		double lng = (double) (p.getLongitudeE6() / 1E6);		
		
		//use the google maps intent
		Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
				Uri.parse("http://maps.google.com/maps?daddr=" + lat + "," + lng));
				mActivity.startActivity(intent);
		return true;
	}
	
}
