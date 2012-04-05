package com.clevertrail.mobile;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.clevertrail.mobile.viewtrail.Activity_ViewTrail;
import com.clevertrail.mobile.viewtrail.Object_TrailArticle;

public class View_ListTrailsList extends ListView {

	public Activity_ListTrails mActivity = null;

	public View_ListTrailsList(Context context) {
		super(context);
		init();
	}

	public View_ListTrailsList(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public View_ListTrailsList(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public void init() {
		setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView arg0, View arg1, int position,
					long arg3) {
				
				if (mActivity != null) {
					Intent i = new Intent(mActivity,
							Activity_ViewTrail.class);
					String sName = mActivity.getTrailNameAt(position);
					i.putExtra("name", sName);
					mActivity.startActivity(i);
				}
			}
		});
	}
}
