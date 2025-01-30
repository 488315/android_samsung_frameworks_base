package com.android.server;

import android.content.Context;
import android.content.p000pm.PackageManagerInternal;
import android.os.IBinder;
import android.os.ISpegHelperService;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Slog;
import com.android.internal.util.ArrayUtils;
import com.android.server.pm.permission.Permission;
import com.android.server.pm.permission.PermissionManagerServiceInternal;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.component.ParsedPermission;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class SpegService extends ProfileService {
  public boolean mBlockSpegInstallation;
  public String mPrevInstalledPkg;
  public volatile ISpegHelperService mService;
  public boolean mSetupWizardFinished;
  public String mSpegPackage;
  public boolean mSpegState;

  @Override // com.android.server.ProfileService
  public boolean checkAppId(int i) {
    return false;
  }

  @Override // com.android.server.ProfileService
  public boolean validateAppIDhook() {
    return false;
  }

  public SpegService(Context context) {
    super(context, "SpegService", "speg_helper");
    this.mSpegPackage = null;
    this.mSpegState = false;
    this.mBlockSpegInstallation = false;
    this.mSetupWizardFinished = false;
    this.packageBlockList = initPackageBlockList("/system/etc/speg-package-blocklist.conf");
    try {
      cleanupMarkerFiles();
    } catch (RuntimeException e) {
      Slog.e(this.TAG, "Unexpected failure in cleanup marker files", e);
    }
  }

  @Override // com.android.server.SystemService
  public void onStart() {
    lambda$selectSuitableProfileService$0();
  }

  @Override // com.android.server.ProfileService
  public IBinder getBinderOfService() {
    return ServiceManager.getService("speg_helper");
  }

  @Override // com.android.server.ProfileService
  public void setInterfaceOfService(IBinder iBinder) {
    if (iBinder != null) {
      this.mService = ISpegHelperService.Stub.asInterface(iBinder);
    }
  }

  @Override // com.android.server.ProfileService
  public void initializeInterfaceOfService() {
    this.mService = null;
  }

  public final void cleanupMarkerFiles() {
    File file = new File("/data/misc/speg");
    if (!file.isDirectory() || !file.canRead()) {
      Slog.e(this.TAG, "Failed to read /data/misc/speg");
      return;
    }
    File[] listFiles = file.listFiles();
    if (listFiles == null) {
      Slog.e(this.TAG, "Failed to get list of files in /data/misc/speg");
      return;
    }
    for (File file2 : listFiles) {
      if (!file2.isDirectory() && file2.getName().startsWith("speg.")) {
        String substring = file2.getName().substring(5);
        Slog.w(this.TAG, "Old speg marker file exists for uid " + substring);
        try {
          Iterator it = getSpegMarkerFilePaths(Integer.parseInt(substring)).iterator();
          while (it.hasNext()) {
            deleteMarkerFile(new File((String) it.next()));
          }
          deleteMarkerFile(file2);
        } catch (NumberFormatException unused) {
          Slog.e(this.TAG, "Failed to convert uid " + substring + " to int");
        }
      }
    }
  }

  public final List getSpegMarkerFilePaths(int i) {
    PackageManagerInternal packageManagerInternal =
        (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
    if (packageManagerInternal == null) {
      Slog.e(this.TAG, "Could not get package manager");
      return Collections.emptyList();
    }
    AndroidPackage androidPackage = packageManagerInternal.getPackage(i);
    if (androidPackage == null) {
      Slog.e(this.TAG, "Could not find app with uid " + i);
      return Collections.emptyList();
    }
    String[] sharedUserPackagesForPackage =
        packageManagerInternal.getSharedUserPackagesForPackage(
            androidPackage.getPackageName(), UserHandle.getUserId(i));
    String str = File.separator + "base.speg" + i;
    if (ArrayUtils.isEmpty(sharedUserPackagesForPackage)) {
      return List.of(androidPackage.getPath() + str);
    }
    ArrayList arrayList = new ArrayList(sharedUserPackagesForPackage.length);
    for (String str2 : sharedUserPackagesForPackage) {
      AndroidPackage androidPackage2 = packageManagerInternal.getPackage(str2);
      if (androidPackage2 != null) {
        arrayList.add(androidPackage2.getPath() + str);
      }
    }
    return arrayList;
  }

  public final void deleteMarkerFile(File file) {
    try {
      if (!file.exists()) {
        Slog.w(this.TAG, "Marker file " + file + " does not exist");
      }
      if (file.delete()) {
        return;
      }
      Slog.e(this.TAG, "Failed to delete marker file: " + file);
    } catch (SecurityException e) {
      Slog.e(this.TAG, "Exception during " + file + " deletion", e);
    }
  }

  public boolean createOrDeleteMarkerFiles(String str, boolean z, int i) {
    if (!checkUserAndService(i, 0)) {
      return false;
    }
    try {
    } catch (RemoteException | NullPointerException unused) {
      Slog.e(this.TAG, "Failed to create file: " + str);
    } catch (UnsupportedOperationException unused2) {
      Slog.wtf(this.TAG, "Trying to use disabled speg_helper");
    }
    return this.mService.createOrDeleteMarkerFiles(str, z, i);
  }

  public boolean storePrimaryProf(String str, String str2, int i) {
    if (!checkUserAndService(i, 0)) {
      return false;
    }
    try {
    } catch (RemoteException | NullPointerException unused) {
      Slog.e(this.TAG, "Failed to prepare profile");
    } catch (UnsupportedOperationException unused2) {
      Slog.wtf(this.TAG, "Trying to use disabled speg");
    }
    return this.mService.storePrimaryProf(str, str2, i);
  }

  @Override // com.android.server.ProfileService
  public boolean isServiceRunning() {
    if (this.mService != null) {
      return true;
    }
    Slog.w(this.TAG, "speg_helper is not running");
    return false;
  }

  public int getPidOf(String str, int i) {
    File file = new File("/sys/fs/cgroup/uid_" + i);
    if (!file.isDirectory() || !file.canRead()) {
      return -1;
    }
    for (File file2 : file.listFiles()) {
      if (file2.isDirectory() && file2.getName().startsWith("pid_")) {
        String substring = file2.getName().substring(4);
        if (str.equals(getProcessName(substring))) {
          try {
            return Integer.parseInt(substring);
          } catch (NumberFormatException e) {
            Slog.e(this.TAG, "Failed to convert pid " + substring + " to int", e);
            return -1;
          }
        }
      }
    }
    return -1;
  }

  public final String getProcessName(String str) {
    int indexOf;
    String str2 = "/proc/" + str + "/cmdline";
    if (!new File(str2).exists()) {
      return null;
    }
    try {
      BufferedReader createBufferedReader = createBufferedReader(str2);
      try {
        String readLine = createBufferedReader.readLine();
        if (readLine == null || (indexOf = readLine.indexOf(0)) == -1) {
          createBufferedReader.close();
          return null;
        }
        String substring = readLine.substring(0, indexOf);
        createBufferedReader.close();
        return substring;
      } finally {
      }
    } catch (IOException e) {
      Slog.e(this.TAG, "Failed to read process name for pid " + str, e);
      return null;
    }
  }

  public boolean checkSpegState(String str) {
    return this.mSpegState && str != null && str.equals(this.mSpegPackage);
  }

  public void setSpegState(String str) {
    this.mSpegPackage = str;
    this.mSpegState = str != null;
  }

  public void setSmartSwitchState(boolean z) {
    this.mBlockSpegInstallation = z;
  }

  public boolean isSmartSwitchBlockSpeg() {
    return this.mBlockSpegInstallation;
  }

  public void setSetupWizardState(boolean z) {
    this.mSetupWizardFinished = z;
  }

  public boolean isSetupWizardFinished() {
    return this.mSetupWizardFinished;
  }

  public boolean hasPrivilegedPermissions(AndroidPackage androidPackage) {
    Permission permissionTEMP;
    PermissionManagerServiceInternal permissionManagerServiceInternal =
        (PermissionManagerServiceInternal)
            LocalServices.getService(PermissionManagerServiceInternal.class);
    List permissions = androidPackage.getPermissions();
    String str = androidPackage.getPackageName() + ".";
    for (int i = 0; i < permissions.size(); i++) {
      ParsedPermission parsedPermission = (ParsedPermission) permissions.get(i);
      if (parsedPermission != null
          && !parsedPermission.getName().startsWith(str)
          && (permissionTEMP =
                  permissionManagerServiceInternal.getPermissionTEMP(parsedPermission.getName()))
              != null
          && (permissionTEMP.getProtectionFlags() & 16) != 0) {
        return true;
      }
    }
    return false;
  }
}
