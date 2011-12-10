package com.clevertrail.mobile.viewtrail;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import com.clevertrail.mobile.R;
import com.clevertrail.mobile.imageutils.ImageLoader;

public class Activity_DisplayPhoto extends Activity {

    public void onCreate(Bundle savedInstanceState) {    	
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.viewtrail_displayphoto);
        
                
        ImageLoader imageLoader=new ImageLoader(this.getApplicationContext());        
        ImageView image=(ImageView)findViewById(R.id.displayphoto);
        
        Bundle b = getIntent().getExtras();
        String sURL = b.getString("url");
        boolean bSaved = b.getBoolean("saved");
        
        //if the article was saved, use the caching mechanism
        if (bSaved){
        	imageLoader.DisplayImage(sURL, image); 
        } else {
        	Bitmap bitmap = imageLoader.getBitmapFromWeb(sURL);
        	image.setImageBitmap(bitmap);
        }        
    }
}
