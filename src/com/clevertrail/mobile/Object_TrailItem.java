package com.clevertrail.mobile;

import org.json.JSONException;
import org.json.JSONObject;

import android.widget.ImageView;
import android.widget.LinearLayout;

public class Object_TrailItem {
	
	public static Object_TrailItem createFromJSON(JSONObject json){
		if (json == null)
			return null;
		
		Object_TrailItem trail = new Object_TrailItem();
		try {
			trail.sName = json.getString("name");
			trail.sDifficulty = json.getString("difficulty");
			trail.sDistance = json.getString("distance");
			trail.sNearestCity = json.getString("nearestcity");
			trail.sThumbnail = json.getString("image");
			trail.sTimeRequired = json.getString("time");
			trail.sTrailType = json.getString("trailtype");
			
			//in this order: hike, bicycle, handicap, swim, climb, horse, camp, dog, fish, family
			trail.mTrailUse[0] = (json.getString("hike").compareTo("1") == 0);
			trail.mTrailUse[1] = (json.getString("bicycle").compareTo("1") == 0);
			trail.mTrailUse[2] = (json.getString("handicap").compareTo("1") == 0);
			trail.mTrailUse[3] = (json.getString("swim").compareTo("1") == 0);
			trail.mTrailUse[4] = (json.getString("climb").compareTo("1") == 0);
			trail.mTrailUse[5] = (json.getString("horse").compareTo("1") == 0);
			trail.mTrailUse[6] = (json.getString("camp").compareTo("1") == 0);
			trail.mTrailUse[7] = (json.getString("dog").compareTo("1") == 0);
			trail.mTrailUse[8] = (json.getString("fish").compareTo("1") == 0);
			trail.mTrailUse[9] = (json.getString("family").compareTo("1") == 0);
			
			//map data
			trail.mTrailheadLat = json.getDouble("lat");
			trail.mTrailheadLng = json.getDouble("lng");			
		} catch (JSONException e) {
			
		}		
		
		return trail;
	}
	
	public Object_TrailItem(){		
	}
	
	public class TrailUse {
		public TrailUse(){			
		}
		
		boolean bHike = false;
		boolean bBicycle = false;
		boolean bHandicap = false;
		boolean bSwim = false;
		boolean bClimb = false;
		boolean bHorse = false;
		boolean bCamp = false;
		boolean bDog = false;
		boolean bFish = false;
		boolean bFamily = false;
	}
	

	
	public String sName;
	public String sDistance;
	public String sDifficulty;
	public String sTimeRequired;
	public String sNearestCity;
	public String sTrailType;
	public String sThumbnail;
	
	public double mTrailheadLat;
	public double mTrailheadLng;
	
	//in this order: hike, bicycle, handicap, swim, climb, horse, camp, dog, fish, family
	public boolean mTrailUse[] = new boolean[10];
}
