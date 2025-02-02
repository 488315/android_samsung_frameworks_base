package com.samsung.android.biometrics.app.setting.fingerprint;

import android.app.SemStatusBarManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Message;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.android.internal.annotations.VisibleForTesting;
import com.samsung.android.biometrics.app.setting.AuthenticationConsumer;
import com.samsung.android.biometrics.app.setting.BackgroundThread;
import com.samsung.android.biometrics.app.setting.ClientCallback;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.Utils;
import com.samsung.android.biometrics.app.setting.Utils$$ExternalSyntheticLambda0;
import com.samsung.android.view.SemWindowManager;
import java.util.Iterator;

/* loaded from: classes.dex */
public class UdfpsAuthClient extends UdfpsClient implements AuthenticationConsumer {
    private UdfpsAuthGuideWindow mGuideWindow;
    private boolean mIsBroadcastSent;

    public static void $r8$lambda$_FGZXte5qDYQQqL1sAjc_gSfxZ8(UdfpsAuthClient udfpsAuthClient, int i) {
        boolean z = false;
        if (i != 0) {
            udfpsAuthClient.getClass();
            if ((Utils.Config.FEATURE_SUPPORT_TASKBAR && Utils.getIntDb(udfpsAuthClient.getContext(), "task_bar", false, 0) == 1) && udfpsAuthClient.mIsBroadcastSent) {
                Intent intent = new Intent("com.samsung.android.intent.action.UPDATE_UDFPS_ICON_VISIBILITY");
                intent.putExtra("VISIBLE", false);
                udfpsAuthClient.getContext().sendBroadcastAsUser(intent, UserHandle.ALL, "android.permission.MANAGE_FINGERPRINT");
                return;
            }
            return;
        }
        udfpsAuthClient.getClass();
        if (Utils.Config.FEATURE_SUPPORT_TASKBAR && Utils.getIntDb(udfpsAuthClient.getContext(), "task_bar", false, 0) == 1) {
            z = true;
        }
        if (z) {
            udfpsAuthClient.mIsBroadcastSent = true;
            Intent intent2 = new Intent("com.samsung.android.intent.action.UPDATE_UDFPS_ICON_VISIBILITY");
            intent2.putExtra("VISIBLE", true);
            udfpsAuthClient.getContext().sendBroadcastAsUser(intent2, UserHandle.ALL, "android.permission.MANAGE_FINGERPRINT");
        }
    }

    @VisibleForTesting
    protected UdfpsAuthGuideWindow createUdfpsAuthGuideWindow() {
        Object obj;
        Object obj2;
        PackageManager packageManager;
        Object obj3 = null;
        try {
            packageManager = this.mContext.getPackageManager();
        } catch (PackageManager.NameNotFoundException e) {
            e = e;
            obj = null;
        }
        if (packageManager == null) {
            obj2 = null;
            return new UdfpsAuthGuideWindow(this.mContext, this.mDisplayStateManager, this, new Pair(obj3, obj2), this.mSensorInfo);
        }
        obj = packageManager.getApplicationLabel(packageManager.getApplicationInfo(getPackageName(), 8192));
        try {
            obj3 = packageManager.getApplicationIcon(getPackageName());
        } catch (PackageManager.NameNotFoundException e2) {
            e = e2;
            e.printStackTrace();
            obj2 = obj3;
            obj3 = obj;
            return new UdfpsAuthGuideWindow(this.mContext, this.mDisplayStateManager, this, new Pair(obj3, obj2), this.mSensorInfo);
        }
        obj2 = obj3;
        obj3 = obj;
        return new UdfpsAuthGuideWindow(this.mContext, this.mDisplayStateManager, this, new Pair(obj3, obj2), this.mSensorInfo);
    }

