package com.hasfun.seoulsubway.common;

import com.hasfun.seoulsubway.base.BaseActivity;

import android.os.Environment;
import android.webkit.WebView;

public class Constants {
	private static boolean isReal = true;
	
	public static BaseActivity baseActivity;
	
	public static String phoneNo;
	
	public static String deviceId;
	
	public static String mSdPath = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ? Environment.getExternalStorageDirectory().getAbsolutePath() : Environment.MEDIA_UNMOUNTED;
	
}
