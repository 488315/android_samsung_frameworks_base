package com.android.server;

import android.content.Context;
import android.gsi.AvbPublicKey;
import android.gsi.GsiProgress;
import android.gsi.IGsiService;
import android.gsi.IGsiServiceCallback;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.image.IDynamicSystemService;
import android.os.storage.StorageManager;
import android.os.storage.VolumeInfo;
import android.util.Slog;

import java.io.File;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DynamicSystemService extends IDynamicSystemService.Stub {
    public final Context mContext;
    public String mDsuSlot;
    public volatile IGsiService mGsiService;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class GsiServiceCallback extends IGsiServiceCallback.Stub {
        public int mResult = -1;

        @Override // android.gsi.IGsiServiceCallback
        public final synchronized void onResult(int i) {
            this.mResult = i;
            notify();
        }
    }

    public DynamicSystemService(Context context) {
        this.mContext = context;
    }

    public final boolean abort() {
        abort_enforcePermission();
        return getGsiService().cancelGsiInstall();
    }

    public final boolean closePartition() {
        closePartition_enforcePermission();
        if (getGsiService().closePartition() == 0) {
            return true;
        }
        Slog.i("DynamicSystemService", "Partition installation completes with error");
        return false;
    }

    public final int createPartition(String str, long j, boolean z) {
        createPartition_enforcePermission();
        int createPartition = getGsiService().createPartition(str, j, z);
        if (createPartition != 0) {
            Slog.i("DynamicSystemService", "Failed to create partition: " + str);
        }
        return createPartition;
    }

    public final boolean finishInstallation() {
        finishInstallation_enforcePermission();
        if (getGsiService().closeInstall() == 0) {
            return true;
        }
        Slog.i("DynamicSystemService", "Failed to finish installation");
        return false;
    }

    public final String getActiveDsuSlot() {
        getActiveDsuSlot_enforcePermission();
        if (this.mDsuSlot == null) {
            this.mDsuSlot = getGsiService().getActiveDsuSlot();
        }
        return this.mDsuSlot;
    }

    public final boolean getAvbPublicKey(AvbPublicKey avbPublicKey) {
        getAvbPublicKey_enforcePermission();
        try {
            return getGsiService().getAvbPublicKey(avbPublicKey) == 0;
        } catch (RemoteException e) {
            throw new RuntimeException(e.toString());
        }
    }

    public final IGsiService getGsiService() {
        return this.mGsiService != null
                ? this.mGsiService
                : IGsiService.Stub.asInterface(ServiceManager.waitForService("gsiservice"));
    }

    public final GsiProgress getInstallationProgress() {
        getInstallationProgress_enforcePermission();
        return getGsiService().getInstallProgress();
    }

    public final boolean isEnabled() {
        isEnabled_enforcePermission();
        return getGsiService().isGsiEnabled();
    }

    public final boolean isInUse() {
        return SystemProperties.getBoolean("ro.gsid.image_running", false);
    }

    public final boolean isInstalled() {
        boolean z = SystemProperties.getBoolean("gsid.image_installed", false);
        Slog.i("DynamicSystemService", "isInstalled(): " + z);
        return z;
    }

    public final boolean remove() {
        remove_enforcePermission();
        try {
            GsiServiceCallback gsiServiceCallback = new GsiServiceCallback();
            synchronized (gsiServiceCallback) {
                getGsiService().removeGsiAsync(gsiServiceCallback);
                gsiServiceCallback.wait(8192L);
            }
            return gsiServiceCallback.mResult == 0;
        } catch (InterruptedException unused) {
            Slog.e("DynamicSystemService", "remove() was interrupted");
            return false;
        }
    }

    public final boolean setAshmem(ParcelFileDescriptor parcelFileDescriptor, long j) {
        setAshmem_enforcePermission();
        try {
            return getGsiService().setGsiAshmem(parcelFileDescriptor, j);
        } catch (RemoteException e) {
            throw new RuntimeException(e.toString());
        }
    }

    public final boolean setEnable(boolean z, boolean z2) {
        setEnable_enforcePermission();
        IGsiService gsiService = getGsiService();
        if (!z) {
            return gsiService.disableGsi();
        }
        try {
            getActiveDsuSlot();
            GsiServiceCallback gsiServiceCallback = new GsiServiceCallback();
            synchronized (gsiServiceCallback) {
                gsiService.enableGsiAsync(z2, this.mDsuSlot, gsiServiceCallback);
                gsiServiceCallback.wait(8192L);
            }
            return gsiServiceCallback.mResult == 0;
        } catch (InterruptedException unused) {
            Slog.e("DynamicSystemService", "setEnable() was interrupted");
            return false;
        }
    }

    public final boolean startInstallation(String str) {
        startInstallation_enforcePermission();
        IGsiService gsiService = getGsiService();
        this.mGsiService = gsiService;
        String str2 = SystemProperties.get("os.aot.path");
        if (str2.isEmpty()) {
            int myUserId = UserHandle.myUserId();
            for (VolumeInfo volumeInfo :
                    ((StorageManager) this.mContext.getSystemService(StorageManager.class))
                            .getVolumes()) {
                if (volumeInfo.getType() == 0
                        && volumeInfo.isMountedWritable()
                        && "vfat".equalsIgnoreCase(volumeInfo.fsType)) {
                    long j = volumeInfo.getDisk().size >> 20;
                    StringBuilder sb = new StringBuilder();
                    sb.append(volumeInfo.getPath());
                    sb.append(": ");
                    sb.append(j);
                    DeviceIdleController$$ExternalSyntheticOutline0.m(
                            sb, " MB", "DynamicSystemService");
                    if (j < 30720) {
                        Slog.i(
                                "DynamicSystemService",
                                volumeInfo.getPath() + ": insufficient storage");
                    } else {
                        File internalPathForUser = volumeInfo.getInternalPathForUser(myUserId);
                        if (internalPathForUser != null) {
                            str2 = new File(internalPathForUser, str).getPath();
                        }
                    }
                }
            }
            if (str2.isEmpty()) {
                str2 =
                        ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                "/data/gsi/dsu/", str);
            }
            Slog.i("DynamicSystemService", "startInstallation -> " + str2);
        }
        this.mDsuSlot = str;
        if (gsiService.openInstall(str2) == 0) {
            return true;
        }
        Slog.i("DynamicSystemService", "Failed to open " + str2);
        return false;
    }

    public final boolean submitFromAshmem(long j) {
        submitFromAshmem_enforcePermission();
        try {
            return getGsiService().commitGsiChunkFromAshmem(j);
        } catch (RemoteException e) {
            throw new RuntimeException(e.toString());
        }
    }

    public final long suggestScratchSize() {
        suggestScratchSize_enforcePermission();
        return getGsiService().suggestScratchSize();
    }
}
