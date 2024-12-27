package com.samsung.android.biometrics.app.setting.fingerprint;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.UserHandle;
import android.util.Log;

import com.samsung.android.biometrics.app.setting.FocusableWindow$$ExternalSyntheticOutline0;

import java.util.function.BooleanSupplier;

public final class HbmLockStateMonitor {
    static final String ACTION_FINGERPRINT_TUI =
            "com.samsung.android.intent.action.FINGERPRINT_TUI";
    BroadcastReceiver mBroadcastReceiver;
    public final Callback mCallback;
    public final Context mContext;
    public final Handler mH;
    public final Injector mInjector;
    public final BooleanSupplier mIsAlphaMaskSaMode;
    public final BooleanSupplier mIsScreenOn;
    final int INVISIBLE_LOCK_TUI = 2;
    final int INVISIBLE_LOCK_TOP_TASK_CAMERA = 4;
    public int mHbmLockState = 0;

    public interface Callback {}

    public class Injector {
        public final String[] mPackageListWithNoMask = {
            "com.sec.android.app.camera", "com.sec.android.gallery3d"
        };
    }

    public HbmLockStateMonitor(
            Context context,
            Handler handler,
            Callback callback,
            BooleanSupplier booleanSupplier,
            BooleanSupplier booleanSupplier2,
            Injector injector) {
        this.mContext = context;
        this.mInjector = injector;
        this.mH = handler;
        this.mCallback = callback;
        this.mIsAlphaMaskSaMode = booleanSupplier;
        this.mIsScreenOn = booleanSupplier2;
    }

    public int getHbmLockStateForTest() {
        return this.mHbmLockState;
    }

    public final void observe(boolean z) {
        if (!z) {
            BroadcastReceiver broadcastReceiver = this.mBroadcastReceiver;
            if (broadcastReceiver == null) {
                return;
            }
            try {
                this.mContext.unregisterReceiver(broadcastReceiver);
                this.mBroadcastReceiver = null;
                return;
            } catch (Exception e) {
                FocusableWindow$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("observe: "), "BSS_HbmLockStateMonitor");
                return;
            }
        }
        if (this.mBroadcastReceiver != null) {
            return;
        }
        this.mBroadcastReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.samsung.android.biometrics.app.setting.fingerprint.HbmLockStateMonitor.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        String action = intent.getAction();
                        if (HbmLockStateMonitor.ACTION_FINGERPRINT_TUI.equals(action)) {
                            HbmLockStateMonitor hbmLockStateMonitor = HbmLockStateMonitor.this;
                            hbmLockStateMonitor.getClass();
                            String stringExtra = intent.getStringExtra("STATE");
                            Log.i(
                                    "BSS_HbmLockStateMonitor",
                                    "onReceive: [" + action + "], " + stringExtra);
                            if (stringExtra != null) {
                                if (stringExtra.equals("START")) {
                                    hbmLockStateMonitor.setHbmLockState(true, 2);
                                } else if (stringExtra.equals("STOP")) {
                                    hbmLockStateMonitor.setHbmLockState(false, 2);
                                }
                            }
                        }
                    }
                };
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ACTION_FINGERPRINT_TUI);
            this.mContext.registerReceiverAsUser(
                    this.mBroadcastReceiver,
                    UserHandle.ALL,
                    intentFilter,
                    "android.permission.MANAGE_FINGERPRINT",
                    this.mH);
        } catch (Exception e2) {
            FocusableWindow$$ExternalSyntheticOutline0.m(
                    e2, new StringBuilder("observe: "), "BSS_HbmLockStateMonitor");
        }
    }

    public void setHbmLockState(boolean z, int i) {
        Log.i(
                "BSS_HbmLockStateMonitor",
                "setHbmLockState: " + z + ", " + i + ", " + this.mHbmLockState);
        Callback callback = this.mCallback;
        if (z) {
            if (this.mHbmLockState == 0) {
                ((OpticalController) callback).onHbmLockState(true);
            }
            this.mHbmLockState |= i;
            return;
        }
        int i2 = this.mHbmLockState;
        if (i2 != 0) {
            int i3 = i2 & (~i);
            this.mHbmLockState = i3;
            if (i3 == 0) {
                ((OpticalController) callback).onHbmLockState(false);
            }
        }
    }
}
