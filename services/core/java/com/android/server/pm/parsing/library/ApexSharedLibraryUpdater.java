package com.android.server.pm.parsing.library;

import android.util.ArrayMap;
import com.android.modules.utils.build.UnboundedSdkLevel;
import com.android.server.SystemConfig;
import com.android.server.pm.parsing.pkg.ParsedPackage;

/* loaded from: classes3.dex */
public class ApexSharedLibraryUpdater extends PackageSharedLibraryUpdater {
  public final ArrayMap mSharedLibraries;

  public ApexSharedLibraryUpdater(ArrayMap arrayMap) {
    this.mSharedLibraries = arrayMap;
  }

  @Override // com.android.server.pm.parsing.library.PackageSharedLibraryUpdater
  public void updatePackage(ParsedPackage parsedPackage, boolean z, boolean z2) {
    int size = this.mSharedLibraries.size();
    for (int i = 0; i < size; i++) {
      updateSharedLibraryForPackage(
          (SystemConfig.SharedLibraryEntry) this.mSharedLibraries.valueAt(i), parsedPackage);
    }
  }

  public final void updateSharedLibraryForPackage(
      SystemConfig.SharedLibraryEntry sharedLibraryEntry, ParsedPackage parsedPackage) {
    if (sharedLibraryEntry.onBootclasspathBefore != null
        && isTargetSdkAtMost(
            parsedPackage.getTargetSdkVersion(), sharedLibraryEntry.onBootclasspathBefore)
        && UnboundedSdkLevel.isAtLeast(sharedLibraryEntry.onBootclasspathBefore)) {
      prefixRequiredLibrary(parsedPackage, sharedLibraryEntry.name);
    }
    if (sharedLibraryEntry.canBeSafelyIgnored) {
      PackageSharedLibraryUpdater.removeLibrary(parsedPackage, sharedLibraryEntry.name);
    }
  }

  public static boolean isTargetSdkAtMost(int i, String str) {
    return isCodename(str) ? i < 10000 : i < Integer.parseInt(str);
  }

  public static boolean isCodename(String str) {
    if (str.length() == 0) {
      throw new IllegalArgumentException();
    }
    return Character.isUpperCase(str.charAt(0));
  }
}
