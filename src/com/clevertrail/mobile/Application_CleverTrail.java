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

import android.app.Application;
import org.acra.ACRA;
import org.acra.*;
import org.acra.annotation.*;

@ReportsCrashes(formKey = "dDRHLTktTEx3amVNQ3RXUzBYelJ0bGc6MQ")
public class Application_CleverTrail extends Application {

	@Override
	//this puts a hook for the ACRA crash reporting software
    public void onCreate() {
        // The following line triggers the initialization of ACRA
        ACRA.init(this);
        super.onCreate();
    }
}
