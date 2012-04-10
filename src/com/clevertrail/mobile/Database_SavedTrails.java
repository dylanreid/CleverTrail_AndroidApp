package com.clevertrail.mobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class Database_SavedTrails {

	public static final String MYDATABASE_NAME = "dbCleverTrail";
	public static final String MYDATABASE_TABLE = "tblSavedTrails";
	public static final int MYDATABASE_VERSION = 1;
	public static final String KEY_CONTENT = "Content";

	final String CREATE_TABLE_SAVEDTRAILS = "CREATE TABLE " + MYDATABASE_TABLE + " ("
		+ "name TEXT PRIMARY KEY COLLATE NOCASE, "
		+ "json TEXT);";
	

	private SQLiteHelper sqLiteHelper;
	private SQLiteDatabase sqLiteDatabase;

	private Context context;

	public Database_SavedTrails(Context c) {
		context = c;
	}

	public Database_SavedTrails openToRead() throws android.database.SQLException {
		sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null,
				MYDATABASE_VERSION);
		sqLiteDatabase = sqLiteHelper.getReadableDatabase();
		return this;
	}

	public Database_SavedTrails openToWrite() throws android.database.SQLException {
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
	
	//search for all saved trails
	public String getJSONString() {
		String[] columns = new String[] { "json" };
		Cursor cursor = sqLiteDatabase.query(MYDATABASE_TABLE, columns, null, null, null, null, null);
		
		cursor.moveToFirst();
		int index_JSON = cursor.getColumnIndex("json");
		
		String sReturn = "[";
		while (!cursor.isAfterLast()){
			sReturn = sReturn.concat(cursor.getString(index_JSON));
			cursor.moveToNext();
			if (!cursor.isAfterLast())
				sReturn = sReturn.concat(",");
			else
				break;
		}
		sReturn = sReturn.concat("]");
				
		return sReturn;
	}
	
	//find a specific trail by name
	public String getJSONString(String sName) {
		String[] columns = new String[] { "json" };
		String selection = "name = '" + sName + "'";
		Cursor cursor = sqLiteDatabase.query(MYDATABASE_TABLE, columns, selection, null, null, null, null);
		
		cursor.moveToFirst();
		int index_JSON = cursor.getColumnIndex("json");
		
		String sReturn = "";
		if (!cursor.isAfterLast())
			sReturn = cursor.getString(index_JSON);
		
		return sReturn;
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