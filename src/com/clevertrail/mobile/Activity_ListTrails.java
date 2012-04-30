package com.clevertrail.mobile;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;

import com.clevertrail.mobile.utils.TitleBar;
import com.clevertrail.mobile.utils.Utils;

public class Activity_ListTrails extends Activity {

	View_ListTrailsList trailList;
	Adapter_ListTrails trailAdapter;
	ArrayList<Object_TrailItem> arTrails;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		arTrails = Object_TrailList.arTrails;
		int icon = R.drawable.ic_viewtrailtab_save_unselected;
		String title = getString(R.string.title_traillist);

		Bundle b = getIntent().getExtras();
		if (b != null) {
			icon = b.getInt("icon");
			title = b.getString("title");
		}

		if (arTrails != null && arTrails.size() > 0) {
			TitleBar.setCustomTitleBar(this, R.layout.listtrails, title, icon);
		} else {
			TitleBar.setCustomTitleBar(this, R.layout.listtrails_notrails,
					title, icon);
		}

		trailList = (View_ListTrailsList) findViewById(R.id.traillist);
		if (trailList != null) {
			trailList.mActivity = this;
			trailAdapter = new Adapter_ListTrails(this, arTrails);
			trailList.setAdapter(trailAdapter);
		}
		
		if (arTrails != null && arTrails.size() >= 20) {
			Utils.showMessage(this, R.string.alert_showing20trails);
		}
	}

	public String getTrailNameAt(int pos) {
		String sName = "";
		if (arTrails != null && arTrails.size() > pos) {
			return arTrails.get(pos).sName;
		}
		return sName;
	}

	@Override
	public void onDestroy() {
		if (trailList != null)
			trailList.setAdapter(null);

		super.onDestroy();
	}

}
