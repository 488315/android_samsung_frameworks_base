package com.android.server.pm.dex;

import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.os.FileUtils;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.EventLog;
import android.util.PackageUtils;
import android.util.Slog;
import android.util.SparseArray;
import com.android.server.pm.Installer;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import libcore.util.HexEncoding;

/* loaded from: classes3.dex */
public class DynamicCodeLogger {
  public final Installer mInstaller;
  public final PackageDynamicCodeLoading mPackageDynamicCodeLoading;
  public IPackageManager mPackageManager;

  public DynamicCodeLogger(Installer installer) {
    this.mInstaller = installer;
    this.mPackageDynamicCodeLoading = new PackageDynamicCodeLoading();
  }

  public DynamicCodeLogger(
      IPackageManager iPackageManager,
      Installer installer,
      PackageDynamicCodeLoading packageDynamicCodeLoading) {
    this.mPackageManager = iPackageManager;
    this.mInstaller = installer;
    this.mPackageDynamicCodeLoading = packageDynamicCodeLoading;
  }

  public final IPackageManager getPackageManager() {
    if (this.mPackageManager == null) {
      this.mPackageManager = IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
    }
    return this.mPackageManager;
  }

  public Set getAllPackagesWithDynamicCodeLoading() {
    return this.mPackageDynamicCodeLoading.getAllPackagesWithDynamicCodeLoading();
  }

