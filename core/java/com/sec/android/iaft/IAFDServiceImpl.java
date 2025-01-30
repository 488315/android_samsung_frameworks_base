package com.sec.android.iaft;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.p009os.Bundle;
import android.p009os.Handler;
import android.p009os.HandlerThread;
import android.p009os.Looper;
import android.p009os.Message;
import android.util.Log;
import android.view.Window;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.p043sm.iafdlib.CheckUpdateCallback;
import com.samsung.android.p043sm.iafdlib.IafdConstant;
import com.samsung.android.p043sm.iafdlib.IafdSmAPIManager;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/* loaded from: classes6.dex */
class IAFDServiceImpl {
    private static final String TAG = "IAFDServiceImpl";
    private IafdSmAPIManager apiSMManager;
    private Context mContext;
    private ServiceHandler mHandler;
    private IAFDSocketFdServer mIAFDGetHotfixDataService = null;
    private IAFDRepair mIAFDRepair;
    private Looper mLooper;

    IAFDServiceImpl(Context context, IAFDDiagnosis miafd) {
        this.mContext = context;
        init();
    }

    private void init() {
        HandlerThread mHandlerThread = new HandlerThread("MessageIAFDThread", 10);
        mHandlerThread.start();
        this.mLooper = mHandlerThread.getLooper();
        this.mHandler = new ServiceHandler(this.mLooper);
        this.mIAFDRepair = new IAFDRepair();
        this.apiSMManager = new IafdSmAPIManager(this.mContext);
        this.mIAFDGetHotfixDataService = new IAFDSocketFdServer(this.mContext);
    }

    void IAFDServiceHandlerMessage(Message msg) {
        this.mHandler.handleMessage(msg);
    }

