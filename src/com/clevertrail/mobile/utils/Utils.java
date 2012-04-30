package com.clevertrail.mobile.utils;

import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class Utils {
	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}

	public static boolean isNetworkAvailable(Activity activity) {
		ConnectivityManager connectivityManager = (ConnectivityManager) activity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null;
	}


	public static void showMessage(final Activity activity, final int nMessage) {
		if (nMessage > 0) {
			activity.runOnUiThread(new Runnable() {
				public void run() {
					AlertDialog ad = new AlertDialog.Builder(activity).create();
					ad.setCancelable(false); // This blocks the 'BACK' button
					ad.setMessage(activity.getText(nMessage));
					ad.setButton("OK", new DialogInterface.OnClickListener() {
					    @Override
					    public void onClick(DialogInterface dialog, int which) {
					        dialog.dismiss();                    
					    }
					});
					ad.show();
				}
			});
		}
	}
}