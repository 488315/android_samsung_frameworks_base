package com.android.server.health;

import android.hardware.health.V2_0.IHealth;
import android.hardware.health.V2_0.Result;
import android.os.RemoteException;
import android.os.Trace;
import android.util.Slog;

import com.android.server.BatteryService$$ExternalSyntheticLambda11;

import vendor.samsung.hardware.health.V2_0.ISehHealthInfoCallback$Stub;

public final class HealthHalCallbackHidl extends ISehHealthInfoCallback$Stub
        implements HealthServiceWrapperHidl.Callback {
    public BatteryService$$ExternalSyntheticLambda11 mCallback;

    public static void traceEnd() {
        Trace.traceEnd(524288L);
    }

    public final void onRegistration(IHealth iHealth, IHealth iHealth2) {
        int registerCallback;
        if (iHealth2 == null) {
            return;
        }
        Trace.traceBegin(524288L, "HealthUnregisterCallback");
        try {
            if (iHealth != null) {
                try {
                    int unregisterCallback = iHealth.unregisterCallback(this);
                    if (unregisterCallback != 0) {
                        Slog.w(
                                "HealthHalCallbackHidl",
                                "health: cannot unregister previous callback: "
                                        + Result.toString(unregisterCallback));
                    }
                } catch (RemoteException e) {
                    Slog.w(
                            "HealthHalCallbackHidl",
                            "health: cannot unregister previous callback (transaction error): "
                                    + e.getMessage());
                }
            }
            traceEnd();
            Trace.traceBegin(524288L, "HealthRegisterCallback");
            try {
                try {
                    registerCallback = iHealth2.registerCallback(this);
                } catch (RemoteException e2) {
                    Slog.e(
                            "HealthHalCallbackHidl",
                            "health: cannot register callback (transaction error): "
                                    + e2.getMessage());
                }
                if (registerCallback == 0) {
                    iHealth2.update();
                    return;
                }
                Slog.w(
                        "HealthHalCallbackHidl",
                        "health: cannot register callback: " + Result.toString(registerCallback));
            } finally {
            }
        } finally {
        }
    }
}
