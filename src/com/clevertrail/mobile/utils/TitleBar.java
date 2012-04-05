package com.clevertrail.mobile.utils;

import android.app.Activity;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.clevertrail.mobile.R;

public class TitleBar {
    
    public static void setCustomTitleBar(Activity activity, int layoutID, String title, int iconID)
    {
    	activity.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);      
        activity.setContentView(layoutID);
        activity.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
        ImageView ivIcon = (ImageView) activity.findViewById(R.id.titlebaricon);
        if (iconID != 0){	        
	        ivIcon.setImageResource(iconID);
        } else {
        	ivIcon.setImageResource(0);
        }
        if (title != ""){
        	TextView tvTitle = (TextView) activity.findViewById(R.id.titlebartitle);
        	tvTitle.setText(title);
        }
    }

}