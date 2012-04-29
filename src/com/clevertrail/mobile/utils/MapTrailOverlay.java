package com.clevertrail.mobile.utils;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;

import com.clevertrail.mobile.viewtrail.Activity_ViewTrail;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.readystatesoftware.mapviewballoons.BalloonItemizedOverlay;

public class MapTrailOverlay extends BalloonItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem> m_overlays = new ArrayList<OverlayItem>();
	private Activity mActivity;
	
	public MapTrailOverlay(Drawable defaultMarker, MapView mapView, Activity activity) {
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
	protected boolean onBalloonTap(int index, OverlayItem item) {
		if (mActivity != null) {
			Intent i = new Intent(mActivity,
					Activity_ViewTrail.class);
			i.putExtra("name", item.getTitle());
			mActivity.startActivity(i);
		}
		return true;
	}
	
}