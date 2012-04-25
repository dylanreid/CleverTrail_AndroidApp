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
        ivLogo.setOnClickListener(onclickMainMenu);
    }
    
    private static OnClickListener onclickMainMenu = new OnClickListener() {
		public void onClick(View v) {
				Intent i = new Intent(mActivity,
						Activity_MainMenu.class);
				mActivity.startActivity(i);
		}
	};

}