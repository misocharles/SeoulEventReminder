/*
 * 
 */
package net.hasfun.appfestival.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;

import org.apache.log4j.Logger;

import android.content.Context;
import android.content.SharedPreferences;

// TODO: Auto-generated Javadoc
/**
 * Preferences Util <h3><b>Preferences</b></h3></br>.
 *
 * @author interwater
 * @date 2012. 9. 28.
 * @version 1.0.0
 * @since 1.0.0
 */
public class Pref
{
	private static final Logger log = Logger.getLogger(Pref.class);
	/** The m context. */
	private static Context mContext = null;

	/* file name */
	/** The Constant APP_PREF */
	public static final String APP_PREF = "APP_PREF";

	/** The Constant PREF_FIRST_EXEC. */
	public static final String PREF_FIRST_EXEC = "isFirstExec"; 
	
	/** The Constant PREF_LOGIN_STATUS.
	 * F : facebook
	 * G : GMail
	 * N : logout
	 ** */
	public static final String PREF_LOGIN_STATUS = "loginStatus";
	
	/** The Constant PREF_LOGIN_MAIL. */
	public static final String PREF_LOGIN_MAIL = "loginMail";
	
	public static final String PREF_APP_TOKEN = "appToken";
	
	public static final String PREF_UID = "uid";
	
	public static final String PREF_APP_VERSION = "appVersion";
	
	public static final String PREF_PUSH_YN = "pushYn";
	/**
	 * 최초실행정보 가져오기
	 * <p>.
	 *
	 * @return the first exec
	 * @author interwater
	 * @version 1.0.0
	 * @since 1.0.0
	 */
	public static boolean getFirstExec()
	{
		return getBoolean(APP_PREF, PREF_FIRST_EXEC, true);
	}
	
	
	/**
	 * 최초실행정보 저장하기.
	 *
	 * @param value the value
	 * @return true, if successful
	 * @author interwater
	 * @version 1.0.0
	 * @since 1.0.0
	 */
	public static boolean setFirstExec(boolean value)
	{
		return setBoolean(APP_PREF, PREF_FIRST_EXEC, value);
	}
	
	public static String getLoginStatus(){
		return getString(APP_PREF, PREF_LOGIN_STATUS, "N");
	}
	
	public static boolean setLoginStatus(String loginType){
		return setString(APP_PREF, PREF_LOGIN_STATUS, loginType);
	}
	
	public static String getLoginMail(){
		return getString(APP_PREF, PREF_LOGIN_MAIL, "");
	}
	
	public static boolean setLoginMail(String eMail){
		return setString(APP_PREF, PREF_LOGIN_MAIL, eMail);
	}
	
	public static String getAppToken(){
		return getString(APP_PREF, PREF_APP_TOKEN, "");
	}
	
	public static boolean setAppToken(String token){
		return setString(APP_PREF, PREF_APP_TOKEN, token);
	}
	
	public static String getUid(){
		return getString(APP_PREF, PREF_UID, "");
	}
	
	public static boolean setUid(String token){
		return setString(APP_PREF, PREF_UID, token);
	}
	
	public static int getAppVersion(){
		return getInt(APP_PREF, PREF_APP_VERSION, 1);
	}
	
	public static boolean setAppVersion(int appVersion){
		return setInt(APP_PREF, PREF_APP_VERSION, appVersion);
	}
	
	public static String getPushYn(){
		return getString(APP_PREF, PREF_PUSH_YN, "N");
	}
	
	public static boolean setPushYn(String val){
		return setString(APP_PREF, PREF_PUSH_YN, val);
	}
	
	/**
	 * 중요 context init shared Preferences 를 이용 하기 위해서는 어플이 구동될때 init명령어를 실행 시켜
	 * 주어야 한다.
	 *
	 * @param context the context
	 */
	public static void init(Context context)
	{
		mContext = context;
	}

	
	/**
	 * *******************************************************
	 * SharedPreferences 조작 메소드
	 * *******************************************************.
	 *
	 * @param filename the filename
	 * @param key the key
	 * @param defValue the def value
	 * @return the boolean
	 */
	
