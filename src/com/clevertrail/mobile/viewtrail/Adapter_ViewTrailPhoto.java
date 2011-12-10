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
import com.clevertrail.mobile.imageutils.ImageLoader;

public class Adapter_ViewTrailPhoto extends BaseAdapter {

	private Activity mActivity;
	private static LayoutInflater mInflater = null;
	public ImageLoader imageLoader;

	public Adapter_ViewTrailPhoto(Activity activity) {
		mActivity = activity;
		mInflater = (LayoutInflater) mActivity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(mActivity.getApplicationContext());
	}

	public int getCount() {
		ArrayList<Object_TrailPhoto> arReferencedPhotos = Object_TrailArticle.arReferencedPhotos; 
		return arReferencedPhotos.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = mInflater.inflate(R.layout.viewtrail_photoitem, null);

		TextView text = (TextView) vi.findViewById(R.id.textitem);
		ImageView image = (ImageView) vi.findViewById(R.id.imageitem);
		
		ArrayList<Object_TrailPhoto> arReferencedPhotos = Object_TrailArticle.arReferencedPhotos;

		if (arReferencedPhotos.size() >= position) {
			Object_TrailPhoto photo = arReferencedPhotos.get(position);
			imageLoader.DisplayImage(photo.mURL120px, image);
			text.setText(photo.mCaption);
		}

		return vi;
	}
}