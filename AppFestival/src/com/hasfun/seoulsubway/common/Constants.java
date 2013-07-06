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
	
	public static String PublicApiKey = "4150495f32313130626967656e697573";
	
	public static final String mapKey = "18836643ed133d7dc1615a773d00076e4faa7dca";
	
}
