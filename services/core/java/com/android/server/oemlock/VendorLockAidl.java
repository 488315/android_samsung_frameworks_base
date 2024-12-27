package com.android.server.oemlock;

import android.hardware.oemlock.IOemLock;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class VendorLockAidl extends OemLock {
    public IOemLock mOemLock;

    public static IOemLock getOemLockHalService() {
        StringBuilder sb = new StringBuilder();
        String str = IOemLock.DESCRIPTOR;
        sb.append(str);
        sb.append("/default");
        IBinder waitForDeclaredService = ServiceManager.waitForDeclaredService(sb.toString());
        int i = IOemLock.Stub.$r8$clinit;
        if (waitForDeclaredService == null) {
            return null;
        }
        IInterface queryLocalInterface = waitForDeclaredService.queryLocalInterface(str);
        if (queryLocalInterface != null && (queryLocalInterface instanceof IOemLock)) {
            return (IOemLock) queryLocalInterface;
        }
        IOemLock.Stub.Proxy proxy = new IOemLock.Stub.Proxy();
        proxy.mRemote = waitForDeclaredService;
        return proxy;
    }

    @Override // com.android.server.oemlock.OemLock
    public final String getLockName() {
        try {
            return ((IOemLock.Stub.Proxy) this.mOemLock).getName();
        } catch (RemoteException e) {
            Slog.e("OemLock", "Failed to get name from HAL", e);
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // com.android.server.oemlock.OemLock
    public final boolean isOemUnlockAllowedByCarrier() {
        try {
            return ((IOemLock.Stub.Proxy) this.mOemLock).isOemUnlockAllowedByCarrier();
        } catch (RemoteException e) {
            Slog.e("OemLock", "Failed to get carrier state from HAL");
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // com.android.server.oemlock.OemLock
    public final boolean isOemUnlockAllowedByDevice() {
        try {
            return ((IOemLock.Stub.Proxy) this.mOemLock).isOemUnlockAllowedByDevice();
        } catch (RemoteException e) {
            Slog.e("OemLock", "Failed to get devie state from HAL");
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // com.android.server.oemlock.OemLock
    public final void setOemUnlockAllowedByCarrier(boolean z, byte[] bArr) {
        int oemUnlockAllowedByCarrier;
        IOemLock iOemLock = this.mOemLock;
        try {
            if (bArr == null) {
                oemUnlockAllowedByCarrier =
                        ((IOemLock.Stub.Proxy) iOemLock)
                                .setOemUnlockAllowedByCarrier(z, new byte[0]);
            } else {
                oemUnlockAllowedByCarrier =
                        ((IOemLock.Stub.Proxy) iOemLock).setOemUnlockAllowedByCarrier(z, bArr);
            }
            if (oemUnlockAllowedByCarrier == 0) {
                Slog.i("OemLock", "Updated carrier allows OEM lock state to: " + z);
            } else {
                if (oemUnlockAllowedByCarrier != 1) {
                    if (oemUnlockAllowedByCarrier == 2) {
                        if (bArr != null) {
                            throw new SecurityException(
                                    "Invalid signature used in attempt to carrier unlock");
                        }
                        throw new IllegalArgumentException("Signature required for carrier unlock");
                    }
                    Slog.e(
                            "OemLock",
                            "Unknown return value indicates code is out of sync with HAL");
                }
                throw new RuntimeException("Failed to set carrier OEM unlock state");
            }
        } catch (RemoteException e) {
            Slog.e("OemLock", "Failed to set carrier state with HAL", e);
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // com.android.server.oemlock.OemLock
    public final void setOemUnlockAllowedByDevice(boolean z) {
        try {
            ((IOemLock.Stub.Proxy) this.mOemLock).setOemUnlockAllowedByDevice(z);
        } catch (RemoteException e) {
            Slog.e("OemLock", "Failed to set device state with HAL", e);
            throw e.rethrowFromSystemServer();
        }
    }
}
