package com.android.server.knox.dar;

import android.content.Context;
import android.os.Binder;
import android.os.Bundle;
import android.os.UserManager;
import android.os.storage.StorageManager;
import android.util.Log;
import com.android.server.pm.Installer;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

@Deprecated
/* loaded from: classes2.dex */
public class EnterprisePartitionManager {
  public static final boolean isKnoxBuildEnabled;
  public static Object mInstallLock;
  public static final Bundle mKnoxInfo;
  public static final String mKnoxVersion;
  public static Installer mPackageTasker;
  public static EnterprisePartitionManager sInstance;
  public final Context mContext;
  public final Injector mInjector;
  public Hashtable mSessionIdDstPath;
  public List notAppliedPaths;
  public StorageManager storage;

  public class EpmMigrationCmd {}

  public class EpmResponseCode {}

  public static void logD(String str) {}

  static {
    Bundle knoxInfo = SemPersonaManager.getKnoxInfo();
    mKnoxInfo = knoxInfo;
    String string = knoxInfo != null ? knoxInfo.getString("version") : null;
    mKnoxVersion = string;
    isKnoxBuildEnabled =
        (string == null || string.isEmpty() || "v00".equals(string)) ? false : true;
    sInstance = null;
  }

  public static void setInstaller(Installer installer, Object obj) {
    if (isKnoxBuildEnabled) {
      mPackageTasker = installer;
      mInstallLock = obj;
    } else {
      mPackageTasker = null;
    }
  }

  public void setTestInstaller(Installer installer, Object obj) {
    mPackageTasker = installer;
    mInstallLock = obj;
  }

  public static synchronized EnterprisePartitionManager getInstance(Context context) {
    EnterprisePartitionManager enterprisePartitionManager;
    synchronized (EnterprisePartitionManager.class) {
      if (sInstance == null) {
        sInstance = new EnterprisePartitionManager(context);
      }
      enterprisePartitionManager = sInstance;
    }
    return enterprisePartitionManager;
  }

  public EnterprisePartitionManager(Context context) {
    this(new Injector(context));
  }

  public EnterprisePartitionManager(Injector injector) {
    this.mSessionIdDstPath = null;
    this.storage = null;
    this.notAppliedPaths = null;
    this.mInjector = injector;
    this.mContext = injector.getContext();
    if (isKnoxBuildEnabled) {
      this.mSessionIdDstPath = new Hashtable();
    } else {
      logD("Knox is not supported on this device..");
    }
  }

  public class Injector {
    public final Context mContext;

    public Injector(Context context) {
      this.mContext = context;
    }

    public Context getContext() {
      return this.mContext;
    }

    public long binderClearCallingIdentity() {
      return Binder.clearCallingIdentity();
    }

    public void binderRestoreCallingIdentity(long j) {
      Binder.restoreCallingIdentity(j);
    }
  }

  public boolean setSdpPolicyToPath(int i, String str) {
    StorageManager storageManager =
        (StorageManager) this.mContext.getSystemService(StorageManager.class);
    this.storage = storageManager;
    if (storageManager != null) {
      return storageManager.setSdpPolicyToPath(i, str);
    }
    Log.e("EnterprisePartitionManager", "StorageManager instance is NULL");
    return false;
  }

  public boolean setEviction(int i, boolean z) {
    boolean eviction;
    Log.i("EnterprisePartitionManager", "setEviction(user:" + i + " evict:" + z + ")");
    synchronized (mInstallLock) {
      try {
        try {
          eviction = mPackageTasker.setEviction(i, z);
        } catch (Installer.InstallerException unused) {
          return false;
        }
      } catch (Throwable th) {
        throw th;
      }
    }
    return eviction;
  }

  public boolean setDualDARPolicy(int i, int i2) {
    long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
    try {
      StorageManager storageManager =
          (StorageManager) this.mContext.getSystemService(StorageManager.class);
      this.storage = storageManager;
      if (storageManager != null) {
        return storageManager.setDualDARPolicy(i, i2);
      }
      Log.e("EnterprisePartitionManager", "StorageManager instance is NULL");
      this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
      return false;
    } finally {
      this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
    }
  }

  public boolean setDualDARPolicyDir(int i, int i2, String str) {
    boolean dualDARPolicyDir;
    Log.i(
        "EnterprisePartitionManager",
        "setDualDARPolicyDir(user:" + i + " flags:" + i2 + " path:" + str + ")");
    synchronized (mInstallLock) {
      try {
        try {
          dualDARPolicyDir = mPackageTasker.setDualDARPolicyDir(i, i2, str);
        } catch (Installer.InstallerException unused) {
          return false;
        }
      } catch (Throwable th) {
        throw th;
      }
    }
    return dualDARPolicyDir;
  }

