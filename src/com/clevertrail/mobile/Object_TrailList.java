package com.clevertrail.mobile;

import java.util.ArrayList;

import org.json.JSONObject;

public class Object_TrailList {
	
	public static void addTrailWithJSON(JSONObject json){
		if (arTrails == null)
			arTrails = new ArrayList();
		
		arTrails.add(Object_TrailItem.createFromJSON(json));		
	}
	
	public static void clearTrails(){
		if (arTrails != null)
			arTrails.clear();
	}
	
	public static ArrayList<Object_TrailItem> arTrails;
}
