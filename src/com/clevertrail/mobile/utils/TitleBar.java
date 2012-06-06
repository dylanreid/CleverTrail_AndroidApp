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

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.clevertrail.mobile.Activity_MainMenu;
import com.clevertrail.mobile.R;

//a global class to set the title bar with an icon and title
public class TitleBar {
	
	private static Activity mActivity;
    
    public static void setCustomTitleBar(Activity activity, int layoutID, String title, int iconID)
    {
    	mActivity = activity;
    	activity.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);      
        activity.setContentView(layoutID);
        activity.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
        ImageView ivIcon = (ImageView) activity.findViewById(R.id.titlebaricon);
        if (iconID != 0){	        
	        ivIcon.setImageResource(iconID);
	        ivIcon.setOnClickListener(onclickMainMenu);
        } else {
        	ivIcon.setImageResource(0);
        }
        if (title != ""){
        	TextView tvTitle = (TextView) activity.findViewById(R.id.titlebartitle);
        	tvTitle.setText(title);
        }
        
        ImageView ivLogo = (ImageView) activity.findViewById(R.id.titlebarlogo);
        
        //handle a click listener to bring the user back to the main menu
        ivLogo.setOnClickListener(onclickMainMenu);
    }
    
    //handle a click on the main logo to bring the user back to the main menu
    private static OnClickListener onclickMainMenu = new OnClickListener() {
		public void onClick(View v) {
				Intent i = new Intent(mActivity,
						Activity_MainMenu.class);
				mActivity.startActivity(i);
		}
	};

}