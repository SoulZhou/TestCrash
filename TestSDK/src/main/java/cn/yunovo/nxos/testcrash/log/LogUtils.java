package cn.yunovo.nxos.testcrash.log;

import android.util.Log;

public class LogUtils {
    public static final String TAG = "TestSDK";


    public static void d(String TAG, String text) {
        Log.d(TAG, text);
    }
    public static void e(String TAG, String text) {
        Log.e(TAG, text);
    }
    public static void w(String TAG, String text) {
        Log.w(TAG, text);
    }
    public static void i(String TAG, String text) {
        Log.i(TAG, text);
    }

    public static void LOGE(String TAG, String text, Throwable e) {
        Log.e(TAG, text);
    }
}
