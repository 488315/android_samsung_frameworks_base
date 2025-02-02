package com.samsung.android.biometrics.app.setting.fingerprint;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.UserHandle;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.samsung.android.biometrics.app.setting.DisplayStateManager$Injector$$ExternalSyntheticOutline0;
import com.samsung.android.biometrics.app.setting.Utils;
import java.util.function.BooleanSupplier;

/* loaded from: classes.dex */
public final class HbmLockStateMonitor {

    @VisibleForTesting
    static final String ACTION_FINGERPRINT_TUI = "com.samsung.android.intent.action.FINGERPRINT_TUI";

    @VisibleForTesting
    BroadcastReceiver mBroadcastReceiver;
    private final Callback mCallback;
    private final Context mContext;

    /* renamed from: mH */
    private final Handler f27mH;
    private final Injector mInjector;
    private final BooleanSupplier mIsAlphaMaskSaMode;
    private final BooleanSupplier mIsScreenOn;

    @VisibleForTesting
    final int INVISIBLE_LOCK_TUI = 2;

    @VisibleForTesting
    final int INVISIBLE_LOCK_TOP_TASK_CAMERA = 4;
    private int mHbmLockState = 0;

    public interface Callback {
    }

    @VisibleForTesting
    public static class Injector {
        private final String[] mPackageListWithNoMask = {"com.sec.android.app.camera", "com.sec.android.gallery3d"};

        public final boolean isPackageNeedToHideMaskTopOfTask() {
            for (String str : this.mPackageListWithNoMask) {
                if (Utils.isForegroundTask(str)) {
                    return true;
                }
            }
            return false;
        }
    }

    @VisibleForTesting
    HbmLockStateMonitor(Context context, Handler handler, Callback callback, BooleanSupplier booleanSupplier, BooleanSupplier booleanSupplier2, Injector injector) {
        this.mContext = context;
        this.mInjector = injector;
        this.f27mH = handler;
        this.mCallback = callback;
        this.mIsAlphaMaskSaMode = booleanSupplier;
        this.mIsScreenOn = booleanSupplier2;
    }

    private void observe(boolean z) {
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
                DisplayStateManager$Injector$$ExternalSyntheticOutline0.m9m(e, new StringBuilder("observe: "), "BSS_HbmLockStateMonitor");
                return;
            }
        }
        if (this.mBroadcastReceiver != null) {
            return;
        }
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.HbmLockStateMonitor.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (HbmLockStateMonitor.ACTION_FINGERPRINT_TUI.equals(action)) {
                    HbmLockStateMonitor hbmLockStateMonitor = HbmLockStateMonitor.this;
                    hbmLockStateMonitor.getClass();
                    String stringExtra = intent.getStringExtra("STATE");
                    Log.i("BSS_HbmLockStateMonitor", "onReceive: [" + action + "], " + stringExtra);
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
            this.mContext.registerReceiverAsUser(this.mBroadcastReceiver, UserHandle.ALL, intentFilter, "android.permission.MANAGE_FINGERPRINT", this.f27mH);
        } catch (Exception e2) {
            DisplayStateManager$Injector$$ExternalSyntheticOutline0.m9m(e2, new StringBuilder("observe: "), "BSS_HbmLockStateMonitor");
        }
    }

    @VisibleForTesting
    int getHbmLockStateForTest() {
        return this.mHbmLockState;
    }

    public final void handleOnTaskStackListener() {
        if (this.mIsScreenOn.getAsBoolean()) {
            if (!this.mInjector.isPackageNeedToHideMaskTopOfTask()) {
                setHbmLockState(false, 4);
            } else if (this.mIsAlphaMaskSaMode.getAsBoolean()) {
                setHbmLockState(true, 4);
            }
        }
    }

    public final boolean isLock() {
        return this.mHbmLockState != 0;
    }

    @VisibleForTesting
    void setHbmLockState(boolean z, int i) {
        Log.i("BSS_HbmLockStateMonitor", "setHbmLockState: " + z + ", " + i + ", " + this.mHbmLockState);
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

    public final void start() {
        observe(true);
    }

    public final void stop() {
        observe(false);
    }
}
