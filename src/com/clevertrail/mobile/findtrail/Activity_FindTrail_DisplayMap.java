package com.clevertrail.mobile.findtrail;

import java.util.List;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.clevertrail.mobile.HelloItemizedOverlay;
import com.clevertrail.mobile.R;
import com.clevertrail.mobile.utils.TitleBar;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class Activity_FindTrail_DisplayMap extends Activity {
	
	public Activity mActivity = null;
	MapView mapView;
	List<Overlay> mapOverlays;
	Drawable drawable;
	HelloItemizedOverlay itemizedOverlay;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		TitleBar.setCustomTitleBar(this, R.layout.findtrail_displaymap, "CleverTrail", R.drawable.ic_viewtrailtab_map_unselected);
		
		mActivity = this;
		
		createMap();
	}
	
	private void createMap(){
		mapView = (MapView) findViewById(R.id.mvFindTrail);
        mapView.setBuiltInZoomControls(true);
        
        mapOverlays = mapView.getOverlays();
        drawable = this.getResources().getDrawable(R.drawable.hikingmarker);
        itemizedOverlay = new HelloItemizedOverlay(drawable);
        
        GeoPoint point = new GeoPoint(19240000,-99120000);
        OverlayItem overlayitem = new OverlayItem(point, "", "");
        
        itemizedOverlay.addOverlay(overlayitem);
        mapOverlays.add(itemizedOverlay);
        
        //position at a certain lat/long and zoom level
      /*  double mapLat = 0;
        double mapLong = 0;
        GeoPoint p = new GeoPoint(
                (int) (mapLat * 1E6), 
                (int) (mapLong * 1E6));
        
        MapController mc = mapView.getController();
        mc.setCenter(p);
        mc.setZoom(17);*/
	}
	
}
