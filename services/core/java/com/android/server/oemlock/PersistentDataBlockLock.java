package com.android.server.oemlock;

import android.content.Context;
import android.os.UserHandle;
import android.os.UserManager;
import android.service.persistentdata.PersistentDataBlockManager;
import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PersistentDataBlockLock extends OemLock {
    public Context mContext;

    @Override // com.android.server.oemlock.OemLock
    public final String getLockName() {
        return "";
    }

    @Override // com.android.server.oemlock.OemLock
    public final boolean isOemUnlockAllowedByCarrier() {
        return !UserManager.get(this.mContext).hasUserRestriction("no_oem_unlock", UserHandle.SYSTEM);
    }

    @Override // com.android.server.oemlock.OemLock
    public final boolean isOemUnlockAllowedByDevice() {
        PersistentDataBlockManager persistentDataBlockManager = (PersistentDataBlockManager) this.mContext.getSystemService("persistent_data_block");
        if (persistentDataBlockManager != null) {
            return persistentDataBlockManager.getOemUnlockEnabled();
        }
        Slog.w("OemLock", "PersistentDataBlock is not supported on this device");
        return false;
    }

    @Override // com.android.server.oemlock.OemLock
    public final void setOemUnlockAllowedByCarrier(boolean z, byte[] bArr) {
        if (bArr != null) {
            Slog.w("OemLock", "Signature provided but is not being used");
        }
        UserManager.get(this.mContext).setUserRestriction("no_oem_unlock", !z, UserHandle.SYSTEM);
        if (z) {
            return;
        }
        PersistentDataBlockManager persistentDataBlockManager = (PersistentDataBlockManager) this.mContext.getSystemService("persistent_data_block");
        if (persistentDataBlockManager == null) {
            Slog.w("OemLock", "PersistentDataBlock is not supported on this device");
        } else if (persistentDataBlockManager.getFlashLockState() != 0) {
            persistentDataBlockManager.setOemUnlockEnabled(false);
        }
    }

    @Override // com.android.server.oemlock.OemLock
    public final void setOemUnlockAllowedByDevice(boolean z) {
        PersistentDataBlockManager persistentDataBlockManager = (PersistentDataBlockManager) this.mContext.getSystemService("persistent_data_block");
        if (persistentDataBlockManager == null) {
            Slog.w("OemLock", "PersistentDataBlock is not supported on this device");
        } else {
            persistentDataBlockManager.setOemUnlockEnabled(z);
        }
    }
}
