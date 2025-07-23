package com.sec.android.iaft;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import com.samsung.android.core.AppJumpBlockTool;
import com.samsung.android.feature.SemFloatingFeature;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/* loaded from: classes6.dex */
class IAFDServiceImpl {
    private static final String TAG = "IAFDServiceImpl";
    private SmLib_IafdSmAPIManager apiSMManager;
    private Context mContext;
    private ServiceHandler mHandler;
    private IAFDSocketFdServer mIAFDGetHotfixDataService = null;
    private IAFDRepair mIAFDRepair;
    private Looper mLooper;

    IAFDServiceImpl(Context context, IAFDDiagnosis iAFDDiagnosis) {
        this.mContext = context;
        init();
    }

    private void init() {
        HandlerThread handlerThread = new HandlerThread("MessageIAFDThread", 10);
        handlerThread.start();
        this.mLooper = handlerThread.getLooper();
        this.mHandler = new ServiceHandler(this.mLooper);
        this.mIAFDRepair = new IAFDRepair();
        this.apiSMManager = new SmLib_IafdSmAPIManager(this.mContext);
        this.mIAFDGetHotfixDataService = new IAFDSocketFdServer(this.mContext);
    }

    void IAFDServiceHandlerMessage(Message message) {
        this.mHandler.handleMessage(message);
    }

