package com.clevertrail.mobile;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.clevertrail.mobile.utils.ImageLoader;
import com.clevertrail.mobile.utils.TrailUseUtils;

public class Adapter_ListTrails extends BaseAdapter {

	private Activity mActivity;
	private static LayoutInflater mInflater = null;
	public ImageLoader imageLoader;
	public ArrayList<Object_TrailItem> arTrails;

	public Adapter_ListTrails(Activity activity, ArrayList<Object_TrailItem> trails) {
		mActivity = activity;
		mInflater = (LayoutInflater) mActivity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(mActivity.getApplicationContext());
		arTrails = trails;
	}

	public int getCount() {
		return arTrails.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = mInflater.inflate(R.layout.listtrails_item, null);
		
		if (position < arTrails.size())
		{
			Object_TrailItem trail = arTrails.get(position);
			ImageLoader imageLoader = new ImageLoader(mActivity.getApplicationContext());
			
			TextView textName = (TextView) vi.findViewById(R.id.listtrails_name);
			TextView textDetails1 = (TextView) vi.findViewById(R.id.listtrails_details1);
			TextView textDetails2 = (TextView) vi.findViewById(R.id.listtrails_details2);
			TextView textCity = (TextView) vi.findViewById(R.id.listtrails_city);
			ImageView image = (ImageView) vi.findViewById(R.id.listtrails_imageitem);
			
			textName.setText(trail.sName); 
			imageLoader.DisplayImage(trail.sThumbnail, image); 
			textCity.setText(trail.sNearestCity + " ");
			
			String sDetails1 = trail.sDifficulty;			
			if (trail.sDifficulty.compareTo("") != 0 && trail.sDistance.compareTo("") != 0) {
				sDetails1 = sDetails1.concat(" - ").concat(trail.sDistance);
			} else {
				sDetails1 = sDetails1.concat(trail.sDistance);
			}
			textDetails1.setText(sDetails1);
			textDetails2.setText(trail.sTrailType);
			
			LinearLayout llTrailUse = (LinearLayout) vi.findViewById(R.id.listtrails_trailuse);
			llTrailUse.removeAllViews();
			
			for (int i=0; i<trail.mTrailUse.length; i++){
				if (trail.mTrailUse[i]) {
					ImageView imageTrailUse = new ImageView(mActivity);			
					imageTrailUse.setImageResource(TrailUseUtils.getTrailUseResource(i));
					LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
							LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					lp.setMargins(2, 0, 0, 0);
					lp.gravity = Gravity.RIGHT;					
					imageTrailUse.setLayoutParams(lp);
					llTrailUse.addView(imageTrailUse);	
				}
			}
		}

		return vi;
	}
}