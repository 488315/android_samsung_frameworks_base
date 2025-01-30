package com.samsung.android.core.pm.containerservice;

import android.content.pm.parsing.PackageLite;
import android.os.Environment;
import android.os.FileUtils;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.storage.IStorageManager;
import android.os.storage.StorageVolume;
import android.util.DataUnit;
import android.util.Log;
import com.android.internal.content.InstallLocationUtils;
import com.android.internal.content.NativeLibraryHelper;
import com.samsung.android.media.AudioParameter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/* loaded from: classes5.dex */
public class PackageHelperExt {
  private static final boolean DEBUG_MOVE = false;
  public static final int OperationSucceeded = 0;
  private static String TAG = "SamsungPackageHelper";
  private static final long MB_IN_BYTES = DataUnit.MEBIBYTES.toBytes(1);

  public interface StorageManagerExt {
    int createSecureContainer(String str, int i, String str2, String str3, int i2, boolean z)
        throws RemoteException;

    int destroySecureContainer(String str, boolean z) throws RemoteException;

    int finalizeSecureContainer(String str) throws RemoteException;

    void finishMediaUpdate() throws RemoteException;

    int fixPermissionsSecureContainer(String str, int i, String str2) throws RemoteException;

    String getSecureContainerFilesystemPath(String str) throws RemoteException;

    String[] getSecureContainerList() throws RemoteException;

    String getSecureContainerPath(String str) throws RemoteException;

    int getUsedSpaceSecureContainer(String str) throws RemoteException;

    StorageVolume[] getVolumeList(int i, String str, int i2) throws RemoteException;

    boolean isSecureContainerMounted(String str) throws RemoteException;

    int mountSecureContainer(String str, String str2, int i, boolean z) throws RemoteException;

    int renameSecureContainer(String str, String str2) throws RemoteException;

    int resizeSecureContainer(String str, int i, String str2) throws RemoteException;

    int trimSecureContainer(String str, int i, String str2) throws RemoteException;

    int unmountSecureContainer(String str, boolean z) throws RemoteException;
  }

  public static StorageManagerExt getStorageManagerExt() throws RemoteException {
    return new StorageManagerExt() { // from class:
                                     // com.samsung.android.core.pm.containerservice.PackageHelperExt.1
      IStorageManager mStorageManager = PackageHelperExt.getStorageManager();

      @Override // com.samsung.android.core.pm.containerservice.PackageHelperExt.StorageManagerExt
      public int createSecureContainer(
          String id, int sizeMb, String fstype, String key, int ownerUid, boolean external)
          throws RemoteException {
        return this.mStorageManager.createSecureContainer(
            id, sizeMb, fstype, key, ownerUid, external);
      }

      @Override // com.samsung.android.core.pm.containerservice.PackageHelperExt.StorageManagerExt
      public String getSecureContainerPath(String id) throws RemoteException {
        return this.mStorageManager.getSecureContainerPath(id);
      }

      @Override // com.samsung.android.core.pm.containerservice.PackageHelperExt.StorageManagerExt
      public int resizeSecureContainer(String id, int sizeMb, String key) throws RemoteException {
        return this.mStorageManager.resizeSecureContainer(id, sizeMb, key);
      }

      @Override // com.samsung.android.core.pm.containerservice.PackageHelperExt.StorageManagerExt
      public int mountSecureContainer(String id, String key, int ownerUid, boolean readOnly)
          throws RemoteException {
        return this.mStorageManager.mountSecureContainer(id, key, ownerUid, readOnly);
      }

      @Override // com.samsung.android.core.pm.containerservice.PackageHelperExt.StorageManagerExt
      public int renameSecureContainer(String oldId, String newId) throws RemoteException {
        return this.mStorageManager.renameSecureContainer(oldId, newId);
      }

      @Override // com.samsung.android.core.pm.containerservice.PackageHelperExt.StorageManagerExt
      public String getSecureContainerFilesystemPath(String id) throws RemoteException {
        return this.mStorageManager.getSecureContainerFilesystemPath(id);
      }

      @Override // com.samsung.android.core.pm.containerservice.PackageHelperExt.StorageManagerExt
      public int finalizeSecureContainer(String id) throws RemoteException {
        return this.mStorageManager.finalizeSecureContainer(id);
      }

      @Override // com.samsung.android.core.pm.containerservice.PackageHelperExt.StorageManagerExt
      public int destroySecureContainer(String id, boolean force) throws RemoteException {
        return this.mStorageManager.destroySecureContainer(id, force);
      }

      @Override // com.samsung.android.core.pm.containerservice.PackageHelperExt.StorageManagerExt
      public boolean isSecureContainerMounted(String id) throws RemoteException {
        return this.mStorageManager.isSecureContainerMounted(id);
      }

      @Override // com.samsung.android.core.pm.containerservice.PackageHelperExt.StorageManagerExt
      public int unmountSecureContainer(String id, boolean force) throws RemoteException {
        return this.mStorageManager.unmountSecureContainer(id, force);
      }

      @Override // com.samsung.android.core.pm.containerservice.PackageHelperExt.StorageManagerExt
      public String[] getSecureContainerList() throws RemoteException {
        return this.mStorageManager.getSecureContainerList();
      }

      @Override // com.samsung.android.core.pm.containerservice.PackageHelperExt.StorageManagerExt
      public int fixPermissionsSecureContainer(String id, int gid, String filename)
          throws RemoteException {
        return this.mStorageManager.fixPermissionsSecureContainer(id, gid, filename);
      }

      @Override // com.samsung.android.core.pm.containerservice.PackageHelperExt.StorageManagerExt
      public void finishMediaUpdate() throws RemoteException {
        this.mStorageManager.finishMediaUpdate();
      }

      @Override // com.samsung.android.core.pm.containerservice.PackageHelperExt.StorageManagerExt
      public int getUsedSpaceSecureContainer(String id) throws RemoteException {
        return this.mStorageManager.getUsedSpaceSecureContainer(id);
      }

      @Override // com.samsung.android.core.pm.containerservice.PackageHelperExt.StorageManagerExt
      public int trimSecureContainer(String id, int sizeMb, String key) throws RemoteException {
        return this.mStorageManager.trimSecureContainer(id, sizeMb, key);
      }

      @Override // com.samsung.android.core.pm.containerservice.PackageHelperExt.StorageManagerExt
      public StorageVolume[] getVolumeList(int userId, String callingPackage, int flags)
          throws RemoteException {
        return this.mStorageManager.getVolumeList(userId, callingPackage, flags);
      }
    };
  }

