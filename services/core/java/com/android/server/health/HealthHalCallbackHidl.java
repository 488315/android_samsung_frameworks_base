package com.android.server.health;

import android.hardware.health.V2_0.HealthInfo;
import android.hardware.health.V2_0.IHealth;
import android.hardware.health.V2_0.Result;
import android.os.RemoteException;
import android.os.Trace;
import android.util.Slog;
import com.android.server.health.HealthServiceWrapperHidl;
import vendor.samsung.hardware.health.Translate;
import vendor.samsung.hardware.health.V2_0.ISehHealthInfoCallback;
import vendor.samsung.hardware.health.V2_0.SehHealthInfo;

/* loaded from: classes2.dex */
public class HealthHalCallbackHidl extends ISehHealthInfoCallback.Stub implements HealthServiceWrapperHidl.Callback {
    public static final String TAG = HealthHalCallbackHidl.class.getSimpleName();
    public HealthInfoCallback mCallback;

    public static void traceBegin(String str) {
        Trace.traceBegin(524288L, str);
    }

    public static void traceEnd() {
        Trace.traceEnd(524288L);
    }

    public HealthHalCallbackHidl(HealthInfoCallback healthInfoCallback) {
        this.mCallback = healthInfoCallback;
    }

    @Override // android.hardware.health.V2_0.IHealthInfoCallback
    public void healthInfoChanged(HealthInfo healthInfo) {
        SehHealthInfo sehHealthInfo = new SehHealthInfo();
        android.hardware.health.V2_1.HealthInfo healthInfo2 = sehHealthInfo.legacy;
        healthInfo2.legacy = healthInfo;
        healthInfo2.batteryCapacityLevel = -1;
        healthInfo2.batteryChargeTimeToFullNowSeconds = -1L;
        this.mCallback.update(Translate.h2saTranslate(sehHealthInfo));
    }

    @Override // android.hardware.health.V2_1.IHealthInfoCallback
    public void healthInfoChanged_2_1(android.hardware.health.V2_1.HealthInfo healthInfo) {
        SehHealthInfo sehHealthInfo = new SehHealthInfo();
        sehHealthInfo.legacy = healthInfo;
        this.mCallback.update(Translate.h2saTranslate(sehHealthInfo));
    }

    @Override // vendor.samsung.hardware.health.V2_0.ISehHealthInfoCallback
    public void sehHealthInfoChanged_2_1(SehHealthInfo sehHealthInfo) {
        this.mCallback.update(Translate.h2saTranslate(sehHealthInfo));
    }

    @Override // com.android.server.health.HealthServiceWrapperHidl.Callback
    public void onRegistration(IHealth iHealth, IHealth iHealth2, String str) {
        int registerCallback;
        if (iHealth2 == null) {
            return;
        }
        traceBegin("HealthUnregisterCallback");
        if (iHealth != null) {
            try {
                try {
                    int unregisterCallback = iHealth.unregisterCallback(this);
                    if (unregisterCallback != 0) {
                        Slog.w(TAG, "health: cannot unregister previous callback: " + Result.toString(unregisterCallback));
                    }
                } catch (RemoteException e) {
                    Slog.w(TAG, "health: cannot unregister previous callback (transaction error): " + e.getMessage());
                }
            } finally {
            }
        }
        traceEnd();
        traceBegin("HealthRegisterCallback");
        try {
            try {
                registerCallback = iHealth2.registerCallback(this);
            } catch (RemoteException e2) {
                Slog.e(TAG, "health: cannot register callback (transaction error): " + e2.getMessage());
            }
            if (registerCallback != 0) {
                Slog.w(TAG, "health: cannot register callback: " + Result.toString(registerCallback));
                return;
            }
            iHealth2.update();
        } finally {
        }
    }
}
