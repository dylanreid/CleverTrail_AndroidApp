package com.clevertrail.mobile.viewtrail;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;

import com.clevertrail.mobile.Database_SavedTrails;
import com.clevertrail.mobile.utils.Utils;

public class Object_TrailArticle {

	public static void loadTrailArticle(Activity activity, String sTrailName) {
		mActivity = activity;
		sName = sTrailName;
		mPD = ProgressDialog.show(activity, "",
				"Loading " + sTrailName + " ...", true);

		new Thread(new Runnable() {
			public void run() {
				int status = Object_TrailArticle.loadTrailArticle_helper(mActivity, sName);
				mPD.dismiss();
				if (status > 0)
					Utils.showToastMessage(mActivity,
							"Error Contacting http://clevertrail.com");

			}
		}).start();
	}

	// 0: success
	// 1: param error
	// 2: connection error
	// 3: response error
	private static int loadTrailArticle_helper(Activity activity,
			String sTrailName) {

		String sResponseLine = "";
		if (sTrailName == null || sTrailName == "")
			return 1;

		HttpURLConnection urlConnection = null;
		try {
			String requestURL = String
					.format("http://clevertrail.com/ajax/handleGetArticleJSONByName.php?name=%s",
							Uri.encode(sTrailName));

			URL url = new URL(requestURL);
			urlConnection = (HttpURLConnection) url.openConnection();

			InputStream in = new BufferedInputStream(
					urlConnection.getInputStream());

			BufferedReader r = new BufferedReader(new InputStreamReader(in));

			sResponseLine = r.readLine();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 2;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// could not connect to clevertrail.com
			e.printStackTrace();
			return 2;
		} finally {
			if (urlConnection != null)
				urlConnection.disconnect();
		}

		JSONObject json = null;
		try {
			if (sResponseLine != "")
				json = new JSONObject(sResponseLine);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 3;
		}

		Object_TrailArticle.bSaved = false;

		// if json is null, we might not have internet activity so check saved
		// files
		if (json == null) {
			Database_SavedTrails db = new Database_SavedTrails(activity);
			db.openToRead();
			String jsonString = db.getJSONString(sTrailName);
			try {
				json = new JSONObject(jsonString);

				// if we found the json object in the db, mark it as saved
				if (json != null) {
					Object_TrailArticle.bSaved = true;
				}

			} catch (JSONException e) {
				return 3;
			}
			db.close();
		}

		if (json != null) {
			Object_TrailArticle.createFromJSON(sTrailName, json);
			Object_TrailArticle.jsonText = sResponseLine;
			
			Intent i = new Intent(mActivity,
					Activity_ViewTrail.class);			
			mActivity.startActivity(i);
			
			return 0;
		}
		return 3;
	}

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
				if (jsonMapString.compareTo("") != 0) {
					JSONObject jsonMapData = new JSONObject(jsonMapString);

					if (jsonMapData != null) {
						Object_TrailArticle.dCenterLat = jsonMapData
								.getDouble("centerLat");
						Object_TrailArticle.dCenterLng = jsonMapData
								.getDouble("centerLong");
						Object_TrailArticle.nCenterZoom = jsonMapData
								.getInt("zoom");
						Object_TrailArticle.sMapType = jsonMapData
								.getString("mapType");

						JSONArray arMarkers = jsonMapData
								.getJSONArray("markerLats");
						for (int i = 0; i < arMarkers.length(); i++) {
							Object_TrailArticle.mMarkerLats[i] = arMarkers
									.getDouble(i);
						}
						arMarkers = jsonMapData.getJSONArray("markerLongs");
						for (int i = 0; i < arMarkers.length(); i++) {
							Object_TrailArticle.mMarkerLngs[i] = arMarkers
									.getDouble(i);
						}
						arMarkers = jsonMapData.getJSONArray("markerDescs");
						for (int i = 0; i < arMarkers.length(); i++) {
							Object_TrailArticle.mMarkerDescs[i] = arMarkers
									.getString(i);
						}
						arMarkers = jsonMapData.getJSONArray("markerTypes");
						for (int i = 0; i < arMarkers.length(); i++) {
							Object_TrailArticle.mMarkerTypes[i] = arMarkers
									.getString(i);
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

	public static void clearData() {
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
		// in this order: hike, bicycle, handicap, swim, climb, horse, camp,
		// dog,
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

		// map data
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

	// map data
	public static double dCenterLat;
	public static double dCenterLng;
	public static int nCenterZoom;
	public static String sMapType;
	public static double mMarkerLats[] = new double[50];
	public static double mMarkerLngs[] = new double[50];
	public static String mMarkerDescs[] = new String[50];
	public static String mMarkerTypes[] = new String[50];

	public static ArrayList<Object_TrailPhoto> arPhotos;

	// progress dialog
	public static ProgressDialog mPD;
	private static Activity mActivity;
}
