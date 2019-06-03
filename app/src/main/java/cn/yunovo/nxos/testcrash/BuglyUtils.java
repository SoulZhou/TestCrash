package cn.yunovo.nxos.testcrash;

import android.content.Context;
import android.os.SystemProperties;
import android.text.TextUtils;

import com.tencent.bugly.crashreport.CrashReport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BuglyUtils {

    //    private static final String APP_ID = "07295ce98b";
    public static final String APP_ID = "55e310b91f";
    public static final boolean IS_DEBUG = true;

    public static final String VERSIONNAME = "versionName";
    public static final String VERSIONCODE = "versionCode";
    public static final String PKGNAME = "pkgName";
    public static final String MESSAGE = "message";

    public static void initBugly(){
        Context context = App.mInstance;
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
//        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
//        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        strategy.setAppVersion(BuildConfig.VERSION_NAME);
        strategy.setAppPackageName(packageName);
        strategy.setAppChannel(getSystemVer());
        CrashReport.setUserId(context,getSnStr());
        CrashReport.setIsDevelopmentDevice(context, IS_DEBUG);
        //  初始化Bugly
        CrashReport.initCrashReport(context, APP_ID, IS_DEBUG, strategy);
        // 如果通过“AndroidManifest.xml”来配置APP信息，初始化方法如下
        // CrashReport.initCrashReport(context, strategy);
        CrashReport.enableBugly(false);
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
    /**
     * 获取渠道号
     */
    public static String getSystemVer(){
        try {
            String sys_ver = android.os.SystemProperties.get("ro.nxos.version");
            String[] buffers = sys_ver.split("_");
            if(buffers!=null&&buffers.length>=3){
                return buffers[0]+"_"+buffers[2];
            }
        }catch (Exception e){
            return "unknown";
        }
        return "unknown";
    }
    /**
     * 获取sn号
     */
    public static String getSnStr(){
        try{
            return SystemProperties.get("gsm.serial", "").replaceAll("\\s+\\w*", "");
        }catch (Exception e){
            return "unknown";
        }
    }
}
