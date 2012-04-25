package com.clevertrail.mobile.viewtrail;

import com.clevertrail.mobile.R;
import com.clevertrail.mobile.R.id;
import com.clevertrail.mobile.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

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
