package com.android.server.health;

import android.os.RemoteException;
import android.os.Trace;
import android.util.Slog;
import vendor.samsung.hardware.health.ISehHealth;
import vendor.samsung.hardware.health.ISehHealthInfoCallback;
import vendor.samsung.hardware.health.SehHealthInfo;

/* loaded from: classes2.dex */
public class HealthRegCallbackAidl {
    public final ISehHealthInfoCallback mHalInfoCallback = new HalInfoCallback();
    public final HealthInfoCallback mServiceInfoCallback;

    public HealthRegCallbackAidl(HealthInfoCallback healthInfoCallback) {
        this.mServiceInfoCallback = healthInfoCallback;
    }

    public void onRegistration(ISehHealth iSehHealth, ISehHealth iSehHealth2) {
        Slog.d("HealthRegCallbackAidl", "onRegistration: oldService: " + iSehHealth + " newService: " + iSehHealth2);
        if (this.mServiceInfoCallback == null) {
            return;
        }
        Trace.traceBegin(524288L, "HealthUnregisterCallbackAidl");
        try {
            unregisterCallback(iSehHealth, this.mHalInfoCallback);
            Trace.traceEnd(524288L);
            Trace.traceBegin(524288L, "HealthRegisterCallbackAidl");
            try {
                registerCallback(iSehHealth2, this.mHalInfoCallback);
            } finally {
            }
        } finally {
        }
    }

    public static void unregisterCallback(ISehHealth iSehHealth, ISehHealthInfoCallback iSehHealthInfoCallback) {
        Slog.d("HealthRegCallbackAidl", "unregisterCallback: oldService: " + iSehHealth);
        if (iSehHealth == null) {
            return;
        }
        try {
            iSehHealth.unregisterCallback(iSehHealthInfoCallback);
        } catch (RemoteException e) {
            Slog.w("HealthRegCallbackAidl", "health: cannot unregister previous callback (transaction error): " + e.getMessage());
        }
    }

    public static void registerCallback(ISehHealth iSehHealth, ISehHealthInfoCallback iSehHealthInfoCallback) {
        Slog.d("HealthRegCallbackAidl", "registerCallback: newService: " + iSehHealth);
        try {
            iSehHealth.registerCallback(iSehHealthInfoCallback);
            try {
                iSehHealth.update();
            } catch (RemoteException e) {
                Slog.e("HealthRegCallbackAidl", "health: cannot update after registering health info callback", e);
            }
        } catch (RemoteException e2) {
            Slog.e("HealthRegCallbackAidl", "health: cannot register callback, framework may cease to receive updates on health / battery info!", e2);
        }
    }

    public class HalInfoCallback extends ISehHealthInfoCallback.Stub {
        @Override // vendor.samsung.hardware.health.ISehHealthInfoCallback
        public String getInterfaceHash() {
            return "632113d2738773fc3ee67a40cf37aa99a91f8bac";
        }

        @Override // vendor.samsung.hardware.health.ISehHealthInfoCallback
        public int getInterfaceVersion() {
            return 1;
        }

        public HalInfoCallback() {
        }

        @Override // vendor.samsung.hardware.health.ISehHealthInfoCallback
        public void healthInfoChanged(SehHealthInfo sehHealthInfo) {
            HealthRegCallbackAidl.this.mServiceInfoCallback.update(sehHealthInfo);
        }
    }
}