    private int getDualUserIdAndIsNoSettingsProvidersOfDual() {
        int i = 0;
        try {
            Process exec = Runtime.getRuntime().exec(new String[]{"/system/bin/sh", "-c", "dumpsys package com.android.providers.settings | grep User"});
            exec.waitFor();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
            int i2 = 1000;
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                i2--;
                if (readLine.startsWith("    User ") && !readLine.startsWith("    User 0") && readLine.contains("installed=false")) {
                    int parseInt = Integer.parseInt(readLine.substring(9, readLine.indexOf(58)));
                    if (parseInt >= 0) {
                        i = parseInt;
                    }
                }
            }
            bufferedReader.close();
        } catch (Exception unused) {
        }
        return i;
    }

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                Log.d(IAFDServiceImpl.TAG, "CMD_TYPE_START");
                Bundle data = message.getData();
                if (data.getBoolean("CheckUpdateFlag", true)) {
                    message.what = 5;
                } else {
                    data.putBoolean("hasUpdate", false);
                    message.what = 6;
                }
                IAFDServiceImpl.this.mHandler.sendMessage(message);
                return;
            }
            if (i == 2) {
                Bundle data2 = message.getData();
                boolean repairHandle = IAFDServiceImpl.this.mIAFDRepair.repairHandle(IAFDServiceImpl.this.mContext, data2);
                if (data2.getString("repairTrigAPP", "vocApp").equals("vocApp") && data2.getInt("OneKeyRepairMode") == 1) {
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("voc://view/faq?app=iafd"));
                    intent.putExtras(data2);
                    intent.putExtra("repairResult", repairHandle);
                    intent.addFlags(268435456);
                    IAFDServiceImpl.this.mContext.startActivity(intent);
                    return;
                }
                return;
            }
            if (i == 5) {
                Bundle data3 = message.getData();
                Log.d(IAFDServiceImpl.TAG, "CMD_TYPE_GETUPDATESTATUS");
                IAFDServiceImpl.this.checkUpdate(data3);
            } else if (i == 6) {
                Log.d(IAFDServiceImpl.TAG, "CMD_TYPE_GETUPDATESTATUS_RESULT");
                IAFDServiceImpl.this.IAFDstartApp(message.getData(), true);
            } else {
                if (i != 9) {
                    return;
                }
                IAFDServiceImpl.this.mIAFDGetHotfixDataService.getDataFromClient(message.getData().getString("hotfixdata"));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean IAFDstartApp(Bundle bundle, boolean z) {
        boolean z2;
        if (z) {
            try {
                z2 = bundle.getBoolean("hasUpdate", false);
            } catch (Exception unused) {
                Log.d(TAG, "ShowAppErrorUiExt fail, skip");
                return false;
            }
        } else {
            z2 = false;
        }
        int i = bundle.getInt("repairType");
        if (bundle.getInt("type") == 35) {
            int i2 = bundle.getInt("dualUserId");
            int dualUserIdAndIsNoSettingsProvidersOfDual = getDualUserIdAndIsNoSettingsProvidersOfDual();
            if (dualUserIdAndIsNoSettingsProvidersOfDual > 0) {
                bundle.putInt("dualUserId", dualUserIdAndIsNoSettingsProvidersOfDual);
            } else if (i2 != 95 && i2 != 96) {
                i = 0;
            }
        }
        if (!z2 && i != 0) {
            String string = bundle.getString("repairTrigAPP", "vocApp");
            if (string.equals("vocApp")) {
                reportErrorDataToServer(bundle);
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("voc://view/faq?app=iafd"));
                intent.putExtras(bundle);
                intent.addFlags(268435456);
                this.mContext.startActivity(intent);
                Log.d(TAG, "ShowAppErrorUiExt start voc app");
                return true;
            }
            showSystemAppDiaglog(bundle, string);
            return true;
        }
        Intent intent2 = new Intent("com.samsung.android.sm.ACTION_START_THIRD_APP_ERROR_DIALOG");
        intent2.setPackage(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SMARTMANAGER_CONFIG_PACKAGE_NAME", "com.samsung.android.lool"));
        intent2.putExtras(bundle);
        this.mContext.startService(intent2);
        Log.d(TAG, "ShowAppErrorUiExt start sm app");
        return true;
    }

    private void showSystemAppDiaglog(final Bundle bundle, final String str) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
            builder.setTitle("System hint").setMessage("Happened exception in the running application, you can try to resolve it with the button of [Try to resolve]").setCancelable(true).setPositiveButton("Try to resolve", new DialogInterface.OnClickListener() { // from class: com.sec.android.iaft.IAFDServiceImpl.2
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (str.equals("SmartMApp")) {
                        Intent intent = new Intent("com.samsung.android.sm.ACTION_START_THIRD_APP_ERROR_DIALOG");
                        intent.setPackage("com.samsung.android.sm_cn");
                        intent.putExtras(bundle);
                        IAFDServiceImpl.this.mContext.startService(intent);
                        return;
                    }
                    Message message = new Message();
                    message.setData(bundle);
                    message.what = 2;
                    IAFDServiceImpl.this.mHandler.sendMessage(message);
                }
            }).setNegativeButton(AppJumpBlockTool.RESULT_CANCEL, new DialogInterface.OnClickListener(this) { // from class: com.sec.android.iaft.IAFDServiceImpl.1
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog create = builder.create();
            Window window = create.getWindow();
            window.setBackgroundDrawable(new ColorDrawable(0));
            window.setGravity(80);
            window.setType(2008);
            window.setType(2038);
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setColor(-1);
            gradientDrawable.setCornerRadius(50.0f);
            gradientDrawable.setStroke(5, -1);
            window.setBackgroundDrawable(gradientDrawable);
            create.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkUpdate(final Bundle bundle) {
        try {
            this.apiSMManager.checkUpdate(bundle.getString(SmLib_IafdConstant.KEY_PACKAGE_NAME), bundle.getLong(SmLib_IafdConstant.KEY_VERSION_CODE), new SmLib_CheckUpdateCallback() { // from class: com.sec.android.iaft.IAFDServiceImpl.3
                @Override // com.sec.android.iaft.SmLib_CheckUpdateCallback
                public void onResult(int i, long j, String str, String str2) {
                    Message message = new Message();
                    bundle.putBoolean("hasUpdate", i == 2);
                    message.setData(bundle);
                    message.what = 6;
                    IAFDServiceImpl.this.mHandler.sendMessage(message);
                }
            });
        } catch (Exception unused) {
            Message message = new Message();
            bundle.putBoolean("hasUpdate", false);
            message.setData(bundle);
            message.what = 6;
            this.mHandler.sendMessage(message);
        }
    }

    public void reportErrorDataToServer(Bundle bundle) {
        try {
            this.apiSMManager.reportErrorDataToServer(bundle.getString(SmLib_IafdConstant.KEY_PACKAGE_NAME), bundle.getInt(SmLib_IafdConstant.KEY_USER_ID), bundle.getInt("type"), bundle.getString(SmLib_IafdConstant.KEY_ERROR_STACK), bundle.getString("component"), bundle.getLong(SmLib_IafdConstant.KEY_VERSION_CODE), bundle.getString("appName"), bundle.getString("versionName"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
