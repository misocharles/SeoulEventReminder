package com.hasfun.seoulsubway.base;



import org.apache.log4j.Logger;
import org.springframework.security.crypto.encrypt.AndroidEncryptors;
import org.springframework.social.connect.sqlite.SQLiteConnectionRepository;
import org.springframework.social.connect.sqlite.support.SQLiteConnectionRepositoryHelper;

import com.hasfun.seoulsubway.common.CommonUtils;
import com.hasfun.seoulsubway.common.ConfigureLog4J;
import com.hasfun.seoulsubway.common.Constants;
import com.hasfun.seoulsubway.common.Pref;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;


public class MainApplication extends Application{
	private SQLiteOpenHelper repositoryHelper;
	private final Logger log = Logger.getLogger(this.getClass());
	private Context context = this;
	// ***************************************
	// Application Methods
	// ***************************************
	@Override
	public void onCreate() {
		Pref.init(this);
		CommonUtils.init(this);
		ConfigureLog4J.configure();
	}
}
