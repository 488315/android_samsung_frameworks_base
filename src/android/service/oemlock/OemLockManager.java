package android.service.oemlock;

import android.annotation.SystemApi;
import android.os.RemoteException;

@SystemApi
/* loaded from: classes3.dex */
public class OemLockManager {
    private IOemLockService mService;

    public OemLockManager(IOemLockService iOemLockService) {
        this.mService = iOemLockService;
    }

    public String getLockName() {
        try {
            return this.mService.getLockName();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setOemUnlockAllowedByCarrier(boolean z, byte[] bArr) {
        try {
            this.mService.setOemUnlockAllowedByCarrier(z, bArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isOemUnlockAllowedByCarrier() {
        try {
            return this.mService.isOemUnlockAllowedByCarrier();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setOemUnlockAllowedByUser(boolean z) {
        try {
            this.mService.setOemUnlockAllowedByUser(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isOemUnlockAllowedByUser() {
        try {
            return this.mService.isOemUnlockAllowedByUser();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isOemUnlockAllowed() {
        try {
            return this.mService.isOemUnlockAllowed();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isDeviceOemUnlocked() {
        try {
            return this.mService.isDeviceOemUnlocked();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