  public static IStorageManager getStorageManager() throws RemoteException {
    IBinder service = ServiceManager.getService(AudioParameter.VALUE_MOUNT);
    if (service != null) {
      return IStorageManager.Stub.asInterface(service);
    }
    Log.m96e(TAG, "Can't get storagemanager service");
    throw new RemoteException("Could not contact storagemanager service");
  }

  public static long calculateInstalledSize(
      PackageLite pkg, NativeLibraryHelper.Handle handle, String abiOverride) throws IOException {
    return InstallLocationUtils.calculateInstalledSize(pkg, handle, abiOverride);
  }

  public static long calculateApksSize(PackageLite pkg) {
    long sizeBytes = 0;
    for (String codePath : pkg.getAllApkPaths()) {
      File codeFile = new File(codePath);
      sizeBytes += codeFile.length();
    }
    return sizeBytes;
  }

  /* JADX WARN: Removed duplicated region for block: B:12:0x0061 A[Catch: RemoteException -> 0x0066, TRY_LEAVE, TryCatch #0 {RemoteException -> 0x0066, blocks: (B:3:0x0011, B:7:0x001f, B:9:0x0048, B:12:0x0061, B:14:0x0033), top: B:2:0x0011 }] */
  /* JADX WARN: Removed duplicated region for block: B:9:0x0048 A[Catch: RemoteException -> 0x0066, TryCatch #0 {RemoteException -> 0x0066, blocks: (B:3:0x0011, B:7:0x001f, B:9:0x0048, B:12:0x0061, B:14:0x0033), top: B:2:0x0011 }] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public static String createSdDir(
      long sizeBytes, String cid, String sdEncKey, int uid, boolean isExternal) {
    int rc;
    long j = MB_IN_BYTES;
    int sizeMb = ((int) (((sizeBytes * 1.03d) + j) / j)) + 1;
    try {
      StorageManagerExt sm = getStorageManagerExt();
      if (!Environment.isExternalStorageEmulated() && isExternal) {
        Log.m98i(TAG, "createSdDir with fat");
        rc = sm.createSecureContainer(cid, sizeMb, "fat", sdEncKey, uid, isExternal);
        if (rc == 0) {
          Log.m96e(TAG, "Failed to create secure container " + cid);
          return null;
        }
        String cachePath = sm.getSecureContainerPath(cid);
        return cachePath;
      }
      Log.m98i(TAG, "createSdDir with ext4");
      rc = sm.createSecureContainer(cid, sizeMb, "ext4", sdEncKey, uid, isExternal);
      if (rc == 0) {}
    } catch (RemoteException e) {
      Log.m96e(TAG, "StorageManagerService running?");
      return null;
    }
  }

  public static boolean resizeSdDir(long sizeBytes, String cid, String sdEncKey) {
    long j = MB_IN_BYTES;
    int sizeMb = ((int) ((sizeBytes + j) / j)) + 1;
    try {
      StorageManagerExt sm = getStorageManagerExt();
      int rc = sm.resizeSecureContainer(cid, sizeMb, sdEncKey);
      if (rc == 0) {
        return true;
      }
    } catch (RemoteException e) {
      Log.m96e(TAG, "StorageManagerService running?");
    }
    Log.m96e(TAG, "Failed to create secure container " + cid);
    return false;
  }

  public static String mountSdDir(String cid, String key, int ownerUid) {
    return mountSdDir(cid, key, ownerUid, true);
  }

  public static String mountSdDir(String cid, String key, int ownerUid, boolean readOnly) {
    try {
      StorageManagerExt sm = getStorageManagerExt();
      int rc = sm.mountSecureContainer(cid, key, ownerUid, readOnly);
      if (rc != 0) {
        Log.m98i(TAG, "Failed to mount container " + cid + ", rc: " + rc);
        return null;
      }
      return sm.getSecureContainerPath(cid);
    } catch (RemoteException e) {
      Log.m96e(TAG, "StorageManagerService running?");
      return null;
    }
  }

  public static boolean unMountSdDir(String cid, boolean force) {
    try {
      StorageManagerExt sm = getStorageManagerExt();
      int rc = sm.unmountSecureContainer(cid, force);
      if (rc != 0) {
        Log.m96e(TAG, "Failed to unmount " + cid + ", force: " + force + ", rc: " + rc);
        return false;
      }
      return true;
    } catch (RemoteException e) {
      Log.m96e(TAG, "StorageManagerService running?");
      return false;
    }
  }

  public static boolean renameSdDir(String oldId, String newId) {
    try {
      int rc = getStorageManagerExt().renameSecureContainer(oldId, newId);
      if (rc != 0) {
        Log.m96e(TAG, "Failed to rename " + oldId + " to " + newId + ", rc: " + rc);
        return false;
      }
      return true;
    } catch (RemoteException e) {
      Log.m98i(TAG, "Failed to rename  " + oldId + " to " + newId + " with exception " + e);
      return false;
    }
  }

  public static String getSdDir(String cid) {
    try {
      return getStorageManagerExt().getSecureContainerPath(cid);
    } catch (RemoteException e) {
      Log.m96e(TAG, "Failed to get container path for " + cid + " with exception " + e);
      return null;
    }
  }

  public static String getSdFilesystem(String cid) {
    try {
      return getStorageManagerExt().getSecureContainerFilesystemPath(cid);
    } catch (RemoteException e) {
      Log.m96e(TAG, "Failed to get container file system path for " + cid + " with exception " + e);
      return null;
    }
  }

  public static boolean finalizeSdDir(String cid) {
    try {
      int rc = getStorageManagerExt().finalizeSecureContainer(cid);
      if (rc != 0) {
        Log.m98i(TAG, "Failed to finalize container " + cid);
        return false;
      }
      return true;
    } catch (RemoteException e) {
      Log.m96e(TAG, "Failed to finalize container " + cid + " with exception " + e);
      return false;
    }
  }

  public static boolean destroySdDir(String cid) {
    try {
      int rc = getStorageManagerExt().destroySecureContainer(cid, true);
      if (rc == 0) {
        return true;
      }
      Log.m98i(TAG, "Failed to destroy container " + cid);
      return false;
    } catch (RemoteException e) {
      Log.m96e(TAG, "Failed to destroy container " + cid + " with exception " + e);
      return false;
    }
  }

  public static String[] getSecureContainerList() {
    try {
      return getStorageManagerExt().getSecureContainerList();
    } catch (RemoteException e) {
      Log.m96e(TAG, "Failed to get secure container list with exception " + e);
      return null;
    }
  }

  public static boolean isContainerMounted(String cid) {
    try {
      return getStorageManagerExt().isSecureContainerMounted(cid);
    } catch (RemoteException e) {
      Log.m96e(TAG, "Failed to find out if container " + cid + " mounted");
      return false;
    }
  }

  public static int getUsedSpaceSecureContainer(String cid) {
    try {
      return getStorageManagerExt().getUsedSpaceSecureContainer(cid);
    } catch (RemoteException e) {
      Log.m96e(TAG, "Failed to find the occupied size of container " + cid);
      return -1;
    }
  }

  public static boolean trimSecureContainer(String cid, int sizeMb, String key) {
    try {
      int rc = getStorageManagerExt().trimSecureContainer(cid, sizeMb, key);
      if (rc != 0) {
        Log.m98i(TAG, "Failed to trim container " + cid);
        return false;
      }
      return true;
    } catch (RemoteException e) {
      Log.m96e(TAG, "Failed to trim container " + cid + " with exception " + e);
      return false;
    }
  }

  public static long extractPublicFiles(File apkFile, File publicZipFile) throws IOException {
    FileOutputStream fstr;
    ZipOutputStream publicZipOutStream;
    if (publicZipFile == null) {
      fstr = null;
      publicZipOutStream = null;
    } else {
      fstr = new FileOutputStream(publicZipFile);
      publicZipOutStream = new ZipOutputStream(fstr);
      Log.m94d(TAG, "Extracting " + apkFile + " to " + publicZipFile);
    }
    long size = 0;
    try {
      ZipFile privateZip = new ZipFile(apkFile.getAbsolutePath());
      try {
        Iterator it = Collections.list(privateZip.entries()).iterator();
        while (it.hasNext()) {
          ZipEntry zipEntry = (ZipEntry) it.next();
          String zipEntryName = zipEntry.getName();
          if ("AndroidManifest.xml".equals(zipEntryName)
              || "resources.arsc".equals(zipEntryName)
              || zipEntryName.startsWith("res/")) {
            size += zipEntry.getSize();
            if (publicZipFile != null) {
              copyZipEntry(zipEntry, privateZip, publicZipOutStream);
            }
          }
        }
        if (publicZipFile != null) {
          publicZipOutStream.finish();
          publicZipOutStream.flush();
          FileUtils.sync(fstr);
          publicZipOutStream.close();
          FileUtils.setPermissions(publicZipFile.getAbsolutePath(), 420, -1, -1);
        }
        return size;
      } finally {
        try {
          privateZip.close();
        } catch (IOException e) {
        }
      }
    } finally {
      IoUtils.closeQuietly(publicZipOutStream);
    }
  }

  private static void copyZipEntry(
      ZipEntry zipEntry, ZipFile inZipFile, ZipOutputStream outZipStream) throws IOException {
    ZipEntry newEntry;
    byte[] buffer = new byte[4096];
    if (zipEntry.getMethod() == 0) {
      newEntry = new ZipEntry(zipEntry);
    } else {
      newEntry = new ZipEntry(zipEntry.getName());
    }
    outZipStream.putNextEntry(newEntry);
    InputStream data = inZipFile.getInputStream(zipEntry);
    while (true) {
      try {
        int num = data.read(buffer);
        if (num > 0) {
          outZipStream.write(buffer, 0, num);
        } else {
          outZipStream.flush();
          return;
        }
      } finally {
        IoUtils.closeQuietly(data);
      }
    }
  }

  public static boolean fixSdPermissions(String cid, int gid, String filename) {
    try {
      int rc = getStorageManagerExt().fixPermissionsSecureContainer(cid, gid, filename);
      if (rc != 0) {
        Log.m98i(TAG, "Failed to fixperms container " + cid);
        return false;
      }
      return true;
    } catch (RemoteException e) {
      Log.m96e(TAG, "Failed to fixperms container " + cid + " with exception " + e);
      return false;
    }
  }

  public static StorageVolume[] getVolumeList(int userId, String callingPackage, int flags) {
    try {
      return getStorageManagerExt().getVolumeList(userId, callingPackage, flags);
    } catch (RemoteException e) {
      Log.m96e(TAG, "Failed to get the volume list with exception" + e);
      return null;
    }
  }
}
