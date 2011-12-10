package com.clevertrail.mobile.viewtrail;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Activity_ViewTrail_Map extends Activity {
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        textview.setText("This is the Map tab");
        setContentView(textview);
    }

}
