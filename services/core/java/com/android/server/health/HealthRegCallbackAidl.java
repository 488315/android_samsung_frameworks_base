package com.android.server.health;

import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.Trace;
import android.util.Slog;

import com.android.server.BatteryService$$ExternalSyntheticLambda11;

import vendor.samsung.hardware.health.ISehHealth;
import vendor.samsung.hardware.health.ISehHealthInfoCallback;
import vendor.samsung.hardware.health.SehHealthInfo;

public class HealthRegCallbackAidl {
    public final HalInfoCallback mHalInfoCallback = new HalInfoCallback();
    public final BatteryService$$ExternalSyntheticLambda11 mServiceInfoCallback;

    public final class HalInfoCallback extends Binder implements ISehHealthInfoCallback {
        public HalInfoCallback() {
            markVintfStability();
            attachInterface(this, ISehHealthInfoCallback.DESCRIPTOR);
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            String str = ISehHealthInfoCallback.DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 16777215) {
                parcel2.writeNoException();
                parcel2.writeInt(1);
                return true;
            }
            if (i == 16777214) {
                parcel2.writeNoException();
                parcel2.writeString("632113d2738773fc3ee67a40cf37aa99a91f8bac");
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            SehHealthInfo sehHealthInfo =
                    (SehHealthInfo) parcel.readTypedObject(SehHealthInfo.CREATOR);
            parcel.enforceNoDataAvail();
            HealthRegCallbackAidl.this.mServiceInfoCallback.update(sehHealthInfo);
            return true;
        }
    }

    public HealthRegCallbackAidl(
            BatteryService$$ExternalSyntheticLambda11 batteryService$$ExternalSyntheticLambda11) {
        this.mServiceInfoCallback = batteryService$$ExternalSyntheticLambda11;
    }

    public static void unregisterCallback(ISehHealth iSehHealth, HalInfoCallback halInfoCallback) {
        Slog.d("HealthRegCallbackAidl", "unregisterCallback: oldService: " + iSehHealth);
        if (iSehHealth == null) {
            return;
        }
        try {
            ((ISehHealth.Stub.Proxy) iSehHealth).unregisterCallback(halInfoCallback);
        } catch (RemoteException e) {
            Slog.w(
                    "HealthRegCallbackAidl",
                    "health: cannot unregister previous callback (transaction error): "
                            + e.getMessage());
        }
    }

    public void onRegistration(ISehHealth iSehHealth, ISehHealth iSehHealth2) {
        Slog.d(
                "HealthRegCallbackAidl",
                "onRegistration: oldService: " + iSehHealth + " newService: " + iSehHealth2);
        if (this.mServiceInfoCallback == null) {
            return;
        }
        Trace.traceBegin(524288L, "HealthUnregisterCallbackAidl");
        HalInfoCallback halInfoCallback = this.mHalInfoCallback;
        try {
            unregisterCallback(iSehHealth, halInfoCallback);
            Trace.traceEnd(524288L);
            Trace.traceBegin(524288L, "HealthRegisterCallbackAidl");
            try {
                Slog.d("HealthRegCallbackAidl", "registerCallback: newService: " + iSehHealth2);
                try {
                    ISehHealth.Stub.Proxy proxy = (ISehHealth.Stub.Proxy) iSehHealth2;
                    proxy.registerCallback(halInfoCallback);
                    try {
                        proxy.update();
                    } catch (RemoteException e) {
                        Slog.e(
                                "HealthRegCallbackAidl",
                                "health: cannot update after registering health info callback",
                                e);
                    }
                } catch (RemoteException e2) {
                    Slog.e(
                            "HealthRegCallbackAidl",
                            "health: cannot register callback, framework may cease to receive"
                                + " updates on health / battery info!",
                            e2);
                }
            } finally {
            }
        } finally {
        }
    }
}
