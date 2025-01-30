package com.android.server.pm.parsing.library;

import com.android.internal.util.ArrayUtils;
import com.android.server.pm.parsing.pkg.ParsedPackage;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class PackageSharedLibraryUpdater {
  public abstract void updatePackage(ParsedPackage parsedPackage, boolean z, boolean z2);

  public static void removeLibrary(ParsedPackage parsedPackage, String str) {
    parsedPackage.removeUsesLibrary(str).removeUsesOptionalLibrary(str);
  }

  public static boolean isLibraryPresent(List list, List list2, String str) {
    return ArrayUtils.contains(list, str) || ArrayUtils.contains(list2, str);
  }

  public void prefixImplicitDependency(ParsedPackage parsedPackage, String str, String str2) {
    List usesLibraries = parsedPackage.getUsesLibraries();
    List usesOptionalLibraries = parsedPackage.getUsesOptionalLibraries();
    if (isLibraryPresent(usesLibraries, usesOptionalLibraries, str2)) {
      return;
    }
    if (ArrayUtils.contains(usesLibraries, str)) {
      parsedPackage.addUsesLibrary(0, str2);
    } else if (ArrayUtils.contains(usesOptionalLibraries, str)) {
      parsedPackage.addUsesOptionalLibrary(0, str2);
    }
  }

  public void prefixRequiredLibrary(ParsedPackage parsedPackage, String str) {
    if (isLibraryPresent(
        parsedPackage.getUsesLibraries(), parsedPackage.getUsesOptionalLibraries(), str)) {
      return;
    }
    parsedPackage.addUsesLibrary(0, str);
  }
}
