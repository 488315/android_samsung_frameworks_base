package android.service.persistentdata;

import android.annotation.SystemApi;
import android.os.RemoteException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public class PersistentDataBlockManager {

    @SystemApi
    public static final int FLASH_LOCK_LOCKED = 1;

    @SystemApi
    public static final int FLASH_LOCK_UNKNOWN = -1;

    @SystemApi
    public static final int FLASH_LOCK_UNLOCKED = 0;
    private static final String TAG = "PersistentDataBlockManager";
    private IPersistentDataBlockService sService;

    @SystemApi
    @Retention(RetentionPolicy.SOURCE)
    public @interface FlashLockState {
    }

    public PersistentDataBlockManager(IPersistentDataBlockService iPersistentDataBlockService) {
        this.sService = iPersistentDataBlockService;
    }

    @SystemApi
    public int write(byte[] bArr) {
        try {
            return this.sService.write(bArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public byte[] read() {
        try {
            return this.sService.read();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int getDataBlockSize() {
        try {
            return this.sService.getDataBlockSize();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public long getMaximumDataBlockSize() {
        try {
            return this.sService.getMaximumDataBlockSize();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void wipe() {
        try {
            this.sService.wipe();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    @Deprecated
    public void setOemUnlockEnabled(boolean z) {
        try {
            this.sService.setOemUnlockEnabled(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    @Deprecated
    public boolean getOemUnlockEnabled() {
        try {
            return this.sService.getOemUnlockEnabled();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int getFlashLockState() {
        try {
            return this.sService.getFlashLockState();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public String getPersistentDataPackageName() {
        try {
            return this.sService.getPersistentDataPackageName();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isFactoryResetProtectionActive() {
        try {
            return this.sService.isFactoryResetProtectionActive();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean deactivateFactoryResetProtection(byte[] bArr) {
        try {
            return this.sService.deactivateFactoryResetProtection(bArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean setFactoryResetProtectionSecret(byte[] bArr) {
        try {
            return this.sService.setFactoryResetProtectionSecret(bArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
