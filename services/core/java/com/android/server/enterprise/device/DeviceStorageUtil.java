package com.android.server.enterprise.device;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import com.android.server.enterprise.adapter.AdapterRegistry;
import com.android.server.enterprise.adapter.IStorageManagerAdapter;
import java.io.File;

/* loaded from: classes2.dex */
public class DeviceStorageUtil {
  public final int ERROR = -1;
  public final int SIZE_INVALID = -1;
  public Context mContext;

  public DeviceStorageUtil(Context context) {
    this.mContext = context;
  }

  public long getAvailableInternalMemorySize() {
    try {
      StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
      long availableBlocksLong = statFs.getAvailableBlocksLong() * statFs.getBlockSizeLong();
      File internalSdCardDirectory = getInternalSdCardDirectory();
      if (internalSdCardDirectory == null) {
        return -1L;
      }
      StatFs statFs2 = new StatFs(internalSdCardDirectory.getPath());
      return availableBlocksLong + (statFs2.getAvailableBlocksLong() * statFs2.getBlockSizeLong());
    } catch (Exception e) {
      e.printStackTrace();
      return -1L;
    }
  }

  public long getTotalInternalMemorySize() {
    try {
      StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
      long blockCountLong = statFs.getBlockCountLong() * statFs.getBlockSizeLong();
      File internalSdCardDirectory = getInternalSdCardDirectory();
      if (internalSdCardDirectory == null) {
        return -1L;
      }
      StatFs statFs2 = new StatFs(internalSdCardDirectory.getPath());
      return blockCountLong + (statFs2.getBlockCountLong() * statFs2.getBlockSizeLong());
    } catch (Exception e) {
      e.printStackTrace();
      return -1L;
    }
  }

  public long getAvailableExternalMemorySize() {
    File externalSdCardDirectory;
    try {
      if (!externalSdCardAvailable()
          || (externalSdCardDirectory = getExternalSdCardDirectory()) == null) {
        return -1L;
      }
      StatFs statFs = new StatFs(externalSdCardDirectory.getPath());
      return statFs.getAvailableBlocksLong() * statFs.getBlockSizeLong();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return -1L;
  }

  public long getTotalExternalMemorySize() {
    File externalSdCardDirectory;
    try {
      if (!externalSdCardAvailable()
          || (externalSdCardDirectory = getExternalSdCardDirectory()) == null) {
        return -1L;
      }
      StatFs statFs = new StatFs(externalSdCardDirectory.getPath());
      return statFs.getBlockCountLong() * statFs.getBlockSizeLong();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return -1L;
  }

  public final File getExternalSdCardDirectory() {
    String externalSdCardPath = getStorageAdapter().getExternalSdCardPath();
    if (externalSdCardPath == null) {
      return null;
    }
    return new File(externalSdCardPath);
  }

  public final boolean externalSdCardAvailable() {
    String externalSdCardState = getStorageAdapter().getExternalSdCardState();
    if (externalSdCardState == null) {
      return false;
    }
    return externalSdCardState.equals("mounted");
  }

  public final File getInternalSdCardDirectory() {
    String internalSdCardPath = getStorageAdapter().getInternalSdCardPath();
    if (internalSdCardPath == null) {
      return null;
    }
    return new File(internalSdCardPath);
  }

  public final IStorageManagerAdapter getStorageAdapter() {
    return (IStorageManagerAdapter) AdapterRegistry.getAdapter(IStorageManagerAdapter.class);
  }
}
