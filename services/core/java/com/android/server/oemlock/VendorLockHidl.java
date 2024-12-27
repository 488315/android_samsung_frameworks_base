package com.android.server.oemlock;

import android.hardware.oemlock.V1_0.IOemLock$Proxy;
import android.os.RemoteException;
import android.util.Slog;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class VendorLockHidl extends OemLock {
    public IOemLock$Proxy mOemLock;

    public static IOemLock$Proxy getOemLockHalService() {
        try {
            return IOemLock$Proxy.getService();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (NoSuchElementException unused) {
            Slog.i("OemLock", "OemLock Hidl HAL not present on device");
            return null;
        }
    }

    @Override // com.android.server.oemlock.OemLock
    public final String getLockName() {
        String[] strArr = new String[1];
        Integer[] numArr = new Integer[1];
        try {
            this.mOemLock.getName(new VendorLockHidl$$ExternalSyntheticLambda0(numArr, strArr));
            int intValue = numArr[0].intValue();
            if (intValue == 0) {
                return strArr[0];
            }
            if (intValue != 1) {
                Slog.e("OemLock", "Unknown return value indicates code is out of sync with HAL");
                return null;
            }
            Slog.e("OemLock", "Failed to get OEM lock name.");
            return null;
        } catch (RemoteException e) {
            Slog.e("OemLock", "Failed to get name from HAL", e);
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // com.android.server.oemlock.OemLock
    public final boolean isOemUnlockAllowedByCarrier() {
        Boolean[] boolArr = new Boolean[1];
        Integer[] numArr = new Integer[1];
        try {
            this.mOemLock.isOemUnlockAllowedByCarrier(new VendorLockHidl$$ExternalSyntheticLambda0(numArr, boolArr));
            int intValue = numArr[0].intValue();
            if (intValue == 0) {
                return boolArr[0].booleanValue();
            }
            if (intValue != 1) {
                Slog.e("OemLock", "Unknown return value indicates code is out of sync with HAL");
            }
            throw new RuntimeException("Failed to get carrier OEM unlock state");
        } catch (RemoteException e) {
            Slog.e("OemLock", "Failed to get carrier state from HAL");
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // com.android.server.oemlock.OemLock
    public final boolean isOemUnlockAllowedByDevice() {
        Boolean[] boolArr = new Boolean[1];
        Integer[] numArr = new Integer[1];
        try {
            this.mOemLock.isOemUnlockAllowedByDevice(new VendorLockHidl$$ExternalSyntheticLambda0(numArr, boolArr));
            int intValue = numArr[0].intValue();
            if (intValue == 0) {
                return boolArr[0].booleanValue();
            }
            if (intValue != 1) {
                Slog.e("OemLock", "Unknown return value indicates code is out of sync with HAL");
            }
            throw new RuntimeException("Failed to get device OEM unlock state");
        } catch (RemoteException e) {
            Slog.e("OemLock", "Failed to get devie state from HAL");
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // com.android.server.oemlock.OemLock
    public final void setOemUnlockAllowedByCarrier(boolean z, byte[] bArr) {
        ArrayList arrayList;
        try {
            if (bArr == null) {
                arrayList = new ArrayList();
            } else {
                ArrayList arrayList2 = new ArrayList(bArr.length);
                for (byte b : bArr) {
                    arrayList2.add(Byte.valueOf(b));
                }
                arrayList = arrayList2;
            }
            int oemUnlockAllowedByCarrier = this.mOemLock.setOemUnlockAllowedByCarrier(arrayList, z);
            if (oemUnlockAllowedByCarrier == 0) {
                Slog.i("OemLock", "Updated carrier allows OEM lock state to: " + z);
                return;
            }
            if (oemUnlockAllowedByCarrier != 1) {
                if (oemUnlockAllowedByCarrier == 2) {
                    if (!arrayList.isEmpty()) {
                        throw new SecurityException("Invalid signature used in attempt to carrier unlock");
                    }
                    throw new IllegalArgumentException("Signature required for carrier unlock");
                }
                Slog.e("OemLock", "Unknown return value indicates code is out of sync with HAL");
            }
            throw new RuntimeException("Failed to set carrier OEM unlock state");
        } catch (RemoteException e) {
            Slog.e("OemLock", "Failed to set carrier state with HAL", e);
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // com.android.server.oemlock.OemLock
    public final void setOemUnlockAllowedByDevice(boolean z) {
        try {
            int oemUnlockAllowedByDevice = this.mOemLock.setOemUnlockAllowedByDevice(z);
            if (oemUnlockAllowedByDevice != 0) {
                if (oemUnlockAllowedByDevice != 1) {
                    Slog.e("OemLock", "Unknown return value indicates code is out of sync with HAL");
                }
                throw new RuntimeException("Failed to set device OEM unlock state");
            }
            Slog.i("OemLock", "Updated device allows OEM lock state to: " + z);
        } catch (RemoteException e) {
            Slog.e("OemLock", "Failed to set device state with HAL", e);
            throw e.rethrowFromSystemServer();
        }
    }
}
