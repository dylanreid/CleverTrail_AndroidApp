package com.clevertrail.mobile.utils;

import java.io.InputStream;
import java.io.OutputStream;

import com.clevertrail.mobile.R;

import android.app.Activity;
import android.content.Context;
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
					Context context = activity.getApplicationContext();
					int duration = Toast.LENGTH_LONG;

					Toast toast = Toast.makeText(context,
							activity.getText(nMessage), duration);
					toast.show();
				}
			});
		}
	}
}