package com.samsung.android.biometrics.app.setting.fingerprint;

import android.content.Intent;
import android.os.UserHandle;
import android.util.Log;

import com.samsung.android.biometrics.app.setting.Utils;
import com.samsung.android.hardware.secinputdev.SemInputDeviceManager;

public final /* synthetic */ class UdfpsIconVisibilityNotifier$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ UdfpsIconVisibilityNotifier f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ UdfpsIconVisibilityNotifier$$ExternalSyntheticLambda0(
            UdfpsIconVisibilityNotifier udfpsIconVisibilityNotifier, boolean z) {
        this.f$0 = udfpsIconVisibilityNotifier;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        UdfpsIconVisibilityNotifier udfpsIconVisibilityNotifier = this.f$0;
        boolean z = this.f$1;
        if (udfpsIconVisibilityNotifier.mLastTspVisibilityCommand == z) {
            return;
        }
        udfpsIconVisibilityNotifier.mLastTspVisibilityCommand = z;
        SemInputDeviceManager semInputDeviceManager =
                (SemInputDeviceManager)
                        udfpsIconVisibilityNotifier.mContext.getSystemService(
                                SemInputDeviceManager.class);
        if (semInputDeviceManager != null) {
            semInputDeviceManager.setFodIconVisible(z ? 1 : 0);
            if (Utils.DEBUG) {
                Log.d("BSS_UdfpsIconVisibilityNotifier", "sendUdfpsIconVisibilityToTsp: " + z);
            }
        }
        if (Utils.Config.FEATURE_SUPPORT_TASKBAR
                && Utils.getIntDb(udfpsIconVisibilityNotifier.mContext, "task_bar", false, 0)
                        == 1) {
            Intent intent =
                    new Intent("com.samsung.android.intent.action.UPDATE_UDFPS_ICON_VISIBILITY");
            intent.putExtra("VISIBLE", z);
            udfpsIconVisibilityNotifier.mContext.sendBroadcastAsUser(
                    intent, UserHandle.ALL, "android.permission.MANAGE_FINGERPRINT");
        }
    }
}
