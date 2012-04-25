package com.clevertrail.mobile.viewtrail;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.clevertrail.mobile.HelloItemizedOverlay;
import com.clevertrail.mobile.R;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
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
     /*   
        mapOverlays = mapView.getOverlays();
        drawable = this.getResources().getDrawable(R.drawable.hikingmarker);
        itemizedOverlay = new HelloItemizedOverlay(drawable);
        
        GeoPoint point = new GeoPoint(19240000,-99120000);
        OverlayItem overlayitem = new OverlayItem(point, "", "");
        
        itemizedOverlay.addOverlay(overlayitem);
        mapOverlays.add(itemizedOverlay);
        
        //position at a certain lat/long and zoom level
        double mapLat = 0;
        double mapLong = 0;
        GeoPoint p = new GeoPoint(
                (int) (mapLat * 1E6), 
                (int) (mapLong * 1E6));
        
        MapController mc = mapView.getController();
        mc.setCenter(p);
        mc.setZoom(17);*/
    }
	
	@Override
	protected boolean isRouteDisplayed() {
	    return false;
	}
}
