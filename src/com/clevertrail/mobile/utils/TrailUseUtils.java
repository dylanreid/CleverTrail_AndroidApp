package com.clevertrail.mobile.utils;

import java.io.File;

import com.clevertrail.mobile.R;

import android.content.Context;

public class TrailUseUtils {

	public static int getTrailUseResource(int nTrailUse){
		switch(nTrailUse){
		case 0: return R.drawable.trailuse_hike;
		case 1: return R.drawable.trailuse_bicycle;
		case 2: return R.drawable.trailuse_handicap;
		case 3: return R.drawable.trailuse_swim;
		case 4: return R.drawable.trailuse_climb;
		case 5: return R.drawable.trailuse_horse;
		case 6: return R.drawable.trailuse_camp;
		case 7: return R.drawable.trailuse_dog;
		case 8: return R.drawable.trailuse_fish;
		case 9: return R.drawable.trailuse_family;
		}
		return 0;
	}
	
}