  public boolean setDualDARPolicyDirRecursively(int i, int i2, String str) {
    boolean dualDARPolicyDirRecursively;
    Log.i(
        "EnterprisePartitionManager",
        "setDualDARPolicyDirRecursively(user:" + i + " flags:" + i2 + " path:" + str + ")");
    synchronized (mInstallLock) {
      try {
        try {
          dualDARPolicyDirRecursively = mPackageTasker.setDualDARPolicyDirRecursively(i, i2, str);
        } catch (Installer.InstallerException unused) {
          return false;
        }
      } catch (Throwable th) {
        throw th;
      }
    }
    return dualDARPolicyDirRecursively;
  }

  public boolean hasDualDARPolicyRecursively(String str, List list) {
    boolean hasDualDARPolicyRecursively;
    Log.i("EnterprisePartitionManager", "hasDualDARPolicyRecursively(path:" + str + ")");
    try {
      List list2 = this.notAppliedPaths;
      if (list2 == null) {
        this.notAppliedPaths = new ArrayList();
      } else {
        list2.clear();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    synchronized (mInstallLock) {
      try {
        hasDualDARPolicyRecursively =
            mPackageTasker.hasDualDARPolicyRecursively(str, this.notAppliedPaths);
        if (!this.notAppliedPaths.isEmpty()) {
          for (String str2 : this.notAppliedPaths) {
            Log.d("EnterprisePartitionManager", "Policy not applied paths : " + str2);
            list.add(str2);
          }
        }
      } catch (Installer.InstallerException unused) {
        if (!this.notAppliedPaths.isEmpty()) {
          for (String str3 : this.notAppliedPaths) {
            Log.d("EnterprisePartitionManager", "Policy not applied paths : " + str3);
            list.add(str3);
          }
        }
        return false;
      } catch (Throwable th) {
        if (!this.notAppliedPaths.isEmpty()) {
          for (String str4 : this.notAppliedPaths) {
            Log.d("EnterprisePartitionManager", "Policy not applied paths : " + str4);
            list.add(str4);
          }
        }
        throw th;
      }
    }
    return hasDualDARPolicyRecursively;
  }

  public boolean getDualDARLockstate() {
    boolean dualDARLockstate;
    Log.i("EnterprisePartitionManager", "getDualDARLockstate");
    synchronized (mInstallLock) {
      try {
        try {
          dualDARLockstate = mPackageTasker.getDualDARLockstate();
        } catch (Installer.InstallerException unused) {
          return false;
        }
      } catch (Throwable th) {
        throw th;
      }
    }
    return dualDARLockstate;
  }

  public boolean isFileExist(String str, int i) {
    boolean z = false;
    if (mPackageTasker == null || str == null || str.isEmpty()) {
      return false;
    }
    if (i != 0 && str.startsWith("/storage/emulated")) {
      str = str.replaceFirst("/storage", "/mnt/user/" + i);
      Log.d("EnterprisePartitionManager", "getFileInfo - realath : " + str);
    }
    synchronized (mInstallLock) {
      try {
        if (mPackageTasker.getKnoxFileInfo(str)[0] == 0) {
          z = true;
        }
      } catch (Installer.InstallerException unused) {
      }
    }
    return z;
  }

  public boolean deleteFile(String str, int i) {
    boolean z = false;
    if (mPackageTasker == null || str == null || str.isEmpty()) {
      return false;
    }
    synchronized (mInstallLock) {
      try {
        z = mPackageTasker.deleteKnoxFile(str);
      } catch (Installer.InstallerException unused) {
      }
    }
    return z;
  }

  public ArrayList getFiles(String str, int i) {
    boolean z;
    if (mPackageTasker == null || str == null || str.isEmpty()) {
      return null;
    }
    ArrayList arrayList = new ArrayList();
    long currentTimeMillis = System.currentTimeMillis();
    synchronized (mInstallLock) {
      try {
        z = mPackageTasker.getKnoxScanDir(str, currentTimeMillis, arrayList);
      } catch (Installer.InstallerException unused) {
        z = false;
      }
    }
    if (z) {
      return arrayList;
    }
    return null;
  }

  public Bundle getFileInfo(String str, int i) {
    boolean z;
    long[] jArr;
    int i2;
    long j;
    long j2;
    Bundle bundle = new Bundle();
    if (mPackageTasker == null || str == null || str.isEmpty()) {
      bundle.putInt(KnoxCustomManagerService.SPCM_KEY_RESULT, -2);
      return bundle;
    }
    if (i != 0 && str.startsWith("/storage/emulated")) {
      str = str.replaceFirst("/storage", "/mnt/user/" + i);
      Log.d("EnterprisePartitionManager", "getFileInfo - realath : " + str);
    }
    synchronized (mInstallLock) {
      z = false;
      jArr = null;
      try {
        jArr = mPackageTasker.getKnoxFileInfo(str);
        i2 = (int) jArr[0];
      } catch (Installer.InstallerException unused) {
        i2 = -1;
      }
    }
    if (jArr == null || i2 != 0) {
      j = 0;
      j2 = 0;
    } else {
      j = jArr[1] * 1000;
      j2 = jArr[2];
      if (jArr[3] == 1) {
        z = true;
      }
    }
    bundle.putInt(KnoxCustomManagerService.SPCM_KEY_RESULT, i2);
    bundle.putLong("last_modified_date", j);
    bundle.putLong("file_size", j2);
    bundle.putBoolean("is_dir", z);
    return bundle;
  }

  public void cancelCopyChunks(long j) {
    String str = (String) this.mSessionIdDstPath.get(Long.valueOf(j));
    if (mPackageTasker == null || str == null || str.isEmpty()) {
      return;
    }
    synchronized (mInstallLock) {
      try {
        if (mPackageTasker.copyKnoxCancel(str, j)) {
          this.mSessionIdDstPath.remove(Long.valueOf(j));
        }
      } catch (Installer.InstallerException unused) {
      }
    }
  }

  public int copyChunks(
      String str, int i, String str2, int i2, long j, int i3, long j2, boolean z) {
    String str3;
    int i4;
    String str4 = str;
    if (mPackageTasker == null) {
      return -19;
    }
    if (str4 == null
        || str.isEmpty()
        || str2 == null
        || str2.isEmpty()
        || !isUserUnlocked(i)
        || !isUserUnlocked(i2)) {
      return -2;
    }
    if (i != 0 && str.startsWith("/storage/emulated")) {
      str4 = str.replaceFirst("/storage", "/mnt/user/" + i);
      Log.d("EnterprisePartitionManager", "srcRealPath : " + str4);
    }
    String str5 = str4;
    if (i2 == 0 || !str2.startsWith("/storage/emulated")) {
      str3 = str2;
    } else {
      String replaceFirst = str2.replaceFirst("/storage", "/mnt/user/" + i2);
      Log.d("EnterprisePartitionManager", "dstRealPath : " + replaceFirst);
      str3 = replaceFirst;
    }
    if (!this.mSessionIdDstPath.containsKey(Long.valueOf(j2))) {
      this.mSessionIdDstPath.put(Long.valueOf(j2), str3);
    }
    int i5 = z ? 36 : 32;
    synchronized (mInstallLock) {
      try {
        i4 = mPackageTasker.copyKnoxChunks(str5, i, str3, i2, i5, j, i3, j2);
      } catch (Installer.InstallerException unused) {
        i4 = -1;
      }
      if (i4 != 201) {
        if (i4 == 200) {
          this.mSessionIdDstPath.remove(Long.valueOf(j2));
        }
      }
      i4 = 0;
    }
    return i4;
  }

  public int copy(String str, int i, String str2, int i2) {
    return copy(str, i, str2, i2, 1);
  }

  public int move(String str, int i, String str2, int i2) {
    return move(str, i, str2, i2, 36);
  }

  public int copy(String str, int i, String str2, int i2, int i3) {
    int i4;
    if (mPackageTasker == null) {
      return -19;
    }
    if (str == null
        || str.isEmpty()
        || str2 == null
        || str2.isEmpty()
        || !isUserUnlocked(i)
        || !isUserUnlocked(i2)) {
      return -2;
    }
    synchronized (mInstallLock) {
      i4 = -1;
      try {
        if (mPackageTasker.copyKnoxAppData(str, i, str2, i2, i3)) {
          i4 = 0;
        }
      } catch (Installer.InstallerException unused) {
      }
    }
    return i4;
  }

  public int move(String str, int i, String str2, int i2, int i3) {
    int i4;
    if (mPackageTasker == null) {
      return -19;
    }
    int i5 = i3 | 4;
    if (str == null
        || str.isEmpty()
        || str2 == null
        || str2.isEmpty()
        || !isUserUnlocked(i)
        || !isUserUnlocked(i2)) {
      return -2;
    }
    synchronized (mInstallLock) {
      i4 = -1;
      try {
        if (mPackageTasker.copyKnoxAppData(str, i, str2, i2, i5)) {
          i4 = 0;
        }
      } catch (Installer.InstallerException unused) {
      }
    }
    return i4;
  }

  public final boolean isUserUnlocked(int i) {
    long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
    boolean z = false;
    try {
      z = UserManager.get(this.mContext).isUserUnlocked(i);
      if (!z) {
        Log.e("EnterprisePartitionManager", "User " + i + " is not unlocked");
      }
    } finally {
      try {
        return z;
      } finally {
      }
    }
    return z;
  }
}
