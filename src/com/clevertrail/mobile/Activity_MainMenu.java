package com.clevertrail.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class Activity_MainMenu extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.mainmenu);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.window_title);
    }
 
}
