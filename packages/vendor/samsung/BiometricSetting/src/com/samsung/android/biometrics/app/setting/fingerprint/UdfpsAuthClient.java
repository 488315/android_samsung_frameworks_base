package com.samsung.android.biometrics.app.setting.fingerprint;

import android.app.SemStatusBarManager;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.samsung.android.biometrics.app.setting.AuthenticationConsumer;
import com.samsung.android.biometrics.app.setting.BackgroundThread;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.SysUiManager;
import com.samsung.android.biometrics.app.setting.Utils;
import com.samsung.android.biometrics.app.setting.Utils$$ExternalSyntheticLambda0;
import com.samsung.android.view.SemWindowManager;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
public abstract class UdfpsAuthClient extends UdfpsClient implements AuthenticationConsumer {
    public UdfpsAuthSensorWindow createUdfpsAuthSensorWindow() {
        return new UdfpsAuthSensorWindow(
                this.mContext, this, this.mSensorInfo, this.mDisplayStateManager);
    }

    public Handler getBgHandler() {
        BackgroundThread.get().getClass();
        return BackgroundThread.sHandler;
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiClient
    public void start(SysUiManager.AnonymousClass1 anonymousClass1) {
        super.start(anonymousClass1);
        if (Utils.Config.FEATURE_SUPPORT_SPEN) {
            boolean z = Utils.DEBUG;
            for (SemWindowManager.VisibleWindowInfo visibleWindowInfo :
                    SemWindowManager.getInstance().getVisibleWindowInfoList()) {
                if ("com.samsung.android.service.aircommand".equals(visibleWindowInfo.packageName)
                        && "Air_Cmd(Launcher)".contentEquals(visibleWindowInfo.name)) {
                    Log.i(
                            "BSS_Utils",
                            "com.samsung.android.service.aircommand".concat(" window is shown"));
                    onUserCancel(1);
                    return;
                }
            }
        }
        if (this.mIsKeyguard) {
            return;
        }
        getBgHandler()
                .post(
                        new Runnable() { // from class:
                                         // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsAuthClient$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                SemStatusBarManager semStatusBarManager =
                                        (SemStatusBarManager)
                                                UdfpsAuthClient.this.mContext.getSystemService(
                                                        SemStatusBarManager.class);
                                if (semStatusBarManager == null
                                        || !semStatusBarManager.isPanelExpanded()) {
                                    return;
                                }
                                semStatusBarManager.collapsePanels();
                            }
                        });
        if (Utils.Config.FEATURE_SUPPORT_DESKTOP_MODE) {
            Context context = this.mContext;
            String string = context.getString(R.string.fingerprint_dex_toast);
            boolean z2 = Utils.DEBUG;
            BackgroundThread backgroundThread = BackgroundThread.get();
            Utils$$ExternalSyntheticLambda0 utils$$ExternalSyntheticLambda0 =
                    new Utils$$ExternalSyntheticLambda0(context, string);
            backgroundThread.getClass();
            BackgroundThread.sHandler.post(utils$$ExternalSyntheticLambda0);
        }
    }

    public void onCaptureComplete() {}

    public void onCaptureStart() {}

    @Override // com.samsung.android.biometrics.app.setting.AuthenticationConsumer
    public void onAuthenticationFailed(String str) {}

    @Override // com.samsung.android.biometrics.app.setting.AuthenticationConsumer
    public void onAuthenticationHelp(int i, String str) {}

    @Override // com.samsung.android.biometrics.app.setting.AuthenticationConsumer
    public void onAuthenticationError(int i, int i2, String str) {}
}
