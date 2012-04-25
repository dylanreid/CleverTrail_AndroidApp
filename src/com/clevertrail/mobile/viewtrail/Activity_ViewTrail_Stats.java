package com.clevertrail.mobile.viewtrail;

import com.clevertrail.mobile.R;
import com.clevertrail.mobile.utils.ImageLoader;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Activity_ViewTrail_Stats extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.viewtrail_stats);
    }
    
    public void onStart(){
    	super.onStart();
    	
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
		if (Object_TrailArticle.mImage != null)
			imageLoader.DisplayImage(Object_TrailArticle.mImage.mURL, image);
		
		TextView tvImageCredit = (TextView) findViewById(R.id.txtImageCredit);
		tvImageCredit.setText(Object_TrailArticle.sImageCredit);
		
		if (Object_TrailArticle.mTrailUse[0]){
			ImageView ivHike = (ImageView) findViewById(R.id.ivTrailUseHike);
			ivHike.setVisibility(View.VISIBLE);			
		}
		
		if (Object_TrailArticle.mTrailUse[1]){
			ImageView ivBicycle = (ImageView) findViewById(R.id.ivTrailUseBicycle);
			ivBicycle.setVisibility(View.VISIBLE);			
		}
		
		if (Object_TrailArticle.mTrailUse[2]){
			ImageView ivHandicap = (ImageView) findViewById(R.id.ivTrailUseHandicap);
			ivHandicap.setVisibility(View.VISIBLE);			
		}
		
		if (Object_TrailArticle.mTrailUse[3]){
			ImageView ivSwim = (ImageView) findViewById(R.id.ivTrailUseSwim);
			ivSwim.setVisibility(View.VISIBLE);			
		}
		
		if (Object_TrailArticle.mTrailUse[4]){
			ImageView ivClimb = (ImageView) findViewById(R.id.ivTrailUseClimb);
			ivClimb.setVisibility(View.VISIBLE);			
		}
		
		if (Object_TrailArticle.mTrailUse[5]){
			ImageView ivHorse = (ImageView) findViewById(R.id.ivTrailUseHorse);
			ivHorse.setVisibility(View.VISIBLE);			
		}
		
		if (Object_TrailArticle.mTrailUse[6]){
			ImageView ivCamp = (ImageView) findViewById(R.id.ivTrailUseCamp);
			ivCamp.setVisibility(View.VISIBLE);			
		}
		
		if (Object_TrailArticle.mTrailUse[7]){
			ImageView ivDog = (ImageView) findViewById(R.id.ivTrailUseDog);
			ivDog.setVisibility(View.VISIBLE);			
		}
		
		if (Object_TrailArticle.mTrailUse[8]){
			ImageView ivFish = (ImageView) findViewById(R.id.ivTrailUseFish);
			ivFish.setVisibility(View.VISIBLE);			
		}
		
		if (Object_TrailArticle.mTrailUse[9]){
			ImageView ivFamily = (ImageView) findViewById(R.id.ivTrailUseFamily);
			ivFamily.setVisibility(View.VISIBLE);			
		}
			
    }
}
