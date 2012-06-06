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

//wrapper class for a trail photo
public class Object_TrailPhoto {

	public Object_TrailPhoto() {

	}

	//parse out anything unnecessary in the caption
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