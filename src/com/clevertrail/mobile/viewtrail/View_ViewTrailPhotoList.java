package com.clevertrail.mobile.viewtrail;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class View_ViewTrailPhotoList extends ListView {

	public Activity mActivity = null;

	public View_ViewTrailPhotoList(Context context) {
		super(context);
		init();
	}

	public View_ViewTrailPhotoList(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public View_ViewTrailPhotoList(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public void init() {
		setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView arg0, View arg1, int position,
					long arg3) {
				
				ArrayList<Object_TrailPhoto> arReferencedPhotos = Object_TrailArticle.arPhotos;

				if (arReferencedPhotos.size() > position && mActivity != null) {
					Intent i = new Intent(mActivity,
							Activity_DisplayPhoto.class);
					Object_TrailPhoto photo = arReferencedPhotos.get(position);
					i.putExtra("url", photo.mURL);
					i.putExtra("saved", Object_TrailArticle.bSaved);
					mActivity.startActivity(i);
				}
			}
		});
	}
}
