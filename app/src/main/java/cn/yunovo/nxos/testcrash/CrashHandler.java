package cn.yunovo.nxos.testcrash;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.yunovo.nxos.carservice.sdk.manager.SettingsManager;
import cn.yunovo.nxos.carservice.sdk.utils.LogUtil;

public class CrashHandler implements UncaughtExceptionHandler {
	
	String pkgName;
	Context mContext;
	UncaughtExceptionHandler mDefaultHandler;
	static CrashHandler mInstance;
	
	/**
	 * @return the mInstance
	 */
	public static CrashHandler getInstance(Context context) {
		if(mInstance == null)
			mInstance = new CrashHandler(context);
		return mInstance;
	}
	
	/**
	 * 
	 */
	public CrashHandler(Context context) {
		mContext = context.getApplicationContext();
		pkgName = mContext.getPackageName();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}
	
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if(!handleException(ex)) {
			if(mDefaultHandler == null) {
				mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
			}
			mDefaultHandler.uncaughtException(thread, ex);
		} else {
//			sleep(60*1000);
            android.os.Process.killProcess(android.os.Process.myPid());  
            System.exit(1);
		}
	}

	public void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	private boolean handleException(Throwable ex) {
		if(ex == null) return false;
		Bundle bundle = new Bundle();
		try {
			PackageManager pm = mContext.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
			if (pi != null) {
				String versionName = pi.versionName == null ? "null" : pi.versionName;
				String versionCode = pi.versionCode + "";
				bundle.putString("versionName", versionName);
				bundle.putString("versionCode", versionCode);
				bundle.putString("pkgName", pkgName);
			}
		} catch (NameNotFoundException e) {
			LogUtil.e("an error occured when collect package info", e.toString());
		}
//		collectInfo(bundle);
		String str = saveCarshException(ex);
//		LogUtil.d("soul","str = "+str);
		bundle.putString("message",str);
//		SettingsManager.instance().testCrash(bundle);
		return true;
	}
	
	void collectInfo(Bundle bundle) {
		if(bundle == null) {
			return;
		}
		try {  
            PackageManager pm = mContext.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {  
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
				bundle.putString("versionName", versionName);
				bundle.putString("versionCode", versionCode);
				bundle.putString("pkgName", pkgName);
            }
        } catch (NameNotFoundException e) {
            LogUtil.e("an error occured when collect package info", e.toString());
        }  
	}
	
	String saveCarshException (Throwable ex) {
		StringBuffer sb = new StringBuffer();
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);  
        Throwable cause = ex.getCause();
        while (cause != null) {  
            cause.printStackTrace(printWriter);  
            cause = cause.getCause();  
        }
        printWriter.close();  
        String result = writer.toString();
//		LogUtil.d("soul","result = "+result);
        sb.append(result);
        return sb.toString();
	}
}
