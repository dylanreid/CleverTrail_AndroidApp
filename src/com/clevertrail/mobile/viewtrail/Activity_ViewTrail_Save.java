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

package com.clevertrail.mobile.viewtrail;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.clevertrail.mobile.Activity_ListTrails;
import com.clevertrail.mobile.Database_SavedTrails;
import com.clevertrail.mobile.R;
import com.clevertrail.mobile.utils.FileCache;
import com.clevertrail.mobile.utils.ImageLoader;
import com.clevertrail.mobile.utils.Utils;

//activity to save a trail to the internal memory of the device
public class Activity_ViewTrail_Save extends Activity {

	private Database_SavedTrails db;
	private boolean mSaved = false;
	private ExecutorService executorService;
	private ProgressDialog mSavingDialog;
	private Activity_ViewTrail_Save mActivity;
	public ProgressDialog mLoadingDialog;
	public static Activity_ViewTrail_Save mViewTrailSaveActivity;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity = this;

		// open/create the database
		db = new Database_SavedTrails(this);

		// used in the pdhandler
		mViewTrailSaveActivity = this;

		setContentView(R.layout.viewtrail_save);

		// register events
		Button btnSaveTrail = (Button) findViewById(R.id.btnSaveTrail);
		btnSaveTrail.setOnClickListener(onclickSaveTrailButton);

		Button btnGotoSavedTrails = (Button) findViewById(R.id.btnSaveGoToSavedTrails);
		btnGotoSavedTrails.setOnClickListener(onclickGotoSavedTrails);

		// register an executor service
		executorService = Executors.newFixedThreadPool(5);

	}

	public void onResume() {
		super.onResume();

		//get the json information from the database for the trail
		String jsonString = "";
		if (db != null) {
			db.openToRead();
			jsonString = db.getJSONString(Object_TrailArticle.sName);
			db.close();
		}

		//determine if the trail is already saved or not
		mSaved = (jsonString.compareTo("") != 0);

		// draw the correct state
		updateView();
	}

	public void updateView() {
		TextView tvCurrentStatus = (TextView) findViewById(R.id.txtSaveStatus);
		Button btnSaveTrail = (Button) findViewById(R.id.btnSaveTrail);
		//if already saved in the database, give the option to un-save and vice versa
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

	//function to delete a trail from the database
	private void deleteTrail() {
		// delete trail
		db.openToWrite();
		db.remove(Object_TrailArticle.sName);
		db.close();
		FileCache fc = new FileCache(this.getApplicationContext());

		// delete all the photos for the trail as well
		ArrayList<Object_TrailPhoto> photos = Object_TrailArticle.arPhotos;
		for (int i = 0; i < photos.size(); i++) {
			Object_TrailPhoto photo = photos.get(i);
			File f = fc.getFile(photo.mURL);
			f.delete();
		}

		mSaved = false;
		updateView();
	}

	//event to jump to the saved trails screen
	private OnClickListener onclickGotoSavedTrails = new OnClickListener() {
		public void onClick(View v) {

			//display a progress dialog
			mActivity.mLoadingDialog = ProgressDialog.show(mActivity, "",
					getString(R.string.progress_loadingsavedtrails), true);

			new Thread(new Runnable() {
				public void run() {
					int error = Database_SavedTrails.openSavedTrails(mActivity);
					mLoadingDialog.dismiss();
					//display an error message if one exists
					Utils.showMessage(mActivity, error);
					return;
				}
			}).start();

		}
	};

	//event for clicking the save button
	private OnClickListener onclickSaveTrailButton = new OnClickListener() {
		public void onClick(View v) {
			if (Object_TrailArticle.sName != "") {
				Button btnSaveTrail = (Button) findViewById(R.id.btnSaveTrail);

				//are we deleting or saving?
				if (btnSaveTrail.getText() == "Un-Save This Trail") {
					deleteTrail();
				} else {
					//display a progress dialog while we do the save
					mSavingDialog = new ProgressDialog(v.getContext());
					mSavingDialog.setMessage(getString(R.string.progress_savingtrail));
					mSavingDialog.setIndeterminate(false);
					mSavingDialog.setCancelable(true);
					mSavingDialog.setButton(getString(R.string.progress_cancel),
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									// Use either finish() or return() to either
									// close the activity or just the dialog
									deleteTrail();
									mSavingDialog.dismiss();
									return;
								}
							});
					mSavingDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
					mSavingDialog.show();

					executorService.submit(new SaveTrail(v.getContext()));
				}
			}
		}
	};

	// a helper class to do the actual save as a separate thread
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
				//save the json for the given trail
				db.insert(Object_TrailArticle.sName,
						Object_TrailArticle.jsonText);
				db.close();

				boolean bSavePhotos = false;
				CheckBox cbSavePhotos = (CheckBox) findViewById(R.id.chkSavePhotos);
				bSavePhotos = cbSavePhotos.isChecked();
				//are we also saving the photos?
				if (bSavePhotos) {
					ImageLoader imageLoader = new ImageLoader(mContext);
					ArrayList<Object_TrailPhoto> photos = Object_TrailArticle.arPhotos;

					//go through the photos and save them to internal memory
					for (int i = 0; i < photos.size(); i++) {
						int incrementSize = 100 / photos.size();
						//increment the progress dialog
						pdHandler.sendEmptyMessage(incrementSize);

						Object_TrailPhoto photo = photos.get(i);

						// in case the user has clicked cancel
						if (mViewTrailSaveActivity.mSaved == false)
							return;

						// using imageLoader will cache the image on the file
						// system
						imageLoader.getBitmapFromFile(photo.mURL);
						imageLoader.getBitmapFromFile(photo.mURL120px);
					}
				}
			}

			//finish the progress dialog
			pdHandler.sendEmptyMessage(0);
		}
	}

	// a handler for a progress dialog while we save
	final Handler pdHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				if (mViewTrailSaveActivity != null) {
					mViewTrailSaveActivity.updateView();
				}
				mSavingDialog.dismiss();
			} else {
				mSavingDialog.incrementProgressBy(msg.what);
			}
		}
	};

}
