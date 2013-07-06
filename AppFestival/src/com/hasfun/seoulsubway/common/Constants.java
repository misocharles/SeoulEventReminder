package com.hasfun.seoulsubway.common;

import java.io.IOException;
import java.util.Properties;

import com.hasfun.seoulsubway.base.BaseActivity;

import android.os.Environment;
import android.webkit.WebView;

public class Constants {
	private static boolean isReal = true;
	
	public static BaseActivity baseActivity;
	
	public static String phoneNo;
	
	public static String deviceId;
	
	public static final String mSdPath = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ? Environment.getExternalStorageDirectory().getAbsolutePath() : Environment.MEDIA_UNMOUNTED;
	
	/*private static final Properties config = new Properties(); 
	static { 
		try { 
			config.load(Constants.class.getClassLoader().getResourceAsStream("project.properties"));
		} catch (IOException e) { 
			e.printStackTrace(); 
		} 
	}*/ 
	/*
	public static final String EVERNOTE_KEY = config.getProperty("evernote.key");
	public static final String EVERNOTE_SECRET = config.getProperty("evernote.secret");
	public static final String mapKey = config.getProperty("daum.map.key");
	public static final String PublicApiKey = config.getProperty("data.seoul.key");
	*/
	
	public static final String EVERNOTE_KEY = "misocharles";
	public static final String EVERNOTE_SECRET = "ad397b1810ad005a";
	public static final String mapKey = "18836643ed133d7dc1615a773d00076e4faa7dca";
	public static final String PublicApiKey = "4150495f32313130626967656e697573";

}
