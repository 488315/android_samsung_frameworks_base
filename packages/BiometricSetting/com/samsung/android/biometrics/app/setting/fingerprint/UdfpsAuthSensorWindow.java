package com.samsung.android.biometrics.app.setting.fingerprint;

import android.app.SemStatusBarManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.samsung.android.biometrics.app.setting.DisplayStateManager$Injector$$ExternalSyntheticOutline0;
import com.samsung.android.biometrics.app.setting.Utils;

/* loaded from: classes.dex */
public class UdfpsAuthSensorWindow extends UdfpsSensorWindow {

    @VisibleForTesting
    BroadcastReceiver mBroadCastReceiver;

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsSensorWindow, com.samsung.android.biometrics.app.setting.SysUiWindow
    public final void addView() {
        super.addView();
        if (this.mBroadCastReceiver != null) {
            return;
        }
        this.mBroadCastReceiver = new BroadcastReceiver() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.UdfpsAuthSensorWindow.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                UdfpsWindowCallback udfpsWindowCallback;
                String action = intent.getAction();
                if ("android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(action)) {
                    String stringExtra = intent.getStringExtra("reason");
                    Log.i("BSS_UdfpsAuthSensorWindow", "ACTION: CLOSE_SYSTEM_DIALOG : reason = " + stringExtra);
                    if (!"recentapps".equals(stringExtra) || (udfpsWindowCallback = UdfpsAuthSensorWindow.this.mCallback) == null) {
                        return;
                    }
                    udfpsWindowCallback.onUserCancel(4);
                    return;
                }
                if ("com.samsung.systemui.statusbar.EXPANDED".equals(action)) {
                    SemStatusBarManager semStatusBarManager = (SemStatusBarManager) UdfpsAuthSensorWindow.this.getContext().getSystemService(SemStatusBarManager.class);
                    if (semStatusBarManager == null || !semStatusBarManager.isPanelExpanded()) {
                        return;
                    }
                    Log.i("BSS_UdfpsAuthSensorWindow", "Cancel auth because of PanelExpanded");
                    UdfpsWindowCallback udfpsWindowCallback2 = UdfpsAuthSensorWindow.this.mCallback;
                    if (udfpsWindowCallback2 != null) {
                        udfpsWindowCallback2.onUserCancel(1);
                        return;
                    }
                    return;
                }
                if ("com.sec.android.intent.action.AIR_BUTTON".equals(action) && Utils.Config.FEATURE_SUPPORT_SPEN && intent.getBooleanExtra("isShow", false)) {
                    Log.i("BSS_UdfpsAuthSensorWindow", "Cancel auth because of AirCommand");
                    UdfpsWindowCallback udfpsWindowCallback3 = UdfpsAuthSensorWindow.this.mCallback;
                    if (udfpsWindowCallback3 != null) {
                        udfpsWindowCallback3.onUserCancel(1);
                    }
                }
            }
        };
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
            intentFilter.addAction("com.samsung.systemui.statusbar.EXPANDED");
            if (Utils.Config.FEATURE_SUPPORT_SPEN) {
                intentFilter.addAction("com.sec.android.intent.action.AIR_BUTTON");
            }
            getContext().registerReceiverAsUser(this.mBroadCastReceiver, UserHandle.ALL, intentFilter, null, this.f22mH);
        } catch (Exception e) {
            DisplayStateManager$Injector$$ExternalSyntheticOutline0.m9m(e, new StringBuilder("registerBroadcastReceiver: "), "BSS_UdfpsAuthSensorWindow");
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsSensorWindow, com.samsung.android.biometrics.app.setting.SysUiWindow
    public void removeView() {
        super.removeView();
        if (this.mBroadCastReceiver != null) {
            try {
                getContext().unregisterReceiver(this.mBroadCastReceiver);
                this.mBroadCastReceiver = null;
            } catch (Exception e) {
                DisplayStateManager$Injector$$ExternalSyntheticOutline0.m9m(e, new StringBuilder("unregisterBroadcastReceiver: "), "BSS_UdfpsAuthSensorWindow");
            }
        }
    }
}
