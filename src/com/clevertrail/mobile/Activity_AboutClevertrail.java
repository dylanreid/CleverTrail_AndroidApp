package com.clevertrail.mobile;

import android.app.Activity;
import android.os.Bundle;

import com.clevertrail.mobile.utils.TitleBar;

public class Activity_AboutClevertrail extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		TitleBar.setCustomTitleBar(this, R.layout.aboutclevertrail, "CleverTrail", 0);

	}
}
