package com.clevertrail.mobile.findtrail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.clevertrail.mobile.Activity_ListTrails;
import com.clevertrail.mobile.R;
import com.clevertrail.mobile.utils.TitleBar;

public class Activity_FindTrail_ByName extends Activity {
	
	public Activity mActivity = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		TitleBar.setCustomTitleBar(this, R.layout.findtrail_byname, "CleverTrail", R.drawable.ic_viewtrailtab_details_unselected);
		
		mActivity = this;
		
		// register events
		Button btnSaveTrail = (Button) findViewById(R.id.btnSearchByName);
		btnSaveTrail.setOnClickListener(onclickSearchByNameButton);
	}
	
	private OnClickListener onclickSearchByNameButton = new OnClickListener() {
		public void onClick(View v) {
			EditText etSearchByName = (EditText) findViewById(R.id.etSearchByName);
			Editable edSearchText = etSearchByName.getText();
			String sSearchText = edSearchText.toString();
			if (sSearchText != "" && mActivity != null) {
				Intent i = new Intent(mActivity, Activity_ListTrails.class);
				i.putExtra("name", sSearchText);
				mActivity.startActivity(i);
			} else {
				//display message about empty string
				
			}
		}
	};
}
