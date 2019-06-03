package cn.yunovo.nxos.testcrash.sdk.ipc;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.IBinder;

import java.util.List;

import cn.yunovo.nxos.testcrash.IMyAidlInterface;
import cn.yunovo.nxos.testcrash.log.LogUtils;

public class MyAidlInterfaceKit {
    public static final String TAG = MyAidlInterfaceKit.class.getSimpleName();
    private static final MyAidlInterfaceKit ourInstance = new MyAidlInterfaceKit();
    private CallBackAidl callBackAidl;
    private Context mContext;
    private IMyAidlInterface mIMyAidlInterface;

    public static MyAidlInterfaceKit getInstance() {
        return ourInstance;
    }

    private MyAidlInterfaceKit() {
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
        LogUtils.d(TAG,"this.mContext.getPackageName() = "+this.mContext.getPackageName());
    }

    public void setCallBackAidl(CallBackAidl callBackAidl) {
        this.callBackAidl = callBackAidl;
    }

    public void startConnect(){
        bindMySevice();
    }

    public void unConnect(){

    }

    private void bindMySevice(){
        Intent intent = new Intent("cn.yunovo.nxos.test.testcrash");
        intent = createExplicitFromImplicitIntent(mContext,intent);
        if(mContext==null){
            new Throwable("mContext can not be null !");
        }
        mContext.bindService(intent,mServiceConnection,Service.BIND_AUTO_CREATE);
//        mServiceConnection.onBindingDied();
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtils.d(TAG,"name.getPackageName() = "+ name.getPackageName()+", name.getClassName() = "+name.getClassName());
            mIMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
            if(mIMyAidlInterface!=null&&callBackAidl!=null){
                callBackAidl.onConnect(mIMyAidlInterface);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            callBackAidl.unConnect();
        }
    };



    public interface CallBackAidl{
        void onConnect(IMyAidlInterface iMyAidlInterface);
        void unConnect();
    }


    public Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);

        // Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }

        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);

        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);

        // Set the component to be explicit
        explicitIntent.setComponent(component);

        return explicitIntent;
    }
}
