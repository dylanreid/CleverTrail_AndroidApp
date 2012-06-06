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

package com.clevertrail.mobile.viewtrail;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

//listview for displaying trail photos
public class View_ViewTrailPhotoList extends ListView {

	public Activity mActivity = null;

	public View_ViewTrailPhotoList(Context context) {
		super(context);
		init();
	}

	public View_ViewTrailPhotoList(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public View_ViewTrailPhotoList(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public void init() {
		//register onclick event for photos
		setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView arg0, View arg1, int position,
					long arg3) {
				
				ArrayList<Object_TrailPhoto> arReferencedPhotos = Object_TrailArticle.arPhotos;

				//if we have a valid photo the user is clicking on, display a larger version of it
				if (arReferencedPhotos.size() > position && mActivity != null) {
					Intent i = new Intent(mActivity,
							Activity_DisplayPhoto.class);
					Object_TrailPhoto photo = arReferencedPhotos.get(position);
					i.putExtra("url", photo.mURL);
					i.putExtra("saved", Object_TrailArticle.bSaved);
					mActivity.startActivity(i);
				}
			}
		});
	}
}
