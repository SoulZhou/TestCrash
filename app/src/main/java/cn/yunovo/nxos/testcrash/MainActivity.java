package cn.yunovo.nxos.testcrash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.tencent.bugly.crashreport.BuglyLog;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.File;

import cn.yunovo.car.api.ApiCanbus;
import cn.yunovo.car.api.ApiMain;
import cn.yunovo.nxos.carservice.sdk.utils.LogUtil;

public class MainActivity extends Activity {


    Button cllick1;
    Button cllick2;
    Button cllick3;
    Button cllick4;
    Button cllick5;
    Button cllick6;
    Button cllick7;
    Button cllick8;
    Button cllick9;
    Button cllick10;
    Button cllick11;
    Button cllick12;
    Button cllick13;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        cllick1 = findViewById(R.id.click1);
        cllick2 = findViewById(R.id.click2);
        cllick3 = findViewById(R.id.click3);
        cllick4 = findViewById(R.id.click4);
        cllick5 = findViewById(R.id.click5);
        cllick6 = findViewById(R.id.click6);
        cllick7 = findViewById(R.id.click7);
        cllick8 = findViewById(R.id.click8);
        cllick9 = findViewById(R.id.click9);
        cllick10 = findViewById(R.id.click10);
        cllick11 = findViewById(R.id.click11);
        cllick12 = findViewById(R.id.click12);
        cllick13 = findViewById(R.id.click13);
        cllick1.setOnClickListener(clickListener);
        cllick2.setOnClickListener(clickListener);
        cllick3.setOnClickListener(clickListener);
        cllick4.setOnClickListener(clickListener);
        cllick5.setOnClickListener(clickListener);
        cllick6.setOnClickListener(clickListener);
        cllick7.setOnClickListener(clickListener);
        cllick8.setOnClickListener(clickListener);
        cllick9.setOnClickListener(clickListener);
        cllick10.setOnClickListener(clickListener);
        cllick11.setOnClickListener(clickListener);
        cllick12.setOnClickListener(clickListener);
        cllick13.setOnClickListener(clickListener);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.click1:
                    CrashReport.testJavaCrash();
                    break;
                case R.id.click3:
                    try {
                        throw new NoSuchMethodException("no such method exception");
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.click2:
                    throw new NullPointerException("null pointer exception");
//                    break;
                case R.id.click4:
                    click4Error();
                    break;
                case R.id.click5:
                    startActivity("");
                    break;
                case R.id.click6:
                    startActivity("dvr");
                    break;
                case R.id.click7:
                    startActivity("back");
                    break;
                case R.id.click8:
                    startActivity("lock");
                    break;
                case R.id.click9:
                    startActivity("photo");
                    break;
                case R.id.click11:
                    startActivity("close");
                    break;
                case R.id.click10:
                    startUpdateMcu();
                    break;
                case R.id.click12:
                    startActivityFragment("simChange");
                    break;
                case R.id.click13:
                    startTestAct();
                    break;
            }
        }
    };

    private void click4Error(){
        int[] datas = new int[]{1,2,3,4};
        for (int i=0;i<5;i++){
            BuglyLog.d("CrashReportInfo", "click4Error CrashReportInfo : "+datas[i]);
        }
    }

    private void startActivity(String extra){
        Intent intent = new Intent("cn.yunovo.nxos.intent.action.MEDIAUI");
        if(!TextUtils.isEmpty(extra)){
            intent.putExtra("type",extra);
        }
        startActivity(intent);
    }

    private void startTestAct(){
        Intent intent = new Intent(this,TestAct.class);
        startActivity(intent);
    }

    private void startActivityFragment(String extra){
        try {
            Intent intent = new Intent("com.car.settings");
            intent.putExtra("fragment",extra);
            intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (Exception e) {

        }
    }


    public void startUpdateMcu() {
        Bundle bundle = new Bundle();
        bundle.putInt("type", 0);
        bundle.putString("filePath", "/mnt/sdcard/S32YUP.BIN");
        ApiMain.cmd(ApiMain.CMD_MCU_UPGRADE,bundle);
    }

}
