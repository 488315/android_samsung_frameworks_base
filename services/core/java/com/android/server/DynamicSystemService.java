package com.android.server;

import android.content.Context;
import android.gsi.AvbPublicKey;
import android.gsi.GsiProgress;
import android.gsi.IGsiService;
import android.gsi.IGsiServiceCallback;
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

/* loaded from: classes.dex */
public class DynamicSystemService extends IDynamicSystemService.Stub {
  public Context mContext;
  public String mDsuSlot;
  public volatile IGsiService mGsiService;
  public String mInstallPath;

  public DynamicSystemService(Context context) {
    this.mContext = context;
  }

  public final IGsiService getGsiService() {
    if (this.mGsiService != null) {
      return this.mGsiService;
    }
    return IGsiService.Stub.asInterface(ServiceManager.waitForService("gsiservice"));
  }

  public class GsiServiceCallback extends IGsiServiceCallback.Stub {
    public int mResult = -1;

    public GsiServiceCallback() {}

    @Override // android.gsi.IGsiServiceCallback
    public synchronized void onResult(int i) {
      this.mResult = i;
      notify();
    }

    public int getResult() {
      return this.mResult;
    }
  }

  public boolean startInstallation(String str) {
    super.startInstallation_enforcePermission();
    IGsiService gsiService = getGsiService();
    this.mGsiService = gsiService;
    String str2 = SystemProperties.get("os.aot.path");
    if (str2.isEmpty()) {
      int myUserId = UserHandle.myUserId();
      for (VolumeInfo volumeInfo :
          ((StorageManager) this.mContext.getSystemService(StorageManager.class)).getVolumes()) {
        if (volumeInfo.getType() == 0
            && volumeInfo.isMountedWritable()
            && "vfat".equalsIgnoreCase(volumeInfo.fsType)) {
          long j = volumeInfo.getDisk().size >> 20;
          Slog.i("DynamicSystemService", volumeInfo.getPath() + ": " + j + " MB");
          if (j < 30720) {
            Slog.i("DynamicSystemService", volumeInfo.getPath() + ": insufficient storage");
          } else {
            File internalPathForUser = volumeInfo.getInternalPathForUser(myUserId);
            if (internalPathForUser != null) {
              str2 = new File(internalPathForUser, str).getPath();
            }
          }
        }
      }
      if (str2.isEmpty()) {
        str2 = "/data/gsi/" + str;
      }
      Slog.i("DynamicSystemService", "startInstallation -> " + str2);
    }
    this.mInstallPath = str2;
    this.mDsuSlot = str;
    if (gsiService.openInstall(str2) == 0) {
      return true;
    }
    Slog.i("DynamicSystemService", "Failed to open " + str2);
    return false;
  }

  public int createPartition(String str, long j, boolean z) {
    super.createPartition_enforcePermission();
    int createPartition = getGsiService().createPartition(str, j, z);
    if (createPartition != 0) {
      Slog.i("DynamicSystemService", "Failed to create partition: " + str);
    }
    return createPartition;
  }

  public boolean closePartition() {
    super.closePartition_enforcePermission();
    if (getGsiService().closePartition() == 0) {
      return true;
    }
    Slog.i("DynamicSystemService", "Partition installation completes with error");
    return false;
  }

  public boolean finishInstallation() {
    super.finishInstallation_enforcePermission();
    if (getGsiService().closeInstall() == 0) {
      return true;
    }
    Slog.i("DynamicSystemService", "Failed to finish installation");
    return false;
  }

  public GsiProgress getInstallationProgress() {
    super.getInstallationProgress_enforcePermission();
    return getGsiService().getInstallProgress();
  }

  public boolean abort() {
    super.abort_enforcePermission();
    return getGsiService().cancelGsiInstall();
  }

  public boolean isInUse() {
    return SystemProperties.getBoolean("ro.gsid.image_running", false);
  }

  public boolean isInstalled() {
    boolean z = SystemProperties.getBoolean("gsid.image_installed", false);
    Slog.i("DynamicSystemService", "isInstalled(): " + z);
    return z;
  }

  public boolean isEnabled() {
    super.isEnabled_enforcePermission();
    return getGsiService().isGsiEnabled();
  }

  public boolean remove() {
    super.remove_enforcePermission();
    try {
      GsiServiceCallback gsiServiceCallback = new GsiServiceCallback();
      synchronized (gsiServiceCallback) {
        getGsiService().removeGsiAsync(gsiServiceCallback);
        gsiServiceCallback.wait(8192L);
      }
      return gsiServiceCallback.getResult() == 0;
    } catch (InterruptedException unused) {
      Slog.e("DynamicSystemService", "remove() was interrupted");
      return false;
    }
  }

  public boolean setEnable(boolean z, boolean z2) {
    super.setEnable_enforcePermission();
    IGsiService gsiService = getGsiService();
    if (z) {
      try {
        if (this.mDsuSlot == null) {
          this.mDsuSlot = gsiService.getActiveDsuSlot();
        }
        GsiServiceCallback gsiServiceCallback = new GsiServiceCallback();
        synchronized (gsiServiceCallback) {
          gsiService.enableGsiAsync(z2, this.mDsuSlot, gsiServiceCallback);
          gsiServiceCallback.wait(8192L);
        }
        return gsiServiceCallback.getResult() == 0;
      } catch (InterruptedException unused) {
        Slog.e("DynamicSystemService", "setEnable() was interrupted");
        return false;
      }
    }
    return gsiService.disableGsi();
  }

  public boolean setAshmem(ParcelFileDescriptor parcelFileDescriptor, long j) {
    super.setAshmem_enforcePermission();
    try {
      return getGsiService().setGsiAshmem(parcelFileDescriptor, j);
    } catch (RemoteException e) {
      throw new RuntimeException(e.toString());
    }
  }

  public boolean submitFromAshmem(long j) {
    super.submitFromAshmem_enforcePermission();
    try {
      return getGsiService().commitGsiChunkFromAshmem(j);
    } catch (RemoteException e) {
      throw new RuntimeException(e.toString());
    }
  }

  public boolean getAvbPublicKey(AvbPublicKey avbPublicKey) {
    super.getAvbPublicKey_enforcePermission();
    try {
      return getGsiService().getAvbPublicKey(avbPublicKey) == 0;
    } catch (RemoteException e) {
      throw new RuntimeException(e.toString());
    }
  }

  public long suggestScratchSize() {
    super.suggestScratchSize_enforcePermission();
    return getGsiService().suggestScratchSize();
  }
}
