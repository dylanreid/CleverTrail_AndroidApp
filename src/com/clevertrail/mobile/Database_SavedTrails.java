package com.clevertrail.mobile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class Database_SavedTrails {

	public static final String MYDATABASE_NAME = "dbCleverTrail";
	public static final String MYDATABASE_TABLE = "tblSavedTrails";
	public static final int MYDATABASE_VERSION = 1;
	public static final String KEY_CONTENT = "Content";

	final String CREATE_TABLE_SAVEDTRAILS = "CREATE TABLE " + MYDATABASE_TABLE
			+ " (" + "name TEXT PRIMARY KEY COLLATE NOCASE, " + "json TEXT);";

	private SQLiteHelper sqLiteHelper;
	private SQLiteDatabase sqLiteDatabase;

	private Context context;

	public Database_SavedTrails(Context c) {
		context = c;
	}

	public Database_SavedTrails openToRead()
			throws android.database.SQLException {
		sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null,
				MYDATABASE_VERSION);
		sqLiteDatabase = sqLiteHelper.getReadableDatabase();
		return this;
	}

	public Database_SavedTrails openToWrite()
			throws android.database.SQLException {
		sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null,
				MYDATABASE_VERSION);
		sqLiteDatabase = sqLiteHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		sqLiteHelper.close();
	}

	public long insert(String sName, String sJSON) {

		ContentValues contentValues = new ContentValues();
		contentValues.put("name", sName.replace("_", " "));
		contentValues.put("json", sJSON);
		return sqLiteDatabase.insert(MYDATABASE_TABLE, null, contentValues);
	}

	public void remove(String sName) {
		String whereClause = "name = '" + sName + "'";
		sqLiteDatabase.delete(MYDATABASE_TABLE, whereClause, null);
	}

	public int deleteAll() {
		return sqLiteDatabase.delete(MYDATABASE_TABLE, null, null);
	}

	public String queueAll() {
		String[] columns = new String[] { "name", "json" };
		Cursor cursor = sqLiteDatabase.query(MYDATABASE_TABLE, columns, null,
				null, null, null, null);
		String result = "";

		int index_NAME = cursor.getColumnIndex("name");
		for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
			result = result + cursor.getString(index_NAME) + "\n";
		}

		return result;
	}

	// search for all saved trails
	public String getJSONString() {
		String[] columns = new String[] { "json" };
		Cursor cursor = sqLiteDatabase.query(MYDATABASE_TABLE, columns, null,
				null, null, null, null);

		cursor.moveToFirst();
		int index_JSON = cursor.getColumnIndex("json");

		String sReturn = "[";
		while (!cursor.isAfterLast()) {
			String sTrailJSON = cursor.getString(index_JSON);
			sReturn = sReturn.concat(sTrailJSON);
			cursor.moveToNext();
			if (!cursor.isAfterLast()) {
				if (sTrailJSON.compareTo("") != 0)
					sReturn = sReturn.concat(",");
			} else {
				break;
			}
		}
		sReturn = sReturn.concat("]");

		return sReturn;
	}

	// find a specific trail by name
	public String getJSONString(String sName) {
		String[] columns = new String[] { "json" };
		String selection = "name = '" + sName + "'";
		Cursor cursor = sqLiteDatabase.query(MYDATABASE_TABLE, columns,
				selection, null, null, null, null);

		cursor.moveToFirst();
		int index_JSON = cursor.getColumnIndex("json");

		String sReturn = "";
		if (!cursor.isAfterLast())
			sReturn = cursor.getString(index_JSON);

		return sReturn;
	}

	// 0: successful
	// 1: database error
	// 2: json error
	public static int openSavedTrails(Activity activity) {
		JSONArray jsonArray = null;
		Database_SavedTrails db = new Database_SavedTrails(activity);
		db.openToRead();
		String jsonString = db.getJSONString();

		try {
			jsonArray = new JSONArray(jsonString);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}

		db.close();

		Object_TrailList.clearTrails();
		if (jsonArray != null && jsonArray.length() > 0) {
			int len = jsonArray.length();
			try {
				for (int i = 0; i < len && i < 20; ++i) {
					JSONObject trail = jsonArray.getJSONObject(i);
					Object_TrailList.addTrailWithJSON(trail);
				}
			} catch (JSONException e) {
				return 2;
			}
		}

		int icon = R.drawable.ic_viewtrailtab_save_unselected;
		String title = "CleverTrail - Saved Trails";
		Intent i = new Intent(activity, Activity_ListTrails.class);
		i.putExtra("icon", icon);
		i.putExtra("title", title);
		activity.startActivity(i);

		return 0;
	}

	public class SQLiteHelper extends SQLiteOpenHelper {

		public SQLiteHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(CREATE_TABLE_SAVEDTRAILS);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub

		}

	}

}