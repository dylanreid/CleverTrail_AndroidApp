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

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.clevertrail.mobile.R;
import com.clevertrail.mobile.utils.ImageLoader;

//activity to display stats for a trail
public class Activity_ViewTrail_Stats extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.viewtrail_stats);
	}

	public void onStart() {
		super.onStart();

		//load all the appropriate stats
		TextView tvName = (TextView) findViewById(R.id.txtStatsName);
		tvName.setText(Object_TrailArticle.sName);
		TextView tvDistance = (TextView) findViewById(R.id.txtDistance);
		tvDistance.setText(Object_TrailArticle.sDistance);
		TextView tvDifficulty = (TextView) findViewById(R.id.txtDifficulty);
		tvDifficulty.setText(Object_TrailArticle.sDifficulty);
		TextView tvTime = (TextView) findViewById(R.id.txtTime);
		tvTime.setText(Object_TrailArticle.sTime);
		TextView tvTrailType = (TextView) findViewById(R.id.txtTrailType);
		tvTrailType.setText(Object_TrailArticle.sType);
		TextView tvElevationGain = (TextView) findViewById(R.id.txtElevation);
		tvElevationGain.setText(Object_TrailArticle.sElevation);
		TextView tvHighPoint = (TextView) findViewById(R.id.txtHighPoint);
		tvHighPoint.setText(Object_TrailArticle.sHighPoint);
		TextView tvLowPoint = (TextView) findViewById(R.id.txtLowPoint);
		tvLowPoint.setText(Object_TrailArticle.sLowPoint);
		TextView tvNearestCity = (TextView) findViewById(R.id.txtNearestCity);
		tvNearestCity.setText(Object_TrailArticle.sNearestCity);
		TextView tvBestMonths = (TextView) findViewById(R.id.txtBestMonths);
		tvBestMonths.setText(Object_TrailArticle.sBestMonth);

		ImageLoader imageLoader = new ImageLoader(getApplicationContext());
		ImageView image = (ImageView) findViewById(R.id.mainimage);
		//load the main image
		if (Object_TrailArticle.mImage != null && Object_TrailArticle.mImage.mURL.compareTo("") != 0)
			imageLoader.DisplayImage(Object_TrailArticle.mImage.mURL, image);

		//image credit if there is one
		if (Object_TrailArticle.sImageCredit.compareTo("") != 0) {
			TextView tvImageCredit = (TextView) findViewById(R.id.txtImageCredit);
			tvImageCredit.setText(Object_TrailArticle.sImageCredit);
			TextView tvImageCreditLabel = (TextView) findViewById(R.id.txtImageCreditLabel);
			tvImageCreditLabel.setVisibility(View.VISIBLE);
		}

		//add the trail use icons
		LinearLayout llTrailUse = (LinearLayout) this
				.findViewById(R.id.llTrailUse);
		llTrailUse.removeAllViews();

		if (Object_TrailArticle.mTrailUse[0]) {
			addTrailUse(llTrailUse, R.drawable.trailuse_hike);
		}

		if (Object_TrailArticle.mTrailUse[1]) {
			addTrailUse(llTrailUse, R.drawable.trailuse_bicycle);
		}

		if (Object_TrailArticle.mTrailUse[2]) {
			addTrailUse(llTrailUse, R.drawable.trailuse_handicap);
		}

		if (Object_TrailArticle.mTrailUse[3]) {
			addTrailUse(llTrailUse, R.drawable.trailuse_swim);
		}

		if (Object_TrailArticle.mTrailUse[4]) {
			addTrailUse(llTrailUse, R.drawable.trailuse_climb);
		}

		if (Object_TrailArticle.mTrailUse[5]) {
			addTrailUse(llTrailUse, R.drawable.trailuse_horse);
		}

		if (Object_TrailArticle.mTrailUse[6]) {
			addTrailUse(llTrailUse, R.drawable.trailuse_camp);
		}

		if (Object_TrailArticle.mTrailUse[7]) {
			addTrailUse(llTrailUse, R.drawable.trailuse_dog);
		}

		if (Object_TrailArticle.mTrailUse[8]) {
			addTrailUse(llTrailUse, R.drawable.trailuse_fish);
		}

		if (Object_TrailArticle.mTrailUse[9]) {
			addTrailUse(llTrailUse, R.drawable.trailuse_family);
		}

	}

	//helper function to add the trail use icon
	private void addTrailUse(LinearLayout ll, int icon) {
		if (ll != null && icon > 0) {
			ImageView ivUse;
			ivUse = new ImageView(this);
			ivUse.setImageResource(icon);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.gravity = Gravity.LEFT;
			ivUse.setLayoutParams(params);
			ivUse.setPadding(1, 0, 0, 0);
			ll.addView(ivUse);
		}
	}

}
