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

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import com.clevertrail.mobile.R;
import com.clevertrail.mobile.utils.ImageLoader;

//class to display a photo for a trail
public class Activity_DisplayPhoto extends Activity {

    public void onCreate(Bundle savedInstanceState) {    	
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.viewtrail_displayphoto);        
                
        //get a handle to an image loader
        ImageLoader imageLoader=new ImageLoader(this.getApplicationContext());        
        ImageView image=(ImageView)findViewById(R.id.displayphoto);
        
        Bundle b = getIntent().getExtras();
        String sURL = b.getString("url");
        
        //is this saved or not (do we look on internal memory or internet?)
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
