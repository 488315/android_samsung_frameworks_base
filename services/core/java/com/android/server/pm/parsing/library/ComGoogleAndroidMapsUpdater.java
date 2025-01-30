package com.android.server.pm.parsing.library;

import com.android.server.pm.parsing.pkg.ParsedPackage;

/* loaded from: classes3.dex */
public class ComGoogleAndroidMapsUpdater extends PackageSharedLibraryUpdater {
  @Override // com.android.server.pm.parsing.library.PackageSharedLibraryUpdater
  public void updatePackage(ParsedPackage parsedPackage, boolean z, boolean z2) {
    parsedPackage.removeUsesLibrary("com.google.android.maps");
    parsedPackage.removeUsesOptionalLibrary("com.google.android.maps");
  }
}
