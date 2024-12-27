package com.android.server.recoverysystem.hal;

import android.hardware.boot.IBootControl;
import android.hardware.boot.V1_2.IBootControl$Proxy;
import android.os.HwParcel;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class BootControlHIDL implements IBootControl {
    public final IBootControl$Proxy v1_2_hal;
    public final android.hardware.boot.V1_0.IBootControl v1_hal;

    public BootControlHIDL(
            android.hardware.boot.V1_0.IBootControl iBootControl,
            android.hardware.boot.V1_1.IBootControl iBootControl2,
            IBootControl$Proxy iBootControl$Proxy) {
        this.v1_hal = iBootControl;
        this.v1_2_hal = iBootControl$Proxy;
        if (iBootControl == null) {
            throw new RemoteException("Failed to find V1.0 BootControl HIDL");
        }
        if (iBootControl$Proxy != null) {
            Slog.i("BootControlHIDL", "V1.2 version of BootControl HIDL HAL available, using V1.2");
        } else if (iBootControl2 != null) {
            Slog.i("BootControlHIDL", "V1.1 version of BootControl HIDL HAL available, using V1.1");
        } else {
            Slog.i("BootControlHIDL", "V1.0 version of BootControl HIDL HAL available, using V1.0");
        }
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return null;
    }

    @Override // android.hardware.boot.IBootControl
    public final int getActiveBootSlot() {
        IBootControl$Proxy iBootControl$Proxy = this.v1_2_hal;
        if (iBootControl$Proxy == null) {
            throw new RemoteException("getActiveBootSlot() requires V1.2 BootControl HAL");
        }
        iBootControl$Proxy.getClass();
        HwParcel hwParcel = new HwParcel();
        hwParcel.writeInterfaceToken("android.hardware.boot@1.2::IBootControl");
        HwParcel hwParcel2 = new HwParcel();
        try {
            iBootControl$Proxy.mRemote.transact(11, hwParcel, hwParcel2, 0);
            hwParcel2.verifySuccess();
            hwParcel.releaseTemporaryStorage();
            return hwParcel2.readInt32();
        } finally {
            hwParcel2.release();
        }
    }

    @Override // android.hardware.boot.IBootControl
    public final int getCurrentSlot() {
        return this.v1_hal.getCurrentSlot();
    }
}
