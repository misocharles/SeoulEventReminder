package net.hasfun.appfestival.base;


import net.hasfun.appfestival.common.CommonUtils;
import net.hasfun.appfestival.common.ConfigureLog4J;
import net.hasfun.appfestival.common.Constants;
import net.hasfun.appfestival.common.Pref;

import org.apache.log4j.Logger;
import org.springframework.security.crypto.encrypt.AndroidEncryptors;
import org.springframework.social.connect.sqlite.SQLiteConnectionRepository;
import org.springframework.social.connect.sqlite.support.SQLiteConnectionRepositoryHelper;

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
