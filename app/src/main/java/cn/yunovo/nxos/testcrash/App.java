package cn.yunovo.nxos.testcrash;

import android.app.Application;
import android.os.Bundle;
import android.os.RemoteException;

import cn.yunovo.car.api.ApiKit;
import cn.yunovo.car.ipc.Connection;
import cn.yunovo.car.ipc.ICallback;
import cn.yunovo.car.ipc.IRemote;
import cn.yunovo.nxos.carservice.sdk.ipc.NxCarServiceKit;


public class App extends Application implements Connection.OnCallbackListener{

    public static final String APP_ID = "55e310b91f";
//    public static final String APP_ID = "f9fda6d80a";
    public static final boolean IS_DEBUG = true;
    public static App mInstance = null;
    private Connection connection = new Connection(this);
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        /**
         * 第三个参数为SDK调试模式开关，调试模式的行为特性如下：
         * 输出详细的Bugly SDK的Log；
         * 每一条Crash都会被立即上报；
         * 自定义日志将会在Logcat中输出。
         * 建议在测试阶段建议设置成true，发布时设置为false。
         */
//        CrashReport.initCrashReport(getApplicationContext(),APP_ID, IS_DEBUG);

//        CrashReport.testJavaCrash();
        NxCarServiceKit.init(this);
        ApiKit.setContext(getApplicationContext());
        connection.connect(getApplicationContext());
        connection.startMonitor();
//        CrashHandler.getInstance(this);
//        BuglyUtils.initBugly();
    }

    @Override
    public void onConnected(IRemote iRemote, ICallback iCallback) throws RemoteException {

    }

    @Override
    public void onUpdate(Bundle bundle) {

    }

    @Override
    public boolean isUpdateOnUIThread() {
        return false;
    }
}
