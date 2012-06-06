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

import com.clevertrail.mobile.R;
import com.clevertrail.mobile.R.id;
import com.clevertrail.mobile.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

//activity to display trail photos in a list view
public class Activity_ViewTrail_Photos extends Activity {
	
    View_ViewTrailPhotoList photoList;
    Adapter_ViewTrailPhoto photoAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //are there photos to display?
        if (Object_TrailArticle.arPhotos != null && Object_TrailArticle.arPhotos.size() > 0)
        {
	        setContentView(R.layout.viewtrail_photos);
	        
	        //assign the listview and the adapter
	        photoList = (View_ViewTrailPhotoList)findViewById(R.id.photolist);
	        photoList.mActivity = this;	        
	        photoAdapter = new Adapter_ViewTrailPhoto(this);
	        photoList.setAdapter(photoAdapter);        
        } else {
        	setContentView(R.layout.viewtrail_nophotos);
        }
    }
    
    @Override
    public void onDestroy()
    {
    	if (photoList != null)
    		photoList.setAdapter(null);

    	super.onDestroy();
    }

}
