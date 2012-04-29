package com.clevertrail.mobile.viewtrail;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Object_TrailArticle {

	public static void createFromJSON(String name, JSONObject json) {

		try {
			if (json != null) {
				Object_TrailArticle.clearData();
				
				jsonSaved = json;
				sName = name;

				// get the referenced photos
				arPhotos = new ArrayList();
				JSONArray arRP = json.getJSONArray("referencedphotos");
				for (int i = 0; i < arRP.length(); i++) {
					JSONArray arJSONPhoto = arRP.getJSONArray(i);
					if (arJSONPhoto.length() >= 2) {
						Object_TrailPhoto photo = new Object_TrailPhoto();
						photo.mURL = arJSONPhoto.getString(0);
						photo.mURL120px = arJSONPhoto.getString(1);
						photo.mCaption = Object_TrailArticle
								.urlDecode(arJSONPhoto.getString(2));
						photo.mSection = arJSONPhoto.getString(3);
						arPhotos.add(photo);
					}
				}

				// get the gallery photos
				arRP = json.getJSONArray("galleryphotos");
				for (int i = 0; i < arRP.length(); i++) {
					JSONArray arJSONPhoto = arRP.getJSONArray(i);
					if (arJSONPhoto.length() >= 2) {
						Object_TrailPhoto photo = new Object_TrailPhoto();
						photo.mURL = arJSONPhoto.getString(0);
						photo.mURL120px = arJSONPhoto.getString(1);
						photo.mCaption = "";
						photo.mSection = "";
						arPhotos.add(photo);
					}
				}

				Object_TrailArticle.sOverview = Object_TrailArticle
						.urlDecode(json.getString("overview"));
				Object_TrailArticle.sDirections = Object_TrailArticle
						.urlDecode(json.getString("directions"));
				Object_TrailArticle.sDescription = Object_TrailArticle
						.urlDecode(json.getString("description"));
				Object_TrailArticle.sConditions = Object_TrailArticle
						.urlDecode(json.getString("conditions"));
				Object_TrailArticle.sFees = Object_TrailArticle.urlDecode(json
						.getString("fees"));
				Object_TrailArticle.sAmenities = Object_TrailArticle
						.urlDecode(json.getString("amenities"));
				Object_TrailArticle.sMisc = Object_TrailArticle.urlDecode(json
						.getString("misc"));

				Object_TrailArticle.mImage = new Object_TrailPhoto();
				Object_TrailArticle.mImage.mURL = Object_TrailArticle
						.urlDecode(json.getString("image"));
				Object_TrailArticle.sImageCredit = Object_TrailArticle
						.urlDecode(json.getString("imagecredit"));
				Object_TrailArticle.sDifficulty = Object_TrailArticle
						.urlDecode(json.getString("difficulty"));
				Object_TrailArticle.sDistance = Object_TrailArticle
						.urlDecode(json.getString("distance"));
				Object_TrailArticle.sTime = Object_TrailArticle.urlDecode(json
						.getString("time"));

				Object_TrailArticle.sType = Object_TrailArticle.urlDecode(json
						.getString("trailtype"));
				Object_TrailArticle.sElevation = Object_TrailArticle
						.urlDecode(json.getString("elevation"));
				Object_TrailArticle.sHighPoint = Object_TrailArticle
						.urlDecode(json.getString("highpoint"));
				Object_TrailArticle.sLowPoint = Object_TrailArticle
						.urlDecode(json.getString("lowpoint"));
				Object_TrailArticle.sBestMonth = Object_TrailArticle
						.urlDecode(json.getString("bestmonth"));
				Object_TrailArticle.sNearestCity = Object_TrailArticle
						.urlDecode(json.getString("nearestcity"));

				// in this order: hike, bicycle, handicap, swim, climb, horse,
				// camp, dog,
				// fish, family
				Object_TrailArticle.mTrailUse[0] = (json.getString("hike")
						.compareTo("1") == 0);
				Object_TrailArticle.mTrailUse[1] = (json.getString("bicycle")
						.compareTo("1") == 0);
				Object_TrailArticle.mTrailUse[2] = (json.getString("handicap")
						.compareTo("1") == 0);
				Object_TrailArticle.mTrailUse[3] = (json.getString("swim")
						.compareTo("1") == 0);
				Object_TrailArticle.mTrailUse[4] = (json.getString("climb")
						.compareTo("1") == 0);
				Object_TrailArticle.mTrailUse[5] = (json.getString("horse")
						.compareTo("1") == 0);
				Object_TrailArticle.mTrailUse[6] = (json.getString("camp")
						.compareTo("1") == 0);
				Object_TrailArticle.mTrailUse[7] = (json.getString("dog")
						.compareTo("1") == 0);
				Object_TrailArticle.mTrailUse[8] = (json.getString("fish")
						.compareTo("1") == 0);
				Object_TrailArticle.mTrailUse[9] = (json.getString("family")
						.compareTo("1") == 0);
				
				String jsonMapString = json.getString("jsonMapdata");
				if (jsonMapString.compareTo("") != 0){
					JSONObject jsonMapData = new JSONObject(jsonMapString);
					
					if (jsonMapData != null) {
						Object_TrailArticle.dCenterLat = jsonMapData.getDouble("centerLat");
						Object_TrailArticle.dCenterLng = jsonMapData.getDouble("centerLong");
						Object_TrailArticle.nCenterZoom = jsonMapData.getInt("zoom");
						Object_TrailArticle.sMapType = jsonMapData.getString("mapType");												
						
						JSONArray arMarkers = jsonMapData.getJSONArray("markerLats");
						for (int i = 0; i < arMarkers.length(); i++) {
							Object_TrailArticle.mMarkerLats[i] = arMarkers.getDouble(i);
						}
						arMarkers = jsonMapData.getJSONArray("markerLongs");
						for (int i = 0; i < arMarkers.length(); i++) {
							Object_TrailArticle.mMarkerLngs[i] = arMarkers.getDouble(i);
						}
						arMarkers = jsonMapData.getJSONArray("markerDescs");
						for (int i = 0; i < arMarkers.length(); i++) {
							Object_TrailArticle.mMarkerDescs[i] = arMarkers.getString(i);
						}
						arMarkers = jsonMapData.getJSONArray("markerTypes");
						for (int i = 0; i < arMarkers.length(); i++) {
							Object_TrailArticle.mMarkerTypes[i] = arMarkers.getString(i);
						}
					}
				}

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String urlDecode(String sText) {
		sText = sText.replace("&quot;", "\"");
		sText = sText.replace("&amp;", "&");
		return sText;
	}
	
	public static void clearData(){
		Object_TrailArticle.sName = "";

		Object_TrailArticle.sOverview = "";
		Object_TrailArticle.sDirections = "";
		Object_TrailArticle.sDescription = "";
		Object_TrailArticle.sConditions = "";
		Object_TrailArticle.sFees = "";
		Object_TrailArticle.sAmenities = "";
		Object_TrailArticle.sMisc = "";

		Object_TrailArticle.mImage = null;
		Object_TrailArticle.sImageCredit = "";
		Object_TrailArticle.sDifficulty = "";
		Object_TrailArticle.sDistance = "";
		Object_TrailArticle.sTime = "";
		// in this order: hike, bicycle, handicap, swim, climb, horse, camp, dog,
		// fish, family
		Object_TrailArticle.mTrailUse = new boolean[10];
		Object_TrailArticle.sType = "";
		Object_TrailArticle.sElevation = "";
		Object_TrailArticle.sHighPoint = "";
		Object_TrailArticle.sLowPoint = "";
		Object_TrailArticle.sBestMonth = "";
		Object_TrailArticle.sNearestCity = "";

		Object_TrailArticle.bSaved = false;
		Object_TrailArticle.jsonSaved = null;
		Object_TrailArticle.jsonText = "";
		
		//map data
		Object_TrailArticle.dCenterLat = 0;
		Object_TrailArticle.dCenterLng = 0;
		Object_TrailArticle.nCenterZoom = 0;
		Object_TrailArticle.sMapType = "";
		Object_TrailArticle.mMarkerLats = new double[50];
		Object_TrailArticle.mMarkerLngs = new double[50];
		Object_TrailArticle.mMarkerDescs = new String[50];
		Object_TrailArticle.mMarkerTypes = new String[50];
		
	}

	public static String sName = "";

	public static String sOverview = "";
	public static String sDirections = "";
	public static String sDescription = "";
	public static String sConditions = "";
	public static String sFees = "";
	public static String sAmenities = "";
	public static String sMisc = "";

	public static Object_TrailPhoto mImage;
	public static String sImageCredit;
	public static String sDifficulty;
	public static String sDistance;
	public static String sTime;
	// in this order: hike, bicycle, handicap, swim, climb, horse, camp, dog,
	// fish, family
	public static boolean mTrailUse[] = new boolean[10];
	public static String sType;
	public static String sElevation;
	public static String sHighPoint;
	public static String sLowPoint;
	public static String sBestMonth;
	public static String sNearestCity;

	public static boolean bSaved = false;
	public static JSONObject jsonSaved = null;
	public static String jsonText = "";
	
	//map data
	public static double dCenterLat;
	public static double dCenterLng;
	public static int nCenterZoom;
	public static String sMapType;
	public static double mMarkerLats[] = new double[50];
	public static double mMarkerLngs[] = new double[50];
	public static String mMarkerDescs[] = new String[50];
	public static String mMarkerTypes[] = new String[50];

	public static ArrayList<Object_TrailPhoto> arPhotos;
}
