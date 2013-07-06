package net.hasfun.appfestival.base;

import net.hasfun.appfestival.common.Constants;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;

/**
 * 
 * @author interwater
 * @date 2012-09-10
 * 
 */
public class BaseActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Constants.baseActivity = this;
		TelephonyManager telephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		Constants.phoneNo = telephonyMgr.getLine1Number().replace("-", "").replace("+82", "0");
		Constants.deviceId = telephonyMgr.getDeviceId();
	}

	private ProgressDialog progressDialog;

	private boolean destroyed = false;
	
	
	// ***************************************
	// Activity methods
	// ***************************************
	public MainApplication getApplicationContext() {
		return (MainApplication) super.getApplicationContext();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.destroyed = true;
	}

	// ***************************************
	// Public methods
	// ***************************************
	public void showProgressDialog(CharSequence message) {
		try {
			if (this.progressDialog == null) {
				this.progressDialog = new ProgressDialog(this);
				this.progressDialog.setIndeterminate(true);
			}
			this.progressDialog.setMessage(message);
			this.progressDialog.show();
		} catch (Exception e){
			// error 발생시 걍 무시 
		}
	}
	
	public void setDialogProgress(int progress){
		try {
			this.progressDialog.setProgress(progress);
		}catch (Exception e){
			// error 발생시 걍 무시
		}
	}
	public void dismissProgressDialog() {
		try {
			if (this.progressDialog != null && !this.destroyed) {
				this.progressDialog.dismiss();
			}
		}catch (Exception e){
			// error 발생시 걍 무시
		}
	}
}
