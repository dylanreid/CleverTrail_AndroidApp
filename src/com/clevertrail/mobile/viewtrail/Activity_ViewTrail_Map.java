package com.clevertrail.mobile.viewtrail;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.clevertrail.mobile.HelloItemizedOverlay;
import com.clevertrail.mobile.R;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class Activity_ViewTrail_Map extends MapActivity {
	
	LinearLayout linearLayout;
	MapView mapView;
	List<Overlay> mapOverlays;
	Drawable drawable;
	HelloItemizedOverlay itemizedOverlay;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.viewtrail_map);
        
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        
        mapOverlays = mapView.getOverlays();
        drawable = this.getResources().getDrawable(R.drawable.hikingmarker);
        itemizedOverlay = new HelloItemizedOverlay(drawable);
        
        GeoPoint point = new GeoPoint(19240000,-99120000);
        OverlayItem overlayitem = new OverlayItem(point, "", "");
        
        itemizedOverlay.addOverlay(overlayitem);
        mapOverlays.add(itemizedOverlay);
    }
	
	@Override
	protected boolean isRouteDisplayed() {
	    return false;
	}
}