	/**
	 * SharedPreference 가져오기
	 * <p>.
	 *
	 * @param filename the filename
	 * @return the preferences
	 * @author interwater
	 * @date 2012. 5. 28.
	 * @version 1.0.0
	 * @since 1.0.0
	 */
	public static SharedPreferences getPreferences(String filename)
	{
		return mContext.getSharedPreferences(filename, Context.MODE_PRIVATE);
	}

	/**
	 * SharedPreferences.Editor 가져오기
	 * <p>
	 *
	 * @param filename the filename
	 * @return the preferences editor
	 * @author interwater
	 * @date 2012. 5. 28.
	 * @version 1.0.0
	 * @since 1.0.0
	 */
	public static SharedPreferences.Editor getPreferencesEditor(String filename)
	{
		SharedPreferences pref = getPreferences(filename);
		return pref.edit();
	}
	
	/* for boolean */
	private static boolean getBoolean(String filename, String key, boolean defValue)
	{
		return getPreferences(filename).getBoolean(key, defValue);
	}

	/**
	 * Sets the boolean.
	 *
	 * @param filename the filename
	 * @param key the key
	 * @param value the value
	 * @return true, if successful
	 */
	private static boolean setBoolean(String filename, String key, boolean value)
	{
		SharedPreferences.Editor editor = getPreferencesEditor(filename);
		editor.putBoolean(key, value);
		return editor.commit();
	}

	/* for String */
	/**
	 * Gets the string.
	 *
	 * @param filename the filename
	 * @param key the key
	 * @param defValue the def value
	 * @return the string
	 */
	private static String getString(String filename, String key, String defValue)
	{
		return getPreferences(filename).getString(key, defValue);
	}

	/**
	 * Sets the string.
	 *
	 * @param filename the filename
	 * @param key the key
	 * @param value the value
	 * @return true, if successful
	 */
	private static boolean setString(String filename, String key, String value)
	{
		SharedPreferences.Editor editor = getPreferencesEditor(filename);
		editor.putString(key, value);
		return editor.commit();
	}

	/**
	 * Gets the int.
	 *
	 * @param filename the filename
	 * @param key the key
	 * @param defValue the def value
	 * @return the int
	 */
	private static int getInt(String filename, String key, int defValue)
	{
		return getPreferences(filename).getInt(key, defValue);
	}

	/**
	 * Sets the int.
	 *
	 * @param filename the filename
	 * @param key the key
	 * @param value the value
	 * @return true, if successful
	 */
	private static boolean setInt(String filename, String key, int value)
	{
		SharedPreferences.Editor editor = getPreferencesEditor(filename);
		editor.putLong(key, value);
		return editor.commit();
	}

	/* for object must implements serializable */
	/**
	 * Sets the object.
	 *
	 * @param filename the filename
	 * @param key the key
	 * @param object the object
	 */
	private static void setObject(String filename, String key, Object object)
	{
		SharedPreferences.Editor editor = getPreferencesEditor(filename);
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream out;
		try
		{
			out = new ObjectOutputStream(arrayOutputStream);
			out.writeObject(object);
			out.close();
			arrayOutputStream.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		editor.putString(key, arrayOutputStream.toString());
		editor.commit();
	}

	/**
	 * Gets the object.
	 *
	 * @param filename the filename
	 * @param key the key
	 * @param defObject the def object
	 * @return the object
	 */
	private static Object getObject(String filename, String key, Object defObject)
	{
		String prefString = getPreferences(filename).getString(key, null);
		Object returnObject = defObject;
		if(prefString != null)
		{
			ByteArrayInputStream byteArray = new ByteArrayInputStream(prefString.getBytes());
			ObjectInputStream in;
			try
			{
				in = new ObjectInputStream(byteArray);
				returnObject = in.readObject();
			}
			catch(OptionalDataException e)
			{
				// TODO Auto-generated catch block
				log.error("OptionalDataException " + e.getMessage());
				e.printStackTrace();
			}
			catch(ClassNotFoundException e)
			{
				// TODO Auto-generated catch block
				log.error("ClassNotFoundException " + e.getMessage());
				e.printStackTrace();
			}
			catch(StreamCorruptedException e)
			{
				// TODO Auto-generated catch block
				log.error("StreamCorruptedException " + e.getMessage());
				e.printStackTrace();
			}
			catch(IOException e)
			{
				// TODO Auto-generated catch block
				log.error("IOException " + e.getMessage());
				e.printStackTrace();
			}
		}
		return returnObject;
	}

}