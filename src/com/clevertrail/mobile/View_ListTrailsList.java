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

package com.clevertrail.mobile;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.clevertrail.mobile.viewtrail.Activity_ViewTrail;
import com.clevertrail.mobile.viewtrail.Object_TrailArticle;

//a class that extends listview to display trails
public class View_ListTrailsList extends ListView {

	public Activity_ListTrails mActivity = null;

	public View_ListTrailsList(Context context) {
		super(context);
		init();
	}

	public View_ListTrailsList(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public View_ListTrailsList(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public void init() {
		setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView arg0, View arg1, int position,
					long arg3) {
				
				//display the trail article when a trail item is clicked
				if (mActivity != null) {
					String sName = mActivity.getTrailNameAt(position);
					Object_TrailArticle.loadTrailArticle(mActivity, sName);					
				}
			}
		});
	}
}
