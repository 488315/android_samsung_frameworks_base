package com.android.server.pm.parsing.library;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.android.internal.compat.IPlatformCompat;
import com.android.server.pm.parsing.pkg.AndroidPackageUtils;
import com.android.server.pm.parsing.pkg.ParsedPackage;
import com.android.server.pm.pkg.AndroidPackage;

/* loaded from: classes3.dex */
public class AndroidTestBaseUpdater extends PackageSharedLibraryUpdater {
  public static boolean isChangeEnabled(AndroidPackage androidPackage, boolean z) {
    if (!z) {
      try {
        return IPlatformCompat.Stub.asInterface(ServiceManager.getService("platform_compat"))
            .isChangeEnabled(
                133396946L, AndroidPackageUtils.generateAppInfoWithoutState(androidPackage));
      } catch (RemoteException | NullPointerException e) {
        Log.e("AndroidTestBaseUpdater", "Failed to get a response from PLATFORM_COMPAT_SERVICE", e);
      }
    }
    return androidPackage.getTargetSdkVersion() > 29;
  }

  @Override // com.android.server.pm.parsing.library.PackageSharedLibraryUpdater
  public void updatePackage(ParsedPackage parsedPackage, boolean z, boolean z2) {
    if (!isChangeEnabled(parsedPackage, z)) {
      prefixRequiredLibrary(parsedPackage, "android.test.base");
    } else {
      prefixImplicitDependency(parsedPackage, "android.test.runner", "android.test.base");
    }
  }
}
