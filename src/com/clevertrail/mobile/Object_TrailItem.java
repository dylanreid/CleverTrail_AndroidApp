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

package com.clevertrail.mobile;

import org.json.JSONException;
import org.json.JSONObject;

import android.widget.ImageView;
import android.widget.LinearLayout;

//a reusable wrapper class for a trail item
public class Object_TrailItem {
	
	//take a json object and parse out trail information from it
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
			//todo: should have a better way to maintain the trail uses rather than "remember this order"
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
	
	//trail use wrapper class
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
