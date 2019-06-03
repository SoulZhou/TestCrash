package cn.yunovo.nxos.testcrash.ipc;

import android.os.RemoteException;

import cn.yunovo.nxos.testcrash.IMyAidlInterface;

public class MyBinder extends IMyAidlInterface.Stub {
    private static final MyBinder ourInstance = new MyBinder();

    public static MyBinder getInstance() {
        return ourInstance;
    }

    private MyBinder() {
    }

    @Override
    public String getName() throws RemoteException {
        return "test";
    }
}