  /* JADX WARN: Removed duplicated region for block: B:31:0x00e0  */
  /* JADX WARN: Removed duplicated region for block: B:40:0x013e  */
  /* JADX WARN: Removed duplicated region for block: B:44:0x0160 A[SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:48:0x0138 A[SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:61:0x00e3  */
  /* JADX WARN: Removed duplicated region for block: B:80:0x0062  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public void logDynamicCodeLoading(String str) {
    ApplicationInfo applicationInfo;
    PackageInfo packageInfo;
    int i;
    ApplicationInfo applicationInfo2;
    String str2;
    byte[] bArr;
    int i2;
    PackageDynamicCodeLoading.PackageDynamicCode packageDynamicCodeInfo =
        getPackageDynamicCodeInfo(str);
    if (packageDynamicCodeInfo == null) {
      return;
    }
    SparseArray sparseArray = new SparseArray();
    boolean z = false;
    for (Map.Entry entry : packageDynamicCodeInfo.mFileUsageMap.entrySet()) {
      String str3 = (String) entry.getKey();
      PackageDynamicCodeLoading.DynamicCodeFile dynamicCodeFile =
          (PackageDynamicCodeLoading.DynamicCodeFile) entry.getValue();
      int i3 = dynamicCodeFile.mUserId;
      byte[] bArr2 = null;
      if (sparseArray.indexOfKey(i3) >= 0) {
        applicationInfo = (ApplicationInfo) sparseArray.get(i3);
      } else {
        try {
          packageInfo = getPackageManager().getPackageInfo(str, 0L, i3);
        } catch (RemoteException unused) {
        }
        if (packageInfo != null) {
          applicationInfo = packageInfo.applicationInfo;
          sparseArray.put(i3, applicationInfo);
          if (applicationInfo == null) {}
        } else {
          applicationInfo = null;
          sparseArray.put(i3, applicationInfo);
          if (applicationInfo == null) {
            Slog.d("DynamicCodeLogger", "Could not find package " + str + " for user " + i3);
            z |= this.mPackageDynamicCodeLoading.removeUserPackage(str, i3);
          }
        }
      }
      boolean z2 = z;
      ApplicationInfo applicationInfo3 = applicationInfo;
      if (applicationInfo3 == null) {
        z = z2;
      } else {
        if (fileIsUnder(str3, applicationInfo3.credentialProtectedDataDir)) {
          i = 2;
        } else if (fileIsUnder(str3, applicationInfo3.deviceProtectedDataDir)) {
          i = 1;
        } else {
          Slog.e("DynamicCodeLogger", "Could not infer CE/DE storage for path " + str3);
          z = z2 | this.mPackageDynamicCodeLoading.removeFile(str, str3, i3);
        }
        try {
          applicationInfo2 = applicationInfo3;
          str2 = "DynamicCodeLogger";
          try {
            bArr2 =
                this.mInstaller.hashSecondaryDexFile(
                    str3, str, applicationInfo3.uid, applicationInfo3.volumeUuid, i);
          } catch (Installer.InstallerException e) {
            e = e;
            Slog.e(
                str2, "Got InstallerException when hashing file " + str3 + ": " + e.getMessage());
            bArr = bArr2;
            if (dynamicCodeFile.mFileType != 'D') {}
            String computeSha256Digest =
                PackageUtils.computeSha256Digest(new File(str3).getName().getBytes());
            if (bArr == null) {}
            Slog.d(str2, "Got no hash for " + str3);
            z = z2 | this.mPackageDynamicCodeLoading.removeFile(str, str3, i3);
            while (r4.hasNext()) {}
          }
        } catch (Installer.InstallerException e2) {
          e = e2;
          applicationInfo2 = applicationInfo3;
          str2 = "DynamicCodeLogger";
        }
        bArr = bArr2;
        String str4 = dynamicCodeFile.mFileType != 'D' ? "dcl" : "dcln";
        String computeSha256Digest2 =
            PackageUtils.computeSha256Digest(new File(str3).getName().getBytes());
        if (bArr == null && bArr.length == 32) {
          computeSha256Digest2 = computeSha256Digest2 + ' ' + HexEncoding.encodeToString(bArr);
          z = z2;
        } else {
          Slog.d(str2, "Got no hash for " + str3);
          z = z2 | this.mPackageDynamicCodeLoading.removeFile(str, str3, i3);
        }
        for (String str5 : dynamicCodeFile.mLoadingPackages) {
          if (str5.equals(str)) {
            i2 = applicationInfo2.uid;
          } else {
            try {
              try {
                i2 = getPackageManager().getPackageUid(str5, 0L, i3);
              } catch (RemoteException unused2) {
                i2 = -1;
                if (i2 == -1) {}
              }
            } catch (RemoteException unused3) {
            }
          }
          if (i2 == -1) {
            writeDclEvent(str4, i2, computeSha256Digest2);
          }
        }
      }
    }
    if (z) {
      this.mPackageDynamicCodeLoading.maybeWriteAsync();
    }
  }

  public final boolean fileIsUnder(String str, String str2) {
    if (str2 == null) {
      return false;
    }
    try {
      return FileUtils.contains(
          new File(str2).getCanonicalPath(), new File(str).getCanonicalPath());
    } catch (IOException unused) {
      return false;
    }
  }

  public PackageDynamicCodeLoading.PackageDynamicCode getPackageDynamicCodeInfo(String str) {
    return this.mPackageDynamicCodeLoading.getPackageDynamicCodeInfo(str);
  }

  public void writeDclEvent(String str, int i, String str2) {
    EventLog.writeEvent(1397638484, str, Integer.valueOf(i), str2);
  }

  public void recordDex(int i, String str, String str2, String str3) {
    if (this.mPackageDynamicCodeLoading.record(str2, str, 68, i, str3)) {
      this.mPackageDynamicCodeLoading.maybeWriteAsync();
    }
  }

  public void recordNative(int i, String str) {
    try {
      String[] packagesForUid = getPackageManager().getPackagesForUid(i);
      if (packagesForUid != null) {
        if (packagesForUid.length == 0) {
          return;
        }
        String str2 = packagesForUid[0];
        if (this.mPackageDynamicCodeLoading.record(str2, str, 78, UserHandle.getUserId(i), str2)) {
          this.mPackageDynamicCodeLoading.maybeWriteAsync();
        }
      }
    } catch (RemoteException unused) {
    }
  }

  public void removePackage(String str) {
    if (this.mPackageDynamicCodeLoading.removePackage(str)) {
      this.mPackageDynamicCodeLoading.maybeWriteAsync();
    }
  }

  public void removeUserPackage(String str, int i) {
    if (this.mPackageDynamicCodeLoading.removeUserPackage(str, i)) {
      this.mPackageDynamicCodeLoading.maybeWriteAsync();
    }
  }

  public void readAndSync(Map map) {
    this.mPackageDynamicCodeLoading.read();
    this.mPackageDynamicCodeLoading.syncData(map);
  }

  public void writeNow() {
    this.mPackageDynamicCodeLoading.writeNow();
  }

  public void load(Map map) {
    HashMap hashMap = new HashMap();
    for (Map.Entry entry : map.entrySet()) {
      List list = (List) entry.getValue();
      int intValue = ((Integer) entry.getKey()).intValue();
      Iterator it = list.iterator();
      while (it.hasNext()) {
        ((Set)
                hashMap.computeIfAbsent(
                    ((PackageInfo) it.next()).packageName,
                    new Function() { // from class:
                                     // com.android.server.pm.dex.DynamicCodeLogger$$ExternalSyntheticLambda0
                      @Override // java.util.function.Function
                      public final Object apply(Object obj) {
                        Set lambda$load$0;
                        lambda$load$0 = DynamicCodeLogger.lambda$load$0((String) obj);
                        return lambda$load$0;
                      }
                    }))
            .add(Integer.valueOf(intValue));
      }
    }
    readAndSync(hashMap);
  }

  public static /* synthetic */ Set lambda$load$0(String str) {
    return new HashSet();
  }

  public void notifyPackageDataDestroyed(String str, int i) {
    if (i == -1) {
      removePackage(str);
    } else {
      removeUserPackage(str, i);
    }
  }
}