    private int getDualUserIdAndIsNoSettingsProvidersOfDual() {
        int maxcnt = 1000;
        int dualuserid = 0;
        String[] cmdArr = {"/system/bin/sh", "-c", "dumpsys package com.android.providers.settings | grep User"};
        try {
            Process p = Runtime.getRuntime().exec(cmdArr);
            p.waitFor();
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while (true) {
                String line = stdInput.readLine();
                if (line == null) {
                    break;
                }
                maxcnt--;
                if (line.startsWith("    User ") && !line.startsWith("    User 0") && line.contains("installed=false")) {
                    int posEnd = line.indexOf(58);
                    String strTmp = line.substring(9, posEnd);
                    dualuserid = Integer.parseInt(strTmp);
                    if (dualuserid < 0) {
                        dualuserid = 0;
                    }
                }
            }
            stdInput.close();
        } catch (Exception e) {
        }
        return dualuserid;
    }

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override // android.p009os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Log.m94d(IAFDServiceImpl.TAG, "CMD_TYPE_START");
                    Bundle bundle = msg.getData();
                    if (bundle.getBoolean("CheckUpdateFlag", true)) {
                        msg.what = 5;
                    } else {
                        bundle.putBoolean("hasUpdate", false);
                        msg.what = 6;
                    }
                    IAFDServiceImpl.this.mHandler.sendMessage(msg);
                    break;
                case 2:
                    Bundle bundle2 = msg.getData();
                    boolean repairResult = IAFDServiceImpl.this.mIAFDRepair.repairHandle(IAFDServiceImpl.this.mContext, bundle2);
                    String trigApp = bundle2.getString("repairTrigAPP", "vocApp");
                    if (trigApp.equals("vocApp")) {
                        int OneKeyRepairMode = bundle2.getInt("OneKeyRepairMode");
                        if (OneKeyRepairMode == 1) {
                            Uri uri = Uri.parse("voc://view/faq?app=iafd");
                            Intent intent = new Intent("android.intent.action.VIEW", uri);
                            intent.putExtras(bundle2);
                            intent.putExtra("repairResult", repairResult);
                            intent.addFlags(268435456);
                            IAFDServiceImpl.this.mContext.startActivity(intent);
                            break;
                        }
                    }
                    break;
                case 5:
                    Bundle bundle3 = msg.getData();
                    Log.m94d(IAFDServiceImpl.TAG, "CMD_TYPE_GETUPDATESTATUS");
                    IAFDServiceImpl.this.checkUpdate(bundle3);
                    break;
                case 6:
                    Log.m94d(IAFDServiceImpl.TAG, "CMD_TYPE_GETUPDATESTATUS_RESULT");
                    IAFDServiceImpl.this.IAFDstartApp(msg.getData(), true);
                    break;
                case 9:
                    IAFDServiceImpl.this.mIAFDGetHotfixDataService.getDataFromClient(msg.getData().getString("hotfixdata"));
                    break;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean IAFDstartApp(Bundle bundle, boolean hasGetUpdateResult) {
        boolean hasUpdated = false;
        if (hasGetUpdateResult) {
            try {
                hasUpdated = bundle.getBoolean("hasUpdate", false);
            } catch (Exception e) {
                Log.m94d(TAG, "ShowAppErrorUiExt fail, skip");
                return false;
            }
        }
        int repairType = bundle.getInt("repairType");
        int expType = bundle.getInt("type");
        if (expType == 35) {
            int dualuidCur = bundle.getInt("dualUserId");
            int dualuidSP = getDualUserIdAndIsNoSettingsProvidersOfDual();
            if (dualuidSP > 0) {
                bundle.putInt("dualUserId", dualuidSP);
            } else if (dualuidCur != 95 && dualuidCur != 96) {
                repairType = 0;
            }
        }
        if (!hasUpdated && repairType != 0) {
            String trigApp = bundle.getString("repairTrigAPP", "vocApp");
            if (trigApp.equals("vocApp")) {
                reportErrorDataToServer(bundle);
                Uri uri = Uri.parse("voc://view/faq?app=iafd");
                Intent intent = new Intent("android.intent.action.VIEW", uri);
                intent.putExtras(bundle);
                intent.addFlags(268435456);
                this.mContext.startActivity(intent);
                Log.m94d(TAG, "ShowAppErrorUiExt start voc app");
                return true;
            }
            showSystemAppDiaglog(bundle, trigApp);
            return true;
        }
        Intent intent2 = new Intent("com.samsung.android.sm.ACTION_START_THIRD_APP_ERROR_DIALOG");
        intent2.setPackage(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SMARTMANAGER_CONFIG_PACKAGE_NAME", "com.samsung.android.lool"));
        intent2.putExtras(bundle);
        this.mContext.startService(intent2);
        Log.m94d(TAG, "ShowAppErrorUiExt start sm app");
        return true;
    }

    private void showSystemAppDiaglog(final Bundle bundle, final String trigApp) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
            builder.setTitle("System hint").setMessage("Happened exception in the running application, you can try to resolve it with the button of [Try to resolve]").setCancelable(true).setPositiveButton("Try to resolve", new DialogInterface.OnClickListener() { // from class: com.sec.android.iaft.IAFDServiceImpl.2
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                    if (trigApp.equals("SmartMApp")) {
                        Intent intent = new Intent("com.samsung.android.sm.ACTION_START_THIRD_APP_ERROR_DIALOG");
                        intent.setPackage("com.samsung.android.sm_cn");
                        intent.putExtras(bundle);
                        IAFDServiceImpl.this.mContext.startService(intent);
                        return;
                    }
                    Message msg = new Message();
                    msg.setData(bundle);
                    msg.what = 2;
                    IAFDServiceImpl.this.mHandler.sendMessage(msg);
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() { // from class: com.sec.android.iaft.IAFDServiceImpl.1
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = builder.create();
            Window window = alert.getWindow();
            window.setBackgroundDrawable(new ColorDrawable(0));
            window.setGravity(80);
            window.setType(2008);
            window.setType(2038);
            GradientDrawable backGround = new GradientDrawable();
            backGround.setColor(-1);
            backGround.setCornerRadius(50.0f);
            backGround.setStroke(5, -1);
            window.setBackgroundDrawable(backGround);
            alert.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkUpdate(final Bundle bundle) {
        try {
            this.apiSMManager.checkUpdate(bundle.getString(IafdConstant.KEY_PACKAGE_NAME), bundle.getLong(IafdConstant.KEY_VERSION_CODE), new CheckUpdateCallback() { // from class: com.sec.android.iaft.IAFDServiceImpl.3
                @Override // com.samsung.android.p043sm.iafdlib.CheckUpdateCallback
                public void onResult(int resultCode, long versionCode, String versionName, String pkgName) {
                    Message msg = new Message();
                    bundle.putBoolean("hasUpdate", resultCode == 2);
                    msg.setData(bundle);
                    msg.what = 6;
                    IAFDServiceImpl.this.mHandler.sendMessage(msg);
                }
            });
        } catch (Exception e) {
            Message msg = new Message();
            bundle.putBoolean("hasUpdate", false);
            msg.setData(bundle);
            msg.what = 6;
            this.mHandler.sendMessage(msg);
        }
    }

    public void reportErrorDataToServer(Bundle bundle) {
        try {
            String pkgName = bundle.getString(IafdConstant.KEY_PACKAGE_NAME);
            int userId = bundle.getInt(IafdConstant.KEY_USER_ID);
            int errorType = bundle.getInt("type");
            String errorStack = bundle.getString(IafdConstant.KEY_ERROR_STACK);
            String errorComponent = bundle.getString("component");
            long versionCode = bundle.getLong(IafdConstant.KEY_VERSION_CODE);
            String appName = bundle.getString("appName");
            String versionName = bundle.getString("versionName");
            this.apiSMManager.reportErrorDataToServer(pkgName, userId, errorType, errorStack, errorComponent, versionCode, appName, versionName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
