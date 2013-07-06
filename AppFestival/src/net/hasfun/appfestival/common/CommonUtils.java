package net.hasfun.appfestival.common;

import java.io.File;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import org.apache.log4j.Logger;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.text.format.DateUtils;

public class CommonUtils {
	private CommonUtils(){}
	private static int mVibrateTime = 500;
	private static Vibrator mVibrator;
	private static Context mContext;
	private static final Logger log = Logger.getLogger(CommonUtils.class);
	
	public static void init(Context context){
		mContext = context;
		// vibrate 관련 설정
		mVibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
	}
	
	public static void vibrate(){
		mVibrator.vibrate(mVibrateTime);
	}
	
	public static void vibrate(int vibrateTime){
		mVibrator.vibrate(vibrateTime);
	}
	
	private static int clearCacheFolder(final File dir, final int numDays) {

	    int deletedFiles = 0;
	    if (dir!= null && dir.isDirectory()) {
	        try {
	            for (File child:dir.listFiles()) {

	                //first delete subdirectories recursively
	                if (child.isDirectory()) {
	                    deletedFiles += clearCacheFolder(child, numDays);
	                }

	                //then delete the files and subdirectories in this dir
	                //only empty directories can be deleted, so subdirs have been done first
	                if (child.lastModified() < new Date().getTime() - numDays * DateUtils.DAY_IN_MILLIS) {
	                    if (child.delete()) {
	                        deletedFiles++;
	                    }
	                }
	            }
	        }
	        catch(Exception e) {
	            log.error(String.format("Failed to clean the cache, error %s", e.getMessage()));
	        }
	    }
	    return deletedFiles;
	}

	/*
	 * Delete the files older than numDays days from the application cache
	 * 0 means all files.
	 */
	public static void clearCache(final Context context, final int numDays) {
	    log.info(String.format("Starting cache prune, deleting files older than %d days", numDays));
	    int numDeletedFiles = clearCacheFolder(context.getCacheDir(), numDays);
	    log.info(String.format("Cache pruning completed, %d files deleted", numDeletedFiles));
	}
}
