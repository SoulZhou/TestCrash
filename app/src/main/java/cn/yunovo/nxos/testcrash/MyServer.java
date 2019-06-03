package cn.yunovo.nxos.testcrash;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import cn.yunovo.nxos.testcrash.ipc.MyBinder;

public class MyServer extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return MyBinder.getInstance();
    }
}
