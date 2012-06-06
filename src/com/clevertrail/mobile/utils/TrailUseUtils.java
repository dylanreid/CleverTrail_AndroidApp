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

package com.clevertrail.mobile.utils;

import java.io.File;

import com.clevertrail.mobile.R;

import android.content.Context;

//class with some basic utils for the trail use functionality
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