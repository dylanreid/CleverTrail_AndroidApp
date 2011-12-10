package com.clevertrail.mobile.viewtrail;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.clevertrail.mobile.Database_SavedTrails;
import com.clevertrail.mobile.R;
import com.clevertrail.mobile.imageutils.FileCache;
import com.clevertrail.mobile.imageutils.ImageLoader;

public class Activity_ViewTrail_Save extends Activity {

	private Database_SavedTrails db;
	private boolean mSaved = false;
	private ExecutorService executorService;
	private ProgressDialog mDialog;
	public static Activity_ViewTrail_Save mViewTrailSaveActivity;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//open/create the database
		db = new Database_SavedTrails(this);

		//used in the pdhandler
		mViewTrailSaveActivity = this; 

		setContentView(R.layout.viewtrail_save);

		// register events
		Button btnSaveTrail = (Button) findViewById(R.id.btnSaveTrail);
		btnSaveTrail.setOnClickListener(onclickSaveTrailButton);

		// register an executor service
		executorService = Executors.newFixedThreadPool(5);

	}

	public void onResume() {
		super.onResume();

		String jsonString = "";
		if (db != null) {
			db.openToRead();
			jsonString = db.getJSONString(Object_TrailArticle.sName);
			db.close();
		}

		mSaved = (jsonString != "");

		// draw the correct state
		updateView();
	}

	public void updateView() {
		TextView tvCurrentStatus = (TextView) findViewById(R.id.txtSaveStatus);
		Button btnSaveTrail = (Button) findViewById(R.id.btnSaveTrail);
		if (mSaved) {
			tvCurrentStatus.setTextColor(Color.GREEN);
			tvCurrentStatus.setText("Saved");
			btnSaveTrail.setText("Un-Save This Trail");
		} else {
			tvCurrentStatus.setTextColor(Color.RED);
			tvCurrentStatus.setText("Unsaved");
			btnSaveTrail.setText("Save This Trail");
		}
	}
	
	private void deleteTrail() {
		//delete trail
		db.openToWrite();
		db.remove(Object_TrailArticle.sName);
		db.close();
		FileCache fc = new FileCache(this.getApplicationContext());
		
		//delete files
		ArrayList<Object_TrailPhoto> photos = Object_TrailArticle.arReferencedPhotos;
		for (int i = 0; i < photos.size(); i++) {
			Object_TrailPhoto photo = photos.get(i);
			File f = fc.getFile(photo.mURL);
			f.delete();
		}
		
		mSaved = false;
		updateView();
	}

	private OnClickListener onclickSaveTrailButton = new OnClickListener() {
		public void onClick(View v) {
			if (Object_TrailArticle.sName != "") {
				Button btnSaveTrail = (Button) findViewById(R.id.btnSaveTrail);

				if (btnSaveTrail.getText() == "Un-Save This Trail") {
					deleteTrail();
				} else {
					mDialog = new ProgressDialog(v.getContext());
					mDialog.setMessage("Saving Trail");
					mDialog.setIndeterminate(false);
					mDialog.setCancelable(true);
					mDialog.setButton("Cancel", new DialogInterface.OnClickListener() 
				    {
				        public void onClick(DialogInterface dialog, int which) 
				        {
				            // Use either finish() or return() to either close the activity or just the dialog
				        	deleteTrail();
				        	mDialog.dismiss();
				        	return;
				        }
				    });
					mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
					mDialog.show();
					
					executorService.submit(new SaveTrail(v.getContext()));
				}
			}
		}
	};

	class SaveTrail implements Runnable {

		Context mContext;

		SaveTrail(Context c) {
			mContext = c;
		}

		@Override
		public void run() {

			if (Object_TrailArticle.jsonSaved != null) {
				mViewTrailSaveActivity.mSaved = true;
				db.openToWrite();
				db.insert(Object_TrailArticle.sName,
						Object_TrailArticle.jsonSaved.toString());
				db.close();

				boolean bSavePhotos = false;
				CheckBox cbSavePhotos = (CheckBox) findViewById(R.id.chkSavePhotos);
				bSavePhotos = cbSavePhotos.isChecked();
				if (bSavePhotos) {
					ImageLoader imageLoader = new ImageLoader(mContext);
					ArrayList<Object_TrailPhoto> photos = Object_TrailArticle.arReferencedPhotos;

					for (int i = 0; i < photos.size(); i++) {
						int incrementSize = 100 / photos.size();
						pdHandler.sendEmptyMessage(incrementSize);

						Object_TrailPhoto photo = photos.get(i);
						
						//in case the user has clicked cancel
						if (mViewTrailSaveActivity.mSaved == false)
							return;
						
						//using imageLoader will cache the image on the file system						
						imageLoader.getBitmapFromFile(photo.mURL);		
						imageLoader.getBitmapFromFile(photo.mURL120px);
					}
				}
			}

			pdHandler.sendEmptyMessage(0);
		}
	}

	final Handler pdHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				if (mViewTrailSaveActivity != null) {					
					mViewTrailSaveActivity.updateView();
				}
				mDialog.dismiss();
			} else {
				mDialog.incrementProgressBy(msg.what);
			}
		}
	};

}
