package com.clevertrail.mobile.viewtrail;

public class Object_TrailPhoto {

	public Object_TrailPhoto() {

	}

	public String getCaption() {
		String sReturn = mCaption;
		sReturn = sReturn.replace("[[", "");
		sReturn = sReturn.replace("]]", "");
		return sReturn;
	}

	String mURL;
	String mURL120px;
	String mCaption;
	String mSection;
}