    @VisibleForTesting
    protected UdfpsAuthSensorWindow createUdfpsAuthSensorWindow() {
        return new UdfpsAuthSensorWindow(this.mContext, this, this.mSensorInfo, this.mDisplayStateManager);
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiClient
    public int getUiType() {
        return 1;
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiClient, android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        Log.i("BSS_UdfpsAuthClient", Utils.getLogFormat(message));
        int i = message.what;
        if (i != 3) {
            if (i != 4) {
                return true;
            }
            stop();
            return true;
        }
        UdfpsAuthGuideWindow udfpsAuthGuideWindow = this.mGuideWindow;
        if (udfpsAuthGuideWindow == null) {
            return true;
        }
        udfpsAuthGuideWindow.resetMessage();
        return true;
    }

    public void handleTspBlock(boolean z) {
        if (this.mGuideWindow != null) {
            this.mHandler.removeMessages(3);
            if (!z) {
                this.mHandler.sendEmptyMessage(3);
                return;
            }
            String acquiredString = FingerprintManager.getAcquiredString(this.mContext, 6, 1004);
            if (TextUtils.isEmpty(acquiredString)) {
                return;
            }
            this.mGuideWindow.showHelpMessage(0, acquiredString);
            this.mHandler.sendEmptyMessageDelayed(3, 3000L);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.AuthenticationConsumer
    public void onAuthenticationError(int i, int i2, String str) {
        this.mHandler.removeMessages(3);
        UdfpsAuthGuideWindow udfpsAuthGuideWindow = this.mGuideWindow;
        if (udfpsAuthGuideWindow != null) {
            udfpsAuthGuideWindow.showHelpMessage(-1, FingerprintManager.getErrorString(this.mContext, i, i2));
            this.mHandler.sendEmptyMessageDelayed(4, 2000L);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.AuthenticationConsumer
    public void onAuthenticationFailed(String str) {
        this.mHandler.removeMessages(3);
        UdfpsAuthGuideWindow udfpsAuthGuideWindow = this.mGuideWindow;
        if (udfpsAuthGuideWindow != null) {
            udfpsAuthGuideWindow.showHelpMessage(-1, getContext().getString(17042722));
            this.mHandler.sendEmptyMessageDelayed(3, 3000L);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.AuthenticationConsumer
    public void onAuthenticationHelp(int i, String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.mHandler.removeMessages(3);
        UdfpsAuthGuideWindow udfpsAuthGuideWindow = this.mGuideWindow;
        if (udfpsAuthGuideWindow != null) {
            udfpsAuthGuideWindow.showHelpMessage(i, str);
            this.mHandler.sendEmptyMessageDelayed(3, 3000L);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsClient, com.samsung.android.biometrics.app.setting.fingerprint.UdfpsWindowCallback
    public final void onSensorIconVisibilityChanged(final int i) {
        super.onSensorIconVisibilityChanged(i);
        BackgroundThread backgroundThread = BackgroundThread.get();
        Runnable runnable = new Runnable() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.UdfpsAuthClient$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                UdfpsAuthClient.$r8$lambda$_FGZXte5qDYQQqL1sAjc_gSfxZ8(UdfpsAuthClient.this, i);
            }
        };
        backgroundThread.getClass();
        BackgroundThread.post(runnable);
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiClient
    public void prepareWindows() {
        UdfpsAuthSensorWindow createUdfpsAuthSensorWindow = createUdfpsAuthSensorWindow();
        createUdfpsAuthSensorWindow.initFromBaseWindow(this.mBaseSensorWindow);
        createUdfpsAuthSensorWindow.showSensorIcon();
        this.mWindows.add(createUdfpsAuthSensorWindow);
        UdfpsAuthGuideWindow createUdfpsAuthGuideWindow = createUdfpsAuthGuideWindow();
        this.mGuideWindow = createUdfpsAuthGuideWindow;
        createUdfpsAuthGuideWindow.init();
        this.mWindows.add(this.mGuideWindow);
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiClient
    public void start(ClientCallback clientCallback) {
        boolean z;
        super.start(clientCallback);
        BackgroundThread backgroundThread = BackgroundThread.get();
        Runnable runnable = new Runnable() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.UdfpsAuthClient$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                SemStatusBarManager semStatusBarManager = (SemStatusBarManager) UdfpsAuthClient.this.getContext().getSystemService(SemStatusBarManager.class);
                if (semStatusBarManager == null || !semStatusBarManager.isPanelExpanded()) {
                    return;
                }
                semStatusBarManager.collapsePanels();
            }
        };
        backgroundThread.getClass();
        BackgroundThread.post(runnable);
        if (Utils.Config.FEATURE_SUPPORT_SPEN) {
            boolean z2 = Utils.DEBUG;
            Iterator it = SemWindowManager.getInstance().getVisibleWindowInfoList().iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                }
                SemWindowManager.VisibleWindowInfo visibleWindowInfo = (SemWindowManager.VisibleWindowInfo) it.next();
                if ("com.samsung.android.service.aircommand".equals(visibleWindowInfo.packageName) && "Air_Cmd(Launcher)".contentEquals(visibleWindowInfo.name)) {
                    Log.i("BSS_Utils", "com.samsung.android.service.aircommand".concat(" window is shown"));
                    z = true;
                    break;
                }
            }
            if (z) {
                onUserCancel(1);
                return;
            }
        }
        if (this.mIsKeyguard || !Utils.Config.FEATURE_SUPPORT_DESKTOP_MODE) {
            return;
        }
        Context context = getContext();
        String string = getContext().getString(R.string.fingerprint_dex_toast);
        boolean z3 = Utils.DEBUG;
        BackgroundThread backgroundThread2 = BackgroundThread.get();
        Utils$$ExternalSyntheticLambda0 utils$$ExternalSyntheticLambda0 = new Utils$$ExternalSyntheticLambda0(context, string);
        backgroundThread2.getClass();
        BackgroundThread.post(utils$$ExternalSyntheticLambda0);
    }

    public void onCaptureComplete() {
    }

    public void onCaptureStart() {
    }
}
