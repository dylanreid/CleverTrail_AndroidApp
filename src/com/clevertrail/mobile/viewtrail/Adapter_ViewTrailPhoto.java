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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.clevertrail.mobile.R;
import com.clevertrail.mobile.utils.ImageLoader;


//adapter class for display a listview of trail photos
public class Adapter_ViewTrailPhoto extends BaseAdapter {

	private Activity mActivity;
	private static LayoutInflater mInflater = null;
	public ImageLoader imageLoader;

	// get handles to an inflater and an image loader
	public Adapter_ViewTrailPhoto(Activity activity) {
		mActivity = activity;
		mInflater = (LayoutInflater) mActivity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(mActivity.getApplicationContext());
	}

	public int getCount() {
		ArrayList<Object_TrailPhoto> arReferencedPhotos = Object_TrailArticle.arPhotos; 
		return arReferencedPhotos.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	//function to draw the photos
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = mInflater.inflate(R.layout.viewtrail_photoitem, null);

		//get text and image for the given position's photo
		TextView text = (TextView) vi.findViewById(R.id.textitem);
		ImageView image = (ImageView) vi.findViewById(R.id.imageitem);
		
		ArrayList<Object_TrailPhoto> arReferencedPhotos = Object_TrailArticle.arPhotos;

		if (arReferencedPhotos.size() >= position) {
			//find the photo info and display it
			Object_TrailPhoto photo = arReferencedPhotos.get(position);
			imageLoader.DisplayImage(photo.mURL120px, image);
			String sCaption = photo.getCaption();
			if (sCaption != null && sCaption.compareTo("") != 0) {
				text.setText(sCaption);
			} else {
				//if there is no text, display a helpful message
				text.setText("[Click for larger image]");
			}
		}

		return vi;
	}
}