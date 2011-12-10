package com.clevertrail.mobile.viewtrail;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Object_TrailArticle {
	
	public static void createFromJSON(String name, JSONObject json) {
		
		try {
			if (json != null) {
				jsonSaved = json;
				sName = name;
				
				//get the referenced photos
				arPhotos = new ArrayList();
				JSONArray arRP = json.getJSONArray("referencedphotos");				
			    for(int i = 0 ; i < arRP.length(); i++){
			    	JSONArray arJSONPhoto = arRP.getJSONArray(i);
			    	if (arJSONPhoto.length() >= 2){
			    		Object_TrailPhoto photo = new Object_TrailPhoto();
			    		photo.mURL = arJSONPhoto.getString(0);
			    		photo.mURL120px = arJSONPhoto.getString(1);
			    		photo.mCaption = Object_TrailArticle.urlDecode(arJSONPhoto.getString(2));
			    		photo.mSection = arJSONPhoto.getString(3);
			    		arPhotos.add(photo);
			    	}
			    }
			    
			    //get the gallery photos
			    arRP = json.getJSONArray("galleryphotos");				
			    for(int i = 0 ; i < arRP.length(); i++){
			    	JSONArray arJSONPhoto = arRP.getJSONArray(i);
			    	if (arJSONPhoto.length() >= 2){
			    		Object_TrailPhoto photo = new Object_TrailPhoto();
			    		photo.mURL = arJSONPhoto.getString(0);
			    		photo.mURL120px = arJSONPhoto.getString(1);
			    		photo.mCaption = "";
			    		photo.mSection = "";
			    		arPhotos.add(photo);
			    	}
			    }
				
				Object_TrailArticle.sOverview = Object_TrailArticle.urlDecode(json.getString("overview"));
				Object_TrailArticle.sDirections = Object_TrailArticle.urlDecode(json.getString("directions"));
				Object_TrailArticle.sDescription = Object_TrailArticle.urlDecode(json.getString("description"));
				Object_TrailArticle.sConditions = Object_TrailArticle.urlDecode(json.getString("conditions")); 
				Object_TrailArticle.sFees = Object_TrailArticle.urlDecode(json.getString("fees"));
				Object_TrailArticle.sAmenities = Object_TrailArticle.urlDecode(json.getString("amenities"));
				Object_TrailArticle.sMisc = Object_TrailArticle.urlDecode(json.getString("misc"));				
			}
		} catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	
	public static String urlDecode(String sText){
		sText = sText.replace("&quot;", "\"");
		sText = sText.replace("&amp;", "&");
		return sText;
	}
	
	public static String sName = "";
	
	public static String sOverview = "";
	public static String sDirections = "";
	public static String sDescription = "";
	public static String sConditions = "";
	public static String sFees = "";
	public static String sAmenities = "";
	public static String sMisc = "";
	
	public static boolean bSaved = false;
	public static JSONObject jsonSaved = null;
	
	public static ArrayList<Object_TrailPhoto> arPhotos;
